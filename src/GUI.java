import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI {


    /**
     * Entry point for the program 
     */
    public static void main(String[] args) {
        try {
            int imageData[][] = readPGM("/Users/david/Google Drive/Varsity/*Work/CSC 3003S/Capstone/capstone/data/cross38.pgm");
            CTImageSlice imageSlice = new CTImageSlice(38, imageData);
            ArrayList<FractureVoxel> fractureVoxels = imageSlice.findFractureVoxels();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        // int maxvalue = scan.nextInt();
        scan.nextInt();

        scan.close();
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

        int[][] imageData = new int[picHeight][picWidth]; // 2D array storing image colour values 
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                imageData[row][col] = dis.readUnsignedByte();
                /*
                if(imageData[row][col] > 200){
                // if(imageData[row][col] != 0 && imageData[row][col] != 200){
                    System.out.print(imageData[row][col] + " ");
                }
                */
            }
            // System.out.println();
        }

        return imageData;
    }

}
