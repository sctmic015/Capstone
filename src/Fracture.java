import java.util.ArrayList;


/**
 * Fracture
 */
public class Fracture {
private ArrayList<FractureVoxel> arrVoxels = new ArrayList<FractureVoxel>(); 



public void addVoxel(FractureVoxel v){
    arrVoxels.add(v);
}

public int getSize(){
    return arrVoxels.size();
}

public boolean isPartOfFracture(FractureVoxel f){
    for (FractureVoxel i : arrVoxels){
        if(i.checkNeighbourVoxel(f)){
            return true;
        }
    }
    return false;
}

    
}