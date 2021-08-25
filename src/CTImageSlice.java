/** 
 * A class representing a CT image slice object
 * @author SCTMIC015, SMTJUL022, BLRDAV002
 */
import java.awt.image.*;
import java.awt.*;

public class CTImageSlice {

    private int zCoOrd;
    private int xDimension;
    private int yDimension;
    private int[][] imageData;
    private BufferedImage image; // RGB image representation of data
    final int threshold = 120;   // Threshold for determining if a voxel is a fracture. To be determined analytically later

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
     * Generates greyscale image representation of image slice
     */
    void deriveImage(){
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

    public int getZCoOrd() {
        return this.zCoOrd;
    }

    public int getXDimension() {
        return this.xDimension;
    }

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

    public int[][] getImageData() {
        // TODO: deep copy
        return this.imageData;
    }


}
