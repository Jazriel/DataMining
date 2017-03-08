package wekaImages.control;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;
/**
 * Filters the types of files displayed in the JFileChooser
 *
 * @author Jose Francisco Diez
 * @version 1.0
 */
public class ImageFilter extends FileFilter {
    final static String jpeg = "jpeg";
    final static String jpg = "jpg";
    final static String gif = "gif";
    final static String tiff = "tiff";
    final static String tif = "tif";
    
    /**
     * Method that accepts or not a file to be displayed in the JFileChooser
     * 
     * @param File object f
     * @return True if it will be displayed, false otherwise
     */
    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }

        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            String extension = s.substring(i+1).toLowerCase();
            if (tiff.equals(extension) ||
                tif.equals(extension) ||
                gif.equals(extension) ||
                jpeg.equals(extension) ||
                jpg.equals(extension)) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }
    
    /**
     * Method that returns the text displayed in the JFileChooser
     * 
     * @return description
     */
    public String getDescription() {
        return "Only image files";
    }
}
