package VFDS;
import java.awt.image.*;
import java.util.ArrayList;
public class FractureCollection {
    private ArrayList<Fracture> arrFractures = new ArrayList<Fracture>();
    

    /**
     * Create fracture collection from Fracture voxels
     * and detect fracture groups
     * @param fractureVoxels
     */
    public FractureCollection(ArrayList<FractureVoxel> fractureVoxels) {
        group(fractureVoxels);
        ColourBuilder.assignColorsToFractures(this.arrFractures);
    }
    

    
    public void group(ArrayList<FractureVoxel> arrFV){
        //iterate through list of idenitfied fracure voxels 
        outer: //outer label to continue to outer portion of nested loops
        for (FractureVoxel i : arrFV){
            //check if there are any fracture objects, if not, create one.
            if(arrFractures.size() == 0){
                Fracture temp = new Fracture();
                temp.addVoxel(i);
                //remove voxel from list
                arrFV.remove(i);
                arrFractures.add(temp);
                continue;
            }

            // iterate thorugh fracture objects, checking if voxel belongs to any fracture
            for(Fracture x : arrFractures){
                ArrayList<Fracture> temp = new ArrayList<Fracture>();
                if(x.isPartOfFracture(i) == true){
                    temp.add(x);
                }

                //voxel only belongs to 1 existing fracture. It gets added, and program iterates to next voxel
                if(temp.size() == 1){
                    //add voxel to fracture
                    x.addVoxel(i);
                    //remove voxel from voxel list
                    arrFV.remove(i);
                    // skip to outer label, starting next iteration of outer loop
                    continue outer;

                    // voxel belongs to multiple fractures, therefore they need to be combined
                } else if(temp.size() > 1){
                    ///combine, then remove existing fractures
                    Fracture combinedFracture = combineFractures(temp);
                    //add voxel to new combined fracture
                    combinedFracture.addVoxel(i);
                    //remove voxel from list
                    arrFV.remove(i);
                    //add combined fracture to Collection list
                    arrFractures.add(combinedFracture);

                    //remove old fractures that needed to be combined
                    for(Fracture fracture : temp){
                        arrFractures.remove(fracture);
                    }
                    continue outer;    
                }    
            }

            //if the voxel doesnt belong to any fracture object, a new one is made
            Fracture temp = new Fracture();
            temp.addVoxel(i);
            arrFV.remove(i);
            arrFractures.add(temp);

        }
    }        
    

    // /**
    //  * Gets grouping of fractures from a set of fracture voxels and appends them to fractures list
    //  * @param fractureVoxels List of fracture voxels (ungrouped)
    //  * @return ArrayList<Fracture> List of fractures
    //  */
    // public ArrayList<Fracture> getFractures(ArrayList<FractureVoxel> fractureVoxels) {
    //     for (FractureVoxel fractureVoxel : fractureVoxels) {
    //         for (FractureVoxel fractureVoxelNeighbour : fractureVoxels) {
    //             // TODO: no neighbour case
    //             if (fractureVoxel.isNeighbourVoxel(fractureVoxelNeighbour)) {
    //                 if (!fractureVoxelNeighbour.hasAssignedFracture()) {
    //                     if (!fractureVoxel.hasAssignedFracture()) {
    //                         Fracture newFracture = new Fracture();
    //                         // add both voxels to new fracture
    //                         // NOTE: when voxels are added to fracture they are marked assigned
    //                         newFracture.addVoxel(fractureVoxel);
    //                         newFracture.addVoxel(fractureVoxelNeighbour);
    //                         fractures.add(newFracture);
    //                     }else{
    //                         // NOTE: when voxels are added to fracture they are marked assigned
    //                         fractureVoxel.getAssignedFracture().addVoxel(fractureVoxelNeighbour);
    //                     }
    //                 }else{
    //                     // TODO: merge two fractures if fractureVoxelNeighbour and fractureVoxel fractures are different
    //                 }
    //             }
    //         }
    //     }
    //     return fractures;
    // }

    /**
     * Gets an image representation of the fractures for a given Z plane
     * @param zPlane The z-plane to get image rep. for 
     * @return BufferedImage An image representation of the fractures for a given Z plane
     */
    public BufferedImage getImage(int zPlane, int width, int height) {
        if (arrFractures.size()>0) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            // Loop through all fractures, then loop through all its voxels 
            // if the voxel is on the plane, paint that voxel onto the image 
            // using the given colour of the fracture
            for (Fracture fracture : arrFractures) {
                for (FractureVoxel fractureVoxel : fracture.getFractureVoxels()) {
                    if (fractureVoxel.getZ() == zPlane) {
                        image.setRGB(fractureVoxel.getX(), fractureVoxel.getY(), fracture.getColor().getRGB());
                    }
                }
            }
            return image;
        }
        else{
            // TODO: check this is ok?
            return new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB); // blank image 
        }
    }

    public static Fracture combineFractures(ArrayList<Fracture> fractureArray){
        //combine fractures
        Fracture fracture = new Fracture();
        // iterate therough list of fractures that have a voxel in common
        for(Fracture f : fractureArray){
            // get the voxel array list from hte fracture
            ArrayList<FractureVoxel> temp = f.getFractureVoxels();
            //add each voxel to the new combined fracture
            for(FractureVoxel x : temp){
                fracture.addVoxel(x);
            }

        }
        return fracture;
    }

}


