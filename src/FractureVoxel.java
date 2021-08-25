/**
 * FractureVoxel
 */
public class FractureVoxel {
    public int zCoOrd;
    public int xCoOrd;
    public int yCoOrd;
    //public int colour;

    public FractureVoxel(int zCoOrd, int xCoOrd, int yCoOrd) {
        this.xCoOrd = xCoOrd;
        this.yCoOrd = yCoOrd;
        this.zCoOrd = zCoOrd;
        //this.colour = colour;
    }

    /**
     * Method to getX co-ord of FractureVoxel
     * @return
     */
    public int getX(){
        return xCoOrd;
    }

    /**
     * Method to get Y co-ord of FractureVoxel
     * @return
     */
    public int getY(){
        return yCoOrd;
    }

    public boolean checkNeighbourVoxel(FractureVoxel neighbour){
        if (Math.abs(neighbour.getX()) - xCoOrd <= 1 && Math.abs((neighbour.getY())) -yCoOrd <=1){
            return true;
        }
        else
            return false;
    }
}
