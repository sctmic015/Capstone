/** 
 * A class representing a CT image slice object
 * @author SCTMIC015, SMTJUL022, BLRDAV002
 */

import java.util.ArrayList;

public class CTImageSlice {

    public int zCoOrd;
    public int xDimension;
    public int yDimension;
    private int[][] imageData;
    final int threshold = 120;   // Threshold for determining if a voxel is a fracture. To be determined analytically later

    /**
     * Contructor when no image data has been extracted but image dimensions are known
     * @param zCoOrd position in stack of images (i.e., The images Z co-ordinate)
     * @param xDimension Width of the image
     * @param yDimension Height of the image
     */
    public CTImageSlice(int zCoOrd, int xDimension, int yDimension){
        this.zCoOrd = zCoOrd;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.imageData = new int[xDimension][yDimension];
    }

    /**
     * Contructor when image data has been extracted
     * @param zCoOrd position in stack of images (i.e., The images Z co-ordinate)
     * @param imageData Image data represented as a 2D array
     */
    public CTImageSlice(int zCoOrd, int[][] imageData){
        this.zCoOrd = zCoOrd;
        this.imageData = imageData;
        // calculated from image data
        this.xDimension = imageData.length;
        this.yDimension = imageData.length;
    }

    /**
     * Finds the fracture voxels by looking at the voxels colour and its neighbours
     * @return
     */
    public ArrayList<FractureVoxel> findFractureVoxels(){
        ArrayList<FractureVoxel> fractureVoxels = new ArrayList<FractureVoxel>();
        for (int x = 1; x < xDimension -1; x ++){
            for (int y = 1; y < yDimension -1; y ++){
                if (imageData[x][y] <= threshold && imageData[x][y] > 0){
                    fractureVoxels.add(new FractureVoxel(zCoOrd, y, x));
                    //System.out.print(imageData[x][y]);
                    System.out.println("X-coOrd: " + y + " Y-coOrd: " + x);  // For some reason x and y are confused lol
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
                if (imageData[i][j] > threshold){
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
