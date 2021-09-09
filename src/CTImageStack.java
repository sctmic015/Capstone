import java.io.File;
import java.util.ArrayList;

/**
 * Holds a stack of image slices 
 */
public class CTImageStack {
    private ArrayList<CTImageSlice> imageSlices;
    private int numSlices; // stores number of slices/layers/z-planes in stack (should be 127)

    /**
     * Constructor that takes an arraylist of image files and 
     * creates and adds ImageSlices
     * @param files ArrayList of files 
     */
    public CTImageStack(ArrayList<File> files) {
        imageSlices=new ArrayList<CTImageSlice>();
        for (int i = 0; i < files.size(); i++) {
            imageSlices.add( new CTImageSlice(i,files.get(i)) );
        }
        numSlices = files.size();
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

}
