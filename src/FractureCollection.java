import java.util.ArrayList;
public class Group{
private ArrayList<Fracture> arrFractures = new ArrayList<Fracture>();

public void group(ArrayList<FractureVoxel> arrFV){
    //iterate through list of idenitfied fracure voxels 

    outer: //outer label to continue to outer portion of nested loops
    for (FractureVoxel i : arrFV){
        //check if there are any fracture objects, if not, create one.
         if(arrFractures.size() == 0){
             Fracture temp = new Fracture();
             temp.addVoxel(i);
             //remove voxel from list
             arrFV.remove(i);
             arrFractures.add(temp);
             continue;
         }

        // iterate thorugh fracture objects, checking if voxel belongs to any fracture
         for(Fracture x : arrFractures){
            if(x.isPartOfFracture(i) == true){
                x.addVoxel(i);
                arrFV.remove(i);
                // skip to outer label, starting next iteration of outer loop
                 continue outer;
            }
        }

        //if the voxel doesnt belong to any fracture object, a new one is made
        Fracture temp = new Fracture();
        temp.addVoxel(i);
        arrFV.remove(i);
        arrFractures.add(temp);

     }
     }        
}


