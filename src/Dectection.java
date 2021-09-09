import java.util.ArrayList;
import java.util.Arrays;

public class Dectection {

    static final int LOWER_BOUND_THRESHOLD = 100;
    static final int UPPER_BOUND_THRESHOLD = 150;

    /**
     * Finds the fracture voxels by looking at the voxels colour and its neighbours
     * @return ArrayList<FractureVoxel> of voxels dectected as fractures
     */
    public static ArrayList<FractureVoxel> findFractureVoxels(CTImageSlice imageSlice){
        ArrayList<FractureVoxel> fractureVoxels = new ArrayList<FractureVoxel>();
        for (int x = 1; x < imageSlice.getXDimension() -1; x ++){
            for (int y = 1; y < imageSlice.getYDimension() -1; y ++){
                int voxelValue = imageSlice.getImageData()[x][y];
                // if (voxelValue > 0 && voxelValue < 10) {
                //     System.out.println(voxelValue);
                // }
                if (voxelValue > LOWER_BOUND_THRESHOLD &&
                    voxelValue < UPPER_BOUND_THRESHOLD
                    ){
                        fractureVoxels.add(new FractureVoxel(imageSlice.getZCoOrd(), y, x));
                        //System.out.print(imageData[x][y]);
                        // System.out.println("X-coOrd: " + y + " Y-coOrd: " + x);  // For some reason x and y are confused lol
                    }
            }
            //System.out.println();
        }
        return fractureVoxels;
    }

    /** 
     * Method to test the number of coloured voxels around a fractured voxel
     * If the count is greater than 4 we can assume it is a fracture
     * Else if the count is less than or equal to 4 we can assume it is part of the background
     * @param x X Co-ordinate of voxel/pixel to check 
     * @param y Y Co-ordinate of voxel/pixel to check 
     * @return
     */
    public boolean testNeighbours(int x, int y, CTImageSlice imageSlice){
        int count = 0;
        for (int i = x -1; i <= x +1; i ++){
            for (int j = y -1; j <= y +1; j ++){
                if (imageSlice.getImageData()[i][j] > LOWER_BOUND_THRESHOLD && 
                    imageSlice.getImageData()[i][j] < UPPER_BOUND_THRESHOLD){
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
