package VFDS;
import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *  FileFilter for restricting user input to only select .pgm image files
 *  @author SCTMIC015, SMTJUL022, BLRDAV002
 */
public class PGMFilenameFilter extends FileFilter {

    /**
     * Empty constructor
     */
    public PGMFilenameFilter() {}

    /**
     * Overides FileFilter interface's accept method 
     * @param file the file that is being checked
     * @return true if and only if the file ends with .pgm
     */
    @Override
    public boolean accept(File file) {
        return file.getName().toLowerCase().endsWith(".pgm");
    }

    /**
     * Description to show in user file chooser 
     * @return Description to show in user file chooser 
     */
    @Override
    public String getDescription() {
        return "Only .pgm";
    }

	
}