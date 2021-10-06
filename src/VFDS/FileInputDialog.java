package VFDS;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class FileInputDialog extends JFrame{


    public FileInputDialog() {
    }

    /**
     * Displays filechooser popup to user to let them load .pgm images 
     * @return ArrayList of Files selected by user 
     * @throws FileNotFoundException
     */
    public ArrayList<File> getFilesFromUser() throws FileNotFoundException {
        // TODO: destory entire frame on close
        this.setVisible(true);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true); // allow selection of multiple files
        //fileChooser.setFileFilter(new PGMFilenameFilter()); // only accept .pgm
        //fileChooser.setAcceptAllFileFilterUsed(true); // remove all files option
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM Files","pgm");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        // Adjust size of file chooser popup
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fileChooser.setPreferredSize(new Dimension((int)(dim.width*.6),(int)(dim.height*.8)));

        int returnVal = fileChooser.showDialog(this, "Load images");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.dispose();
            ArrayList<File> files = new ArrayList<File>();
            files.addAll(Arrays.asList( fileChooser.getSelectedFiles() ));
            Collections.sort(files, new Comparator<File>(){
                @Override
                public int compare(File o1, File o2) {
                    String s1 = o1.getName();
                    String s2 = o2.getName();
                    return new AlphanumComparator().compare(s1, s2);
                }
            });
            return files;
        }else{
            this.dispose();
            throw new FileNotFoundException("No File Selected");
        }
    }



}
