package VFDS;
import java.util.ArrayList;

public class Dectection {

    static int LOWER_BOUND_THRESHOLD = 100;
    static int UPPER_BOUND_THRESHOLD = 150;
    
     /**
     * method finds threshold values for detection of noisy images
     */
    public static void findThresholds(ArrayList<CTImageSlice> controlImages){
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
        int counter = 0;
        for(int i : arrHistogram){

            //continue/break values indicate indexes that are noise
            if(counter < 20){
                counter++;
                continue;
            }
            if(counter == 189){
                break;
            }

            //checking previous 'x' values behind the index. Change x for different probabiltiy metric/certainty of threshold
            int tempB = 0;
            int tempF =0;
            for(int x = 1; x <=4; x++){
                if(arrHistogram.get(counter - x) != 0){
                    tempB ++;
                }


            }
            for(int x =1; x<=5; x++){
                if(arrHistogram.get(counter + x) != 0){
                    tempF ++;
                }
            }
            //we can play arround with "4" for accuracy purposes. i.e 4 of its 5 predecessors/following values have a frequency above 0.
            //account for gaps
            if((tempB == 0) && (tempF >= 4)){
                tempLower = counter;

                counter ++;
                continue;
            }else if((tempB > 1) && (tempF <= 1)){
                if(tempLower!= 0){
                    tempHigher = counter;
                    //both thresholds found, therefore break
                    break;
                }
                counter++;
                continue;
            } else {
                counter ++;
                continue;
            }
        }


        LOWER_BOUND_THRESHOLD = (int)(tempLower-(tempHigher-tempLower)*0.1);
        UPPER_BOUND_THRESHOLD = (int)(tempHigher + (tempHigher - tempLower)*0.1);

    }

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
