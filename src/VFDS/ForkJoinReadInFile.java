package VFDS;
import java.io.File;
import java.util.concurrent.RecursiveAction;
import java.util.ArrayList;


public class ForkJoinReadInFile extends RecursiveAction{
    private static int threshold = 25; // threshold for recursion (num. of images to work on per a thread)

    private ArrayList<File> files;
    private ArrayList<CTImageSlice> imageSlices;
    private int start;

    /**
     * Constructor to create RecursiveAction task
     * @param imageSlices imageSlices arraylist to place files into 
     * @param files raw image files to use to analyze 
     * @param start starting portion of arraylist to work on (necessary due to recursion)
     */
    public ForkJoinReadInFile(ArrayList<CTImageSlice> imageSlices, ArrayList<File> files, int start) {
        this.files = files;
        this.imageSlices = imageSlices;
        this.start = start;
    }
    
    /**
     * Overriden method that works on its given portion of the files arraylist 
     * and takes those files and reads in the PGM image file, makes a CTImageSlice from them 
     * and then adds them to the imageSlices arraylist in the correct position  
     */
    @Override
    protected void compute() {
        // https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html
        // https://mkyong.com/java/java-fork-join-framework-examples/
        if (imageSlices.size() <= threshold) {
            // compute directly
            for (int i = 0; i < files.size(); i++) {
                imageSlices.add(start+i, new CTImageSlice(i,files.get(i)) );
            }
        }else{
            // TODO: check that this captures all the images? i.e., doesnt 'miss' images
            int split = files.size()/2;
            invokeAll(
                new ForkJoinReadInFile(imageSlices, (ArrayList<File>)files.subList(start, split), start),
                new ForkJoinReadInFile(imageSlices, (ArrayList<File>)files.subList(start+split, files.size()), start+split)
            );
        }
    }
    
}
