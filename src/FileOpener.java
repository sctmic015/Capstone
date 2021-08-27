import javax.swing.*;
import java.awt.*;

public class FileOpener extends JFrame{


    public FileOpener() {
    }


    public String getFileFromUser() {
        // TODO: destory entire frame on close
        this.setVisible(true);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false); // allow selection of multiple files
        // fileChooser.setFileFilter(new PDFFilter()); // only accept .pdf
        fileChooser.setAcceptAllFileFilterUsed(false); // remove all files option
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fileChooser.setPreferredSize(new Dimension((int)(dim.width*.6),(int)(dim.height*.8)));
        int returnVal = fileChooser.showDialog(this, "Open image");
        if (returnVal==0) {
            this.dispose();
            return fileChooser.getSelectedFile().getPath();
        }else{
            this.dispose();
            return null;
        }
    }




}
