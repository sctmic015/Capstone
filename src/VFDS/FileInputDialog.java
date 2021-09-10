package VFDS;
import javax.swing.*;
import java.awt.*;
import java.io.File;
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
     */
    public ArrayList<File> getFilesFromUser() {
        // TODO: destory entire frame on close
        this.setVisible(true);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true); // allow selection of multiple files
        fileChooser.setFileFilter(new PGMFilenameFilter()); // only accept .pgm 
        fileChooser.setAcceptAllFileFilterUsed(true); // remove all files option
        // Adjust size of file chooser popup
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fileChooser.setPreferredSize(new Dimension((int)(dim.width*.6),(int)(dim.height*.8)));

        int returnVal = fileChooser.showDialog(this, "Load images");
        if (returnVal==0) {
            this.dispose();
            ArrayList<File> files = new ArrayList<File>();
            files.addAll(Arrays.asList( fileChooser.getSelectedFiles() ));
            Collections.sort(files, new Comparator<File>(){
                @Override
                public int compare(File o1, File o2) {
                    int n1 = Integer.parseInt(o1.getName().split("[a-zA-Z]+")[1].replace(".", ""));
                    int n2 = Integer.parseInt(o2.getName().split("[a-zA-Z]+")[1].replace(".", ""));
                    return n1 - n2;
                }
            });
            return files;
        }else{
            this.dispose();
            return null;
        }
    }



}
