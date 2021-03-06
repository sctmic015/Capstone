package VFDS;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.awt.image.*;
import java.awt.*;

/**
 * Class holds a stack of image slices and is used to iterate thorugh the stack,
 *  calling each ones' respective detection methods
 *  @author SCTMIC015, SMTJUL022, BLRDAV002
 */
public class CTImageStack {
    private ArrayList<CTImageSlice> imageSlices;
    private int numSlices; // stores number of slices/layers/z-planes in stack (should be 127)
    private FractureCollection fractures;

    /**
     * Constructor that takes an arraylist of image slices and 
     * @param ArrayList<CTImageSlice> imageSlices loaded in 
     */
    public CTImageStack(ArrayList<CTImageSlice> imageSlices) {
        this.imageSlices = imageSlices;
        Collections.sort(imageSlices); // TODO: safety check what now?
        numSlices = imageSlices.size();
    }


    /**
     * Gets image slice in image stack, given z co-ordinate
     * @param zCoOrd z co-ordinate of imageslice wanted 
     * @return CTImageSlice image slice on given plane (z co-ordinate), NULL if no imageslice 
     */
    public CTImageSlice getImageSlice(int zCoOrd) {
        if (imageSlices.size() >= zCoOrd) {
            return imageSlices.get(zCoOrd);
        }
        else{
            return null;
        }
    }

    /**
     * Gets the number of slices in the CTStack
     * @return int number of slices in the CTStack
     */
    public int getSize() {
        return numSlices;
    }


    /**
     * Runs detection on all CTImageSlices 
     * to find fracture voxels and join them into fractures objects
     */
    public void detectFractures() {
        if (fractures == null) {
            ArrayList<FractureVoxel> allFractureVoxels = new ArrayList<FractureVoxel>();
            for (CTImageSlice imageSlice : imageSlices) {
                allFractureVoxels.addAll( imageSlice.getFractureVoxels() );
            }
            fractures = new FractureCollection(allFractureVoxels);
        }
    }

    /**
     * loads Fracures in from saved file and assigns them the Fracture collection object
     * @param File text file to load fractures in
     */
    public void loadFractures(File file) throws FileNotFoundException {
        if (fractures == null) {
            ArrayList<FractureVoxel> allFractureVoxels = new ArrayList<FractureVoxel>();
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()){
                String line = scan.nextLine();
                Scanner scan2 = new Scanner(line);
                scan2.useDelimiter("\\s*,\\s*");
                int x = scan2.nextInt();
                int y = scan2.nextInt();
                int z = scan2.nextInt();
                FractureVoxel temp = new FractureVoxel(z, x, y);
                allFractureVoxels.add(temp);
            }
            fractures = new FractureCollection(allFractureVoxels);
        }
    }

    /**
     * Saves detected fractures to a text file for future loading
     * @param File text file to save fractures
     */
    public File saveFractures(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        BufferedWriter buffer = new BufferedWriter(writer);
        ArrayList<Fracture> frac = this.fractures.getFractures();
        for (Fracture x: frac){
            ArrayList<FractureVoxel> tempVoxels = x.getFractureVoxels();
            for (FractureVoxel y: tempVoxels){
                buffer.write(y.getAll() + "\n");
            }
        }
        buffer.close();
        return file;
    }

    /**
     * Gets fracture collection for this image stack 
     * 
     * NOTE: If the fractures havent been detected yet, it will 
     * return null  
     * @return FractureCollection fractures for this image stack
     */
    public FractureCollection getFractures() {
        return fractures;
    }

    /**
     * loads Fracures in from saved file and assigns them the Fracture collection object
     * @param int xCoOrd 
     * @return BufferedImage for overlay for the x-view
     */
    public BufferedImage getXviewImage(int xCoOrd) {
        BufferedImage image = new BufferedImage(imageSlices.get(0).getXDimension(), imageSlices.get(0).getYDimension(), BufferedImage.TYPE_INT_ARGB);
        for (CTImageSlice imageSlice : imageSlices) {
            if (imageSlice==null) { 
                System.out.println("missing image in slice position");
            }else{
                Color[] data = imageSlice.getXview(xCoOrd);
                for (int x = 0; x < data.length; x++) {
                    image.setRGB(x, imageSlice.getZCoOrd(), data[x].getRGB());
                } 
            }  
        }
        return image;
    }

    /**
     * loads Fracures in from saved file and assigns them the Fracture collection object
     * @param int yCoOrd 
     * @return BufferedImage for overlay for the y view
     */
    public BufferedImage getYviewImage(int yCoOrd) {
        BufferedImage image = new BufferedImage(imageSlices.get(0).getXDimension(), imageSlices.get(0).getYDimension(), BufferedImage.TYPE_INT_ARGB);
        for (CTImageSlice imageSlice : imageSlices) {
            if (imageSlice==null) {
                // missing image in slice position 
                System.out.println("missing image in slice position");
            }else{
                Color[] data = imageSlice.getYview(yCoOrd);
                for (int y = 0; y < data.length; y++) {
                    image.setRGB(y, imageSlice.getZCoOrd(), data[y].getRGB());
                    // TODO: remove
                    // if (imageSlice.getZCoOrd() == 77) {
                    //     image.setRGB(imageSlice.getZCoOrd(), y, new Color(200,100,23,255).getRGB());
                    // }
                }
            }
            
        }
        return image;
    }
}
