package VFDS;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class DetectConcurrently extends RecursiveTask<ArrayList<FractureVoxel>> {

    int low;
    int high;
    ArrayList<CTImageSlice> arrCT;
    
    DetectConcurrently (ArrayList<CTImageSlice> CT, int low, int high){
        this.arrCT = CT;
        this.low = low;
        this.high = high;
    }

    protected ArrayList<FractureVoxel> compute(){
            if(high-low <= 10) {
                ArrayList<FractureVoxel> arrFV = new ArrayList<FractureVoxel>();

                for (int i = low; i < high; i++){
                    arrFV.addAll(arrCT.get(i).getFractureVoxels());
                }
                return arrFV;
            } else {
                int mid = low + (high -low)/2;
                DetectConcurrently left = new DetectConcurrently(arrCT, low, mid);
                DetectConcurrently right = new DetectConcurrently(arrCT, mid, high);
                left.fork();
                ArrayList<FractureVoxel> rightResult = new ArrayList<FractureVoxel>();
                ArrayList<FractureVoxel> leftResult = new ArrayList<FractureVoxel>();

                rightResult.addAll(right.compute());
                leftResult.addAll(left.join());
                ArrayList<FractureVoxel> answer = new ArrayList<FractureVoxel>();
                answer.addAll(rightResult);
                answer.addAll(leftResult);
                return answer;
            }




    }
    
}
