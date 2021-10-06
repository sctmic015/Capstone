package VFDS;
/**
 *  Fracture Voxel objects represent detected fracture voxels
 *  and store their co-ordinates, as well as other data needed for grouping
 *  @author SCTMIC015, SMTJUL022, BLRDAV002
 */
public class FractureVoxel {
    public int zCoOrd;
    public int xCoOrd;
    public int yCoOrd;
    private Fracture assignedFracture; // Ref. to Fracture this voxel is assigned to 

    /**
    *  Generic Constructor
    */
    public FractureVoxel(int zCoOrd, int xCoOrd, int yCoOrd) {
        this.xCoOrd = xCoOrd;
        this.yCoOrd = yCoOrd;
        this.zCoOrd = zCoOrd;
    }

    /**
     * Method to getX co-ord of FractureVoxel
     * @return int X co-ord of FractureVoxel
     */
    public int getX(){
        return xCoOrd;
    }

    /**
     * Method to get Y co-ord of FractureVoxel
     * @return int Y co-ord of FractureVoxel
     */
    public int getY(){
        return yCoOrd;
    }

    /**
     * Method to get Z co-ord of FractureVoxel
     * @return int Z co-ord of FractureVoxel
     */
    public int getZ(){
        return zCoOrd;
    }

    /**
     * method gets voxel co-ordinates for file saving
     * @return voxel co-ordinates for file saving
     */
    public String getAll() {
        return (this.getX() + " , " + this.getY() + " , " + this.getZ());
    }


    /**
     * Checks whether the given voxel is a neighbour to this voxel
     * NOTE: returns false if the same voxel is given (equality doesnt mean neighbor)
     * @param neighbourVoxel
     * @return boolean True if it is a neighbour, False if it isn't or they the same voxel
     */
    public boolean isNeighbourVoxel(FractureVoxel neighbourVoxel){
        if (neighbourVoxel.getX() - this.getX() >= -1 && neighbourVoxel.getX() - this.getX() <= 1 && 
            neighbourVoxel.getY() - this.getY() >= -1 && neighbourVoxel.getY() - this.getY() <= 1 &&
            neighbourVoxel.getZ() - this.getZ() >= -1 && neighbourVoxel.getZ() - this.getZ() <= 1) {
                if (neighbourVoxel.getX() == this.getX() && neighbourVoxel.getY() == this.getY() && neighbourVoxel.getZ() == this.getZ()) {
                    return false;
                }else{
                    return true;
                }
        }
        return false;
    }


    /**
     * Determines if the fracture voxel has been assigned to a fracture (i.e., set of fracture voxels) yet
     * @return boolean True if voxel has been assigned to a fracture, False otherwise 
     */
    public boolean hasAssignedFracture() {
        if (assignedFracture != null) {
            return true;
        }
        return false;
    }

    /**
     * Gets reference to the fracture this voxel is assigned to
     * Note: null if not assigned fracture 
     * @return Fracture reference to the fracture this voxel is assigned to
     */
    public Fracture getAssignedFracture() {
        return assignedFracture;
    }


    /**
     * Assigns the voxel to a fracture 
     * @param fracture Fracture fracture to assign the voxel to 
     * @return boolean True if assigned fracture is not null otherwise false 
     */
    public boolean setAssignedFracture(Fracture fracture) {
        this.assignedFracture = fracture;
        if(fracture == null){
            return false;
        }
        return true;
    }

    /**
     * Method checks if inpuuted voxel is the same as the one calling the method
     * @param FractureVoxel
     * @return boolean value
     */
    public boolean equals(FractureVoxel test){
        if (this.xCoOrd == test.getX() && this.yCoOrd == test.getY() && this.zCoOrd == test.zCoOrd){
            return true;
        }
        else
            return false;
    }

}
