package VFDS;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

/**
 * Handles all file management 
 * Usecases:
 *  - Loading in PGM image files 
 *  - Saving fracture data 
 *  - Loading fracture data
 */
public class FileHandler {
    private GUI gui;
    private File selectedFile;
    private File fileToSave;


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
        // Request files rom user
        FileInputDialog fileOpener = new FileInputDialog();
        ArrayList<File> files = fileOpener.getFilesFromUser();

        if (gui == null) {
            throw new Exception("No GUI attached to FileHandler");
        }
        if (files != null) {
            // Given files, create CTImageStack and pass that to the GUI 
            // NOTE: the createCTImageStack(files) method will do the converting from file -> CTImageSlice 
            gui.setImageStack( createCTImageStack(files) );
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
                }
            }
            inputStream.close();
            System.out.println("Read in PGM file: "+fileName);
            return imageData;
        } catch (Exception e) {
            // TODO: resolve the issue of if this actually corrupts
            e.printStackTrace();
            int[][] result = new int[0][0];
            return result;
        }
    }

    private CTImageStack createCTImageStack(ArrayList<File> files) {
        // Given files, creates CTImageSlices for each file
        // Does this in parallel using ForkJoin framework
        ArrayList<CTImageSlice> imageSlices=new ArrayList<CTImageSlice>();
        ForkJoinReadInFile readInAction = new ForkJoinReadInFile(imageSlices, files, 0);
        new ForkJoinPool().invoke(readInAction);

        // Takes abocve CTImageSlices and creates CTImageStack
        CTImageStack imageStack = new CTImageStack(imageSlices);
        return imageStack;
    }

    /**
     * Displays fracture load dialog to user and returns the file chosen 
     * @return File file user chooses to load in
     * @throws Exception
     */
    public File loadSavedFractures() throws Exception{
        JFileChooser fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home.Documents")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File","txt");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Load Fracture File");

        int result = fileChooser.showOpenDialog(this.gui);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }else{
            throw new FileNotFoundException("No File Selected");
        }
        return selectedFile;
    }


    /**
     * Called to display fracture save dialog to user
     * @return File file object that fractures need to be saved to
     * @throws FileNotFoundException
     */
    public File saveFractures() throws FileNotFoundException {
        // parent component of the dialog
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File","txt");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            this.fileToSave = fileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }else{
            throw new FileNotFoundException("No File Selected");
        }
        return fileToSave;
    }
}
