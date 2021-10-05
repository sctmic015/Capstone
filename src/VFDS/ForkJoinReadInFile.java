package VFDS;
import java.io.File;
import java.util.concurrent.RecursiveAction;
import java.util.ArrayList;


public class ForkJoinReadInFile extends RecursiveAction{
    private static int threshold = 10; // threshold for recursion (num. of images to work on per a thread)

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
        // to make seq. change "files" to "imageSlices" 
        int fsize=files.size(); //TODO:remove
        if (files.size() <= threshold) {
            // compute directly
            for (int i = 0; i < files.size()-1; i++) {
                imageSlices.add(start+i, new CTImageSlice(i,files.get(i)) );
            }
        }else{
            if (start==files.size()) {
                // TODO: is this ok?
                // imageSlices.add(start-1, new CTImageSlice(start,files.get(start)) );
            }else{
                // TODO: check that this captures all the images? i.e., doesnt 'miss' images
                int split = files.size()/2;
                ArrayList<File> files1=new ArrayList<File>();
                ArrayList<File> files2=new ArrayList<File>();
                try {
                    files1.addAll(files.subList(start, start+split));
                    files2.addAll(files.subList(start+split, files.size()));
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println();
                }
                invokeAll(
                    new ForkJoinReadInFile(imageSlices, files1, start),
                    new ForkJoinReadInFile(imageSlices, files2, start+split)
                );
            }
        }
    }
    
}
