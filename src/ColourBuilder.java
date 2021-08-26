import java.awt.Color;
import java.util.ArrayList;

public class ColourBuilder {
    static Color colors[] = { Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN,
     Color.PINK, Color.ORANGE, Color.RED, Color.MAGENTA};  


     public static void assignColorsToFractures(ArrayList<Fracture> fractures){
        //shouldnt be more than 3 or 4 total fractures according to patrick. 
        int count = 0;
        for (Fracture fracture : fractures){
            fracture.setColor(colors[count]);
            count ++;
        }
     }
}