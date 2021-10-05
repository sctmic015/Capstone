package VFDS;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

/**
 * Holds a stack of image slices 
 */
public class CTImageStack {
    private ArrayList<CTImageSlice> imageSlices;
    private int numSlices; // stores number of slices/layers/z-planes in stack (should be 127)
    private FractureCollection fractures;

    /**
     * Constructor that takes an arraylist of image files and 
     * creates and adds ImageSlices
     * @param files ArrayList of files 
     */
    public CTImageStack(ArrayList<CTImageSlice> imageSlices) {
        this.imageSlices = imageSlices;
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
     * Runs detection on all CTImageSlices in parallel 
     * to find fracture voxels and join them into fractures 
     * @param Runtime 
     */
    public void detectFractures() {
        // TODO: do in parallel 
        if (fractures == null) {
            ArrayList<FractureVoxel> allFractureVoxels = new ArrayList<FractureVoxel>();

            //apply concurrency at this point
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            allFractureVoxels.addAll(forkJoinPool.invoke(new DetectConcurrently(imageSlices, 0, imageSlices.size()) ));

            // for (CTImageSlice imageSlice : imageSlices) {
            //     allFractureVoxels.addAll( imageSlice.getFractureVoxels() );
            // }

            fractures = new FractureCollection(allFractureVoxels);
            
        }
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
}
