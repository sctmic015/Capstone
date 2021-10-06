package Testing;

import VFDS.CTImageSlice;
import VFDS.CTImageStack;
import VFDS.Detection;
import VFDS.FractureVoxel;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class TestDetection {

    @Test

    public static void main(String[] args){
        boolean noisy = true;
        // Obtain all necessary file info and all that jazz
        Scanner scan = new Scanner(System.in);
        //System.out.print("Enter num files: ");
        int numFiles = 512;//scan.nextInt();
        //System.out.println("Enter image file name: ");
        String fractureFileName = "ellipse512_18_noise/test-D512-V6141-F14-";//scan.nextLine();
        //System.out.println("Enter Test File name: ");
        String testFileName = "ellipse512_18_noise/test-D512-V6141-F14-fvoxels-";//scan.nextLine();
        /*System.out.println("Is the data noisy: y/n");
        String noisyString = scan.nextLine();
        if (noisyString.equals("y")){
            noisy = true;
        }
        */

        double startTime = System.nanoTime();
        // Load in the CTImages and testStack Images
        ArrayList<CTImageSlice> Stack = new ArrayList<CTImageSlice>();
        ArrayList<int[][]> testStack = new ArrayList<int[][]>();
        for (int i = 0; i < numFiles; i ++){
            File fractureFile = new File(fractureFileName + i+".pgm");
            int[][] imageData = readPGM(fractureFile);
            CTImageSlice CTImage = new CTImageSlice(i, imageData);
            Stack.add(CTImage);
            File testFile = new File(testFileName + i+".pgm");
            int[][] imageData2 = readPGM(testFile);
            testStack.add(imageData2);
            System.out.println(i);
        }

        CTImageStack CTStack = new CTImageStack(Stack);
        // Finds all testFractures in an ArrayOfFractureVoxels
        ArrayList<FractureVoxel> testFractures = writeTestFractures(testStack);

        ArrayList<FractureVoxel> fractureVoxels = new ArrayList<FractureVoxel>();
        Detection.findThresholds(CTStack);
       // System.out.println("Lower threshold:= " + Dectection.LOWER_BOUND_THRESHOLD + " Upper threshold:= " + Dectection.UPPER_BOUND_THRESHOLD);

        for (int i = 0; i < numFiles; i++) {
            ArrayList<FractureVoxel> fractureVoxelsLine = Detection.findFractureVoxels(CTStack.getImageSlice(i));
            fractureVoxels.addAll(fractureVoxelsLine);
        }
        System.out.println(testFractures.size());
        System.out.println(fractureVoxels.size());
        testInfo(testFractures, fractureVoxels);
        System.out.println(System.nanoTime() - startTime);
    }

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


    // Get test Fracture Voxels
    public static ArrayList<FractureVoxel> writeTestFractures(ArrayList<int[][]> testStack){
        ArrayList<FractureVoxel> testFractures = new ArrayList<FractureVoxel>();
        for (int i = 0; i < testStack.size(); i ++){
            for (int j = 0; j < testStack.get(i).length; j ++){
                for (int k = 0; k < testStack.get(i).length; k ++){
                    if (testStack.get(i)[j][k] == 255){
                        testFractures.add(new FractureVoxel(i ,k , j));
                        System.out.println("x co-ord =: " + k + " y co-ord =: " + j + " z co-ord =:" + i);
                    }
                }
            }
        }
        return testFractures;
    }

    public static void testInfo(ArrayList<FractureVoxel> test, ArrayList<FractureVoxel> results){
        double countMatches = 0;
        double testSize = test.size();
        ArrayList<FractureVoxel> falsePositives = new ArrayList<FractureVoxel>();
        for (int i = 0; i < results.size(); i ++){
            if (contains(test, results.get(i))){
                countMatches ++;
            }
            else
                falsePositives.add(results.get(i));
        }
        System.out.println("Number of matches = " + countMatches);
        System.out.println("Number of false positives = " + falsePositives.size());
        System.out.println("Number of missed fractures = " + test.size());
        System.out.println("The x, y, z, pixel value of the non found fractures are: ");
        for (int i = 0; i < test.size(); i ++){
            System.out.println(test.get(i).getX() + " , " + test.get(i).getY() + " , " + test.get(i).getZ());
        }
        double percentage = countMatches/testSize * 100;
        System.out.println("Percentage of fractures detected = " + percentage + "%");
    }

    public static boolean contains(ArrayList<FractureVoxel> test, FractureVoxel fractureVoxel){
        for (int i = 0; i < test.size(); i ++){
            if (fractureVoxel.equals(test.get(i))){
                test.remove(i);
                return true;
            }
        }
        return false;
    }

}

