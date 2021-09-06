import java.io.File;
import java.util.ArrayList;

/**
 * Holds a stack of image slices 
 */
public class CTImageStack {
    private ArrayList<CTImageSlice> imageSlices;

    /**
     * Constructor that takes array of image files and 
     * creates and adds ImageSlices
     * @param files
     */
    public CTImageStack(File[] files) {
        imageSlices=new ArrayList<CTImageSlice>();
        for (int i = 0; i < files.length; i++) {
            imageSlices.add(new CTImageSlice(i,files[i]));
        }
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

}
