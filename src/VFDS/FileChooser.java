package VFDS;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

/**
 * Handles all file management
 * Usecases:
 *  - Loading in PGM image files
 *  - Saving fracture data
 *  - Loading fracture data
 */
public class FileChooser extends JFileChooser {
    private GUI gui;
    private File fileToSave;


    public FileChooser() {
    }

    public FileChooser(GUI gui) {
        this.gui=gui;
    }

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
        }else
            throw new FileNotFoundException("No File Selected");
        return fileToSave;
    }
}
