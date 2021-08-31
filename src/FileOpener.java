import javax.swing.*;
import java.awt.*;
import java.io.File;


public class FileOpener extends JFrame{


    public FileOpener() {
    }


    /**
     * Displays filechooser popup to user to let them load .pgm images 
     * @return Array of Files selected by user 
     */
    public File[] getFilesFromUser() {
        // TODO: destory entire frame on close
        this.setVisible(true);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false); // allow selection of multiple files
        fileChooser.setFileFilter(new PGMFilenameFilter()); // only accept .pgm 
        fileChooser.setAcceptAllFileFilterUsed(true); // remove all files option
        // Adjust size of file chooser popup
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fileChooser.setPreferredSize(new Dimension((int)(dim.width*.6),(int)(dim.height*.8)));

        int returnVal = fileChooser.showDialog(this, "Load images");
        if (returnVal==0) {
            this.dispose();
            return fileChooser.getSelectedFiles();
        }else{
            this.dispose();
            return null;
        }
    }




}
