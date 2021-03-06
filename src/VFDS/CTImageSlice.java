package VFDS;
import java.awt.image.*;
import java.awt.*;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

/** 
 * A class representing a CT image slice object
 * @author SCTMIC015, SMTJUL022, BLRDAV002
 */
public class CTImageSlice implements Comparable{

    private int zCoOrd;
    private int xDimension;
    private int yDimension;
    private int[][] imageData;
    private BufferedImage image; // RGB image representation of data
    private ArrayList<FractureVoxel> fractureVoxels;
    
    /**
     * Contructor when no image data has been extracted but image dimensions are known
     * @param zCoOrd position in stack of images (i.e., The images Z co-ordinate)
     * @param xDimension Width of the image
     * @param yDimension Height of the image
     */
    public CTImageSlice(int zCoOrd, int xDimension, int yDimension){
        this.zCoOrd = zCoOrd;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.imageData = new int[xDimension][yDimension];
    }

    /**
     * Contructor when image data has been extracted
     * @param zCoOrd position in stack of images (i.e., The images Z co-ordinate)
     * @param imageData Image data represented as a 2D array
     */
    public CTImageSlice(int zCoOrd, int[][] imageData){
        this.zCoOrd = zCoOrd;
        this.imageData = imageData;
        // calculated from image data
        this.xDimension = imageData.length;
        this.yDimension = imageData.length;
        deriveImage();
    }
    
    /**
     * Contructor when parsing image Data in via a File object
     * @param zCoOrd position in stack of images (i.e., The images Z co-ordinate)
     * @param imageFile PGM File of the image slice
     */
    public CTImageSlice(int zCoOrd, File imageFile){
        this.zCoOrd = zCoOrd;
        this.imageData = FileHandler.readPGM(imageFile);
        // calculated from image data
        this.xDimension = imageData.length;
        this.yDimension = imageData.length;
        deriveImage();
    }
    
    /**
     * Generates greyscale image representation of image slice
     */
    private void deriveImage(){
        image = new BufferedImage(xDimension, yDimension, BufferedImage.TYPE_INT_ARGB);
        for(int x=0; x < xDimension-1; x++){
            for(int y=0; y < yDimension-1; y++) {
                int r = imageData[y][x];
                int g = imageData[y][x];
                int b = imageData[y][x];
                int a = 255;
                Color color = new Color(r,g,b,a);
                image.setRGB(x, y, color.getRGB());
            }
        }
    }

    /**
     * Gets the Z co-ordinate of the CTImageSlice
     * @return int representing Z Co-ordinate
     */
    public int getZCoOrd() {
        return this.zCoOrd;
    }

    /**
     * Gets the X co-ordinate of the CTImageSlice
     * @return int representing X Co-ordinate
     */
    public int getXDimension() {
        return this.xDimension;
    }

    /**
     * Gets the Y co-ordinate of the CTImageSlice
     * @return int representing Y Co-ordinate
     */
    public int getYDimension() {
        return this.yDimension;
    }

    /**
     * Gets the image representation of the image slice
     * Will attempt to derive the image if image data is present but an image representation 
     * hasnt been generated
     * @return BufferedImage image representation of the image slice
     */
    public BufferedImage getImage() {
        if (image == null && imageData != null) {
            deriveImage();
        }
        return image;
    }

    /**
     * Gets the imageData as a two dimensional array of intgers
     * @return int[][] imageData array
     */
    public int[][] getImageData() {
        // TODO: deep copy
        return this.imageData;
    }

    /**
     * Gets the fracture voxels for this CTImageSlice 
     * Does not redectect if dectection has already been done 
     * to prevent double computation 
     * @return ArrayList of the fracture voxels for this CTImageSlice
     */
    public ArrayList<FractureVoxel> getFractureVoxels() {
        if (fractureVoxels==null) {
            this.detectFractureVoxels();
        }
        return fractureVoxels;
    }

    /**
     * Runs dectection on CTImageSlice to get fracture voxels
     *
     * NOTE: Fracture detection is not done in this class  
     */
    private void detectFractureVoxels() {
        fractureVoxels = Detection.findFractureVoxels(this);
    }

    /**
     * Gets 1d array of an x-axis side view of the image slice 
     * @param xCoOrd
     * @return 1d array of an x-axis side view of the image slice
     */
    public Color[] getXview(int xCoOrd) {
        Color[] data = new Color[xDimension];
        for (int y = 0; y < data.length; y++) {
            // reason these are swapped is becuase x/y co-ords are wrong
            int color = imageData[y][xCoOrd];
            data[y] = new Color(color,color,color,255);
            if (fractureVoxels != null) {
                for (FractureVoxel fractureVoxel : fractureVoxels) {
                    if (fractureVoxel.getX() == xCoOrd && fractureVoxel.getY() == y) {
                        data[y] = fractureVoxel.getAssignedFracture().getColor();
                    }
                }
            }
        }
        return data;
    }

    /**
     * Gets 1d array of an y-axis side view of the image slice 
     * @param yCoOrd
     * @return 1d array of an y-axis side view of the image slice
     */
    public Color[] getYview(int yCoOrd) {
        Color[] data = new Color[yDimension];
        for (int x = 0; x < data.length; x++) {
            int color = imageData[yCoOrd][x];
            data[x] = new Color(color,color,color,255);
            if (fractureVoxels != null) {
                for (FractureVoxel fractureVoxel : fractureVoxels) {
                    if (fractureVoxel.getX() == x && fractureVoxel.getY() == yCoOrd) {
                        data[x] = fractureVoxel.getAssignedFracture().getColor();
                    }
                }
            }
        }
        return data;
    }

    @Override
    public int compareTo(Object o) {
        // TODO: class check
        CTImageSlice other = (CTImageSlice) o;
        return this.zCoOrd-other.getZCoOrd();
    }

}
