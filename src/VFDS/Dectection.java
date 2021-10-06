package VFDS;

import java.util.ArrayList;

public class Dectection {

    static int LOWER_BOUND_THRESHOLD = 200;
    static int UPPER_BOUND_THRESHOLD = 200;
    
     /**
      * Finds threshold values for detection of noisy images
      * @param imageStack imageStack to get thresholds for
      */
     public static void findThresholds(CTImageStack imageStack){
        // image analysis --> Determine if noisy; Detect thresholds.
        ArrayList<CTImageSlice> controlImages = new ArrayList<CTImageSlice>();
        for (int i = ((imageStack.getSize()/2) - 20);
            i < ((imageStack.getSize()/2) + 20); i ++){
                controlImages.add(imageStack.getImageSlice(i));
        }

         //ArrayList of integers/counters to plot frequency of voxel colour values
         ArrayList<Integer> arrHistogram = new ArrayList<>(201);

         int tempLower = 0;
         int tempHigher = 0;
         for(int i = 0; i < 201; i++){
             arrHistogram.add(0);
         }

         //plot the histogram points according to the voxelvalue and its frequency
         int voxelValue = 0;
         for(CTImageSlice imageSlice : controlImages){
             for (int x = 1; x < imageSlice.getXDimension() -1; x ++){
                 for (int y = 1; y < imageSlice.getYDimension() -1; y ++){
                     voxelValue = imageSlice.getImageData()[x][y];
                     //error checking
                     if(voxelValue > 200){
                         voxelValue = 200;
                     }

                     int temp = arrHistogram.get(voxelValue);
                     //update the frequency at the index value
                     temp++;
                     arrHistogram.set(voxelValue, temp);
                 }
             }
         }

         //this section checks the following/preceding values of the index to determine if it is a bound threshold
         //counter also serves as index for histogram


         //find upper bound
         for(int i = arrHistogram.size() -10; i >= 0; i--){
             int x_val = arrHistogram.get(i);
             if(x_val > 0 && arrHistogram.get(i+1) == 0){
                 tempHigher = i ;
                 break;
             }
         }

         for(int i = tempHigher -1; i >= 0; i --){
             int tempB = 0;
             for(int x = 1; x <=5; x++){
                 if(arrHistogram.get(i - x) != 0){
                     tempB ++;
                 }
             }
             if(tempB == 0){
                 tempLower = i;
                 break;
             }
         }


         LOWER_BOUND_THRESHOLD = tempLower;
         UPPER_BOUND_THRESHOLD = (int)(tempHigher + (tempHigher - tempLower)*0.1);

     }

    /**
     * Finds the fracture voxels by looking at the voxels colour and its neighbours
     * @return ArrayList<FractureVoxel> of voxels dectected as fractures
     */
    public static ArrayList<FractureVoxel> findFractureVoxels(CTImageSlice imageSlice){
        ArrayList<FractureVoxel> fractureVoxels = new ArrayList<FractureVoxel>();
        for (int x = 5; x < imageSlice.getXDimension() -5; x ++){
            for (int y = 5; y < imageSlice.getYDimension() -5; y ++){
                //System.out.println(LOWER_BOUND_THRESHOLD + " " + UPPER_BOUND_THRESHOLD);
                int voxelValue = imageSlice.getImageData()[x][y];
                // if (voxelValue > 0 && voxelValue < 10) {
                //     System.out.println(voxelValue);
                // }
                if (tentacles(imageSlice, x, y) == 0){
                }
                else if (tentacles(imageSlice, x, y) == 1){
                    if (voxelValue > LOWER_BOUND_THRESHOLD &&
                            voxelValue <= UPPER_BOUND_THRESHOLD
                    ){
                        fractureVoxels.add(new FractureVoxel(imageSlice.getZCoOrd(), y, x));
                        //System.out.print(imageData[x][y]);
                        //System.out.println("X-coOrd: " + y + " Y-coOrd: " + x);  // For some reason x and y are confused lol
                    }
                }
                else{
                    if (voxelValue <= UPPER_BOUND_THRESHOLD
                    ){
                        fractureVoxels.add(new FractureVoxel(imageSlice.getZCoOrd(), y, x));
                        //System.out.print(imageData[x][y]);
                       // System.out.println("X-coOrd: " + y + " Y-coOrd: " + x);  // For some reason x and y are confused lol
                    }
                }


            }
            //System.out.println();
        }
        return fractureVoxels;
    }

    public static int tentacles(CTImageSlice imageSlice, int x, int y){
        boolean inShape = false;
        int count = 0;
        // Tentacle one: left side
        for (int i = x-3; i < x; i ++){
            if (imageSlice.getImageData()[i][y] > LOWER_BOUND_THRESHOLD) {
                count ++;
                break;
            }
        }
        for (int i = x+1; i < x +4; i ++){
            if (imageSlice.getImageData()[i][y] > LOWER_BOUND_THRESHOLD) {
                count ++;
                break;
            }
        }
        for (int i = y-3; i < y; i ++){
            if (imageSlice.getImageData()[x][i] > LOWER_BOUND_THRESHOLD) {
                count ++;
                break;
            }
        }
        for (int i = y+1; i < y +4; i ++){
            if (imageSlice.getImageData()[x][i] > LOWER_BOUND_THRESHOLD) {
                count ++;
                break;
            }
        }
        if (count == 0 || count == 1){
            return 0;
        }
        if (count == 2) {
            return 1;
        }
        else
            return 2;
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
