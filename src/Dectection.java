import java.io.*;
import java.util.Scanner;
import java.util.*;

public class Dectection {


    public static void main (String[] args) throws Exception {
        ArrayList<FractureVoxel> fractureVoxels;
        int[][] image = readPGM("cross53.pgm"); // Read pgm image into 2D array
        // int[][] smoothedImage = SomeSmoothingMethod;
        CTImageSlice CTImageSlice = new CTImageSlice(53, image.length, image.length, image); // Create a new Image slice object

        fractureVoxels = CTImageSlice.findFractureVoxels(); // Find Fracture Voxels returns an array list of fractureVoxels objects
    }

    /**
     * Method to read PGM pixel values into a 2D integer Array
     * @param fileName
     * @return Integer array of pixel values
     * @throws IOException
     * Code adapted from somewhere on stackoverflow
     */
    public static int[][] readPGM(String fileName) throws IOException {
        String filePath = fileName;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Scanner scan = new Scanner(fileInputStream);
// Discard the magic number
        scan.nextLine();
// Discard the comment line
        scan.nextLine();
// Read pic width, height and max value
        int picWidth = scan.nextInt();
        int picHeight = scan.nextInt();
        int maxvalue = scan.nextInt();

        fileInputStream.close();

        fileInputStream = new FileInputStream(filePath);
        DataInputStream dis = new DataInputStream(fileInputStream);

        // look for 4 lines (i.e.: the header) and discard them
        int numnewlines = 4;
        while (numnewlines > 0) {
            char c;
            do {
                c = (char)(dis.readUnsignedByte());
            } while (c != '\n');
            numnewlines--;
        }

        int[][] data2D = new int[picHeight][picWidth];
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                data2D[row][col] = dis.readUnsignedByte();
                //System.out.print(data2D[row][col] + " ");
            }
            //System.out.println();
        }

        return data2D;
    }
}
