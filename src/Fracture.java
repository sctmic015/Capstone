import java.util.ArrayList;
import java.awt.*;


/**
 * Fracture
 */
public class Fracture {
    private ArrayList<FractureVoxel> fractureVoxels = new ArrayList<FractureVoxel>(); 
    private Color color;

    /**
     * Basic constructor
     */
    public Fracture() {
        color = new Color(255,0,0,255); // defualt to red
    }

    /**
     * Constructor for when a color is passed 
     * @param color color of fracture  
     */
    public Fracture(Color color) {
        this.color = color;
    }


    /**
     * Adds FractureVoxel to fracture and marks the voxel as assigned by referencing this fracture 
     * @param fractureVoxel FractureVoxel to add to fracture
     */
    public void addVoxel(FractureVoxel fractureVoxel){
        if(!fractureVoxels.contains(fractureVoxel)){
            fractureVoxels.add(fractureVoxel);
        }
        fractureVoxel.setAssignedFracture(this);
    }

    /**
     * Gets number of fracture voxels in the fracture
     * @return int number of fracture voxels in the fracture
     */
    public int getSize(){
        return fractureVoxels.size();
    }

    /**
     * Checks if given fracture voxel is in the fracture
     * @param fractureVoxel Fracture voxel to look up 
     * @return true if fracture contains the voxel, false otherwise
     */
    public boolean containsFractureVoxel(FractureVoxel fractureVoxelToCheck){
        return fractureVoxels.contains(fractureVoxelToCheck);
    }

    /**
     * Gets a list of all fracture voxels in fracture
     * @return ArrayList<FractureVoxel> List of fracture voxels in fracture
     */
    public ArrayList<FractureVoxel> getFractureVoxels() {
        return fractureVoxels;
    }

    /**
     * Gets color of the fracture 
     * @return Color color of the fracture 
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets Color of fracture
     * @param color
     */
    public void setColor(Color color){
        this.color = color;
    }
    
}