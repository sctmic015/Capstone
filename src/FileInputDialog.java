import javax.swing.*;
import java.awt.*;
import java.io.File;


public class FileInputDialog extends JFrame{


    public FileInputDialog() {
    }


    /**
     * Displays filechooser popup to user to let them load .pgm images 
     * @return Array of Files selected by user 
     */
    public File[] getFilesFromUser() {
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
            if (fileChooser.getSelectedFile() != null && fileChooser.getSelectedFiles() == null) {
                File[] result = new File[1];
                result[0]=fileChooser.getSelectedFile();
                return result;
            }
            return fileChooser.getSelectedFiles();
        }else{
            this.dispose();
            return null;
        }
    }



}
