import java.awt.Color;
import java.util.ArrayList;

/** 
 * A class used to assign colours to fracture objects
 * @author SCTMIC015, SMTJUL022, BLRDAV002
 */
public class ColourBuilder {
    static Color colors[] = { Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN,
     Color.PINK, Color.ORANGE, Color.RED, Color.MAGENTA};  

    
     /** 
    * Method iterates through fracture objects, assigning each one a primary colour
    */
     public static void assignColorsToFractures(ArrayList<Fracture> fractures){
        //shouldnt be more than 3 or 4 total fractures according to patrick. 
        int count = 0;
        for (Fracture fracture : fractures){
            fracture.setColor(ColourBuilder.colors[count % ColourBuilder.colors.length]);
            count ++;
        }
     }
}