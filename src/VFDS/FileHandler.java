package VFDS;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all file management 
 * Usecases:
 *  - Loading in PGM image files 
 *  - Saving fracture data 
 *  - Loading fracture data
 */
public class FileHandler {
    private GUI gui;


    public FileHandler() {
    }

    public FileHandler(GUI gui) {
        this.gui=gui;
    }



    /**
     * Loads new set of image files into GUI given an array of files 
     * @return boolean True if successfull, false otherwise
     * @throws Exception Exception if no gui instance variable is set or its null 
     */
    public boolean loadImages() throws Exception{
        FileInputDialog fileOpener = new FileInputDialog();
        ArrayList<File> files = fileOpener.getFilesFromUser();
        if (gui == null) {
            throw new Exception("No GUI attached to FileHandler");
        }
        if (files != null) {
            gui.setImageStack( new CTImageStack(files) );
            return true;
        }
        return false;
    }

    /**
     * Method to read PGM pixel values into a 2D integer Array
     * @param file PGM Image File to read in 
     * @return int[][] 2D Integer array of pixel values
     * Code adapted from somewhere on stackoverflow
     */
    public static int[][] readPGM(File file) {
        try {
            String fileName = file.getPath();
            FileInputStream fileInputStream;
                fileInputStream = new FileInputStream(fileName);
        
            Scanner scan = new Scanner(fileInputStream);
            // Discard the magic number
            scan.nextLine();
            // Discard the comment line
            scan.nextLine();
            // Read pic width, height and max value
            int picWidth = scan.nextInt();
            int picHeight = scan.nextInt();
            // int maxvalue = scan.nextInt();
            scan.nextInt();

            scan.close();
            fileInputStream.close();

            fileInputStream = new FileInputStream(fileName);
            DataInputStream inputStream = new DataInputStream(fileInputStream);

            // look for 4 lines (i.e.: the header) and discard them
            int numnewlines = 4;
            while (numnewlines > 0) {
                char c;
                do {
                    c = (char)(inputStream.readUnsignedByte());
                } while (c != '\n');
                numnewlines--;
            }

            int[][] imageData = new int[picHeight][picWidth]; // 2D array storing image colour values 
            for (int row = 0; row < picHeight; row++) {
                for (int col = 0; col < picWidth; col++) {
                    imageData[row][col] = inputStream.readUnsignedByte();
                    /*
                    if(imageData[row][col] > 200){
                    // if(imageData[row][col] != 0 && imageData[row][col] != 200){
                        System.out.print(imageData[row][col] + " ");
                    }
                    */
                }
                // System.out.println();
            }
            inputStream.close();
            return imageData;
        } catch (Exception e) {
            // TODO: resolve the issue of if this actually corrupts
            // TODO Auto-generated catch block
            e.printStackTrace();
            int[][] result = new int[0][0];
            return result;
        }
    }
}
