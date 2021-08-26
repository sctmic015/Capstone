import java.util.ArrayList;


/**
 * Fracture
 */
public class Fracture {
    private ArrayList<FractureVoxel> fractureVoxels = new ArrayList<FractureVoxel>(); 

    /**
     * Basic constructor
     */
    public Fracture() {}


    /**
     * Adds FractureVoxel to fracture and marks the voxel as assigned by referencing this fracture 
     * @param fractureVoxel FractureVoxel to add to fracture
     */
    public void addVoxel(FractureVoxel fractureVoxel){
        if(!fractureVoxels.contains(fractureVoxel)){
            fractureVoxels.add(fractureVoxel);
        }
        fractureVoxel.setAssignedFracture(this);
    }

    /**
     * Gets number of fracture voxels in the fracture
     * @return int number of fracture voxels in the fracture
     */
    public int getSize(){
        return fractureVoxels.size();
    }

    /**
     * Checks if given fracture voxel is in the fracture
     * @param fractureVoxel Fracture voxel to look up 
     * @return true if fracture contains the voxel, false otherwise
     */
    public boolean containsFractureVoxel(FractureVoxel fractureVoxelToCheck){
        return fractureVoxels.contains(fractureVoxelToCheck);
    }

    
}