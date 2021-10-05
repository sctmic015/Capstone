package VFDS;
import java.io.File;
import java.util.concurrent.RecursiveAction;
import java.util.ArrayList;


public class ForkJoinReadInFile extends RecursiveAction{
    private static int threshold = 1; // threshold for recursion (num. of images to work on per a thread)

    private ArrayList<File> files;
    private ArrayList<CTImageSlice> imageSlices;
    private int pos;

    /**
     * Constructor to create RecursiveAction task
     * @param imageSlices imageSlices arraylist to place files into 
     * @param files raw image files to use to analyze 
     * @param pos starting portion of arraylist to work on (necessary due to recursion)
     */
    public ForkJoinReadInFile(ArrayList<CTImageSlice> imageSlices, ArrayList<File> files, int pos) {
        this.files = files;
        this.imageSlices = imageSlices;
        this.pos = pos;
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
        // to make seq. change "files" to "imageSlices" 
        int numFiles=files.size(); //TODO:remove
        if (numFiles <= threshold) {
            // compute directly
            for (int i = 0; i < numFiles; i++) {
                imageSlices.add(new CTImageSlice(pos+i,files.get(i)) );
                // imageSlices.add(pos+i, new CTImageSlice(pos+i,files.get(i)) );
            }
        }else{
            // TODO: check that this captures all the images? i.e., doesnt 'miss' images
            int split = numFiles/2;
            ArrayList<File> files1=new ArrayList<File>();
            ArrayList<File> files2=new ArrayList<File>();
            try {
                files1.addAll(files.subList(0, split));
                files2.addAll(files.subList(split, numFiles));
            } catch (Exception e) {
                System.out.println();
            }
            invokeAll(
                // new ForkJoinReadInFile(imageSlices, files1, 0),
                // new ForkJoinReadInFile(imageSlices, files2, 0)
                new ForkJoinReadInFile(imageSlices, files1, pos),
                new ForkJoinReadInFile(imageSlices, files2, pos+split)
            );
            
        }
    }
    
}
