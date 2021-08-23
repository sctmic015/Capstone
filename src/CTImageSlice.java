/** A class representing a CT image slice object
 * @author SCTMIC015, SMTJUL022, BLRDAV002
 */

import java.util.ArrayList;

public class CTImageSlice {

    public int zCoOrd;
    public int xDimension;
    public int yDimension;
    public int[][] imageArray;
    final int threshold = 120;   // Threshold for determining if a voxel is a fracture. To be determined analytically later

    public CTImageSlice(int zCoOrd, int xDimension, int yDimension, int[][] imageArray){
        this.zCoOrd = zCoOrd;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.imageArray = imageArray;
    }

    /**
     * Finds the fracture voxels based on the colour of the voxel and the neighbour checking in testNeighbours method
     * @return
     */
    public ArrayList<FractureVoxel> findFractureVoxels(){
        ArrayList<FractureVoxel> fractureVoxels = new ArrayList<FractureVoxel>();
        for (int i = 1; i < xDimension -1; i ++){
            for (int j = 1; j < yDimension -1; j ++){
                if (imageArray[i][j] <= threshold && testNeighbours(i, j)){
                    fractureVoxels.add(new FractureVoxel(zCoOrd, i, j));
                    //System.out.print(imageArray[i][j]);
                    System.out.println("X-coOrd: " + i + " Y-coOrd: " + j);  // For some reason x and y are confused lol
                }
            }
            //System.out.println();
        }
        return fractureVoxels;
    }

    /** Method to test the number of coloured voxels around a fractured voxel
     * If the count is greater than 4 we can assume it is a fracture
     * Else if the count is less than or equal to 4 we can assume it is part of the background
     * @param x
     * @param y
     * @return
     */
    public boolean testNeighbours(int x, int y){
        int count = 0;
        for (int i = x -1; i <= x +1; i ++){
            for (int j = y -1; j <= y +1; j ++){
                if (imageArray[i][j] >threshold){
                    count ++;
                }
            }
        }
        if (count > 4){
            return true;
        }
        else
            return false;
    }
}
