import java.util.ArrayList;
public class Group{
private ArrayList<Fracture> arrFractures = new ArrayList<Fracture>();

public void group(ArrayList<FractureVoxel> arrFV){
    //iterate through list of idenitfied fracure voxels 
    for (FractureVoxel i : arrFV){
        //check if there are any fracture objects, if not, create one.
         if(arrFractures.size() == 0){
             Fracture temp = new Fracture();
             temp.addVoxel(i);
             arrFractures.add(temp);
             continue;
         }

         for(Fracture x : arrFractures){

            if(x.isPartOfFracture(i) == true){

            }
        }


     }


     }        
}


