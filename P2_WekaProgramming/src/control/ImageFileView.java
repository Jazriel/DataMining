package wekaImages.control;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * Shows the image on the JFileChooser
 *
 * @author Jose Francisco Diez
 * @version 1.0
 */
public class ImageFileView extends FileView {
    ImageIcon jpgIcon = new ImageIcon("images/jpgIcon.gif");
    ImageIcon gifIcon = new ImageIcon("images/gifIcon.gif");
    ImageIcon tiffIcon = new ImageIcon("images/tiffIcon.gif");
    
    /**
     * Returns the name of a file
     * 
     * @param File object f
     * @return the name (String)
     */
    public String getName(File f) {
        return null; 
    }
    /**
     * Returns the description of the file
     * 
     * @param File object f
     * @return description 
     */
    public String getDescription(File f) {
        return null; 
    }
    /**
     * Method that obtains if a File object is transversable
     * 
     * @param File object f
     * @return true if the file is trasversable false in the opposite case
     */
    public Boolean isTraversable(File f) {
        return null; 
    }
    /**
     * Obtains the description of the file type
     * 
     * @param File object f
     * @return description of the file type
     */
    public String getTypeDescription(File f) {
        String extension = getExtension(f);
        String type = null;

        if (extension != null) {
            if (extension.equals("jpeg") ||
                extension.equals("jpg")) {
                type = "JPEG Image";
            } else if (extension.equals("gif")){
                type = "GIF Image";
            } else if (extension.equals("tiff") ||
                       extension.equals("tif")) {
                type = "TIFF Image";
            } 
        }
        return type;
    }
    /**
     * Method that obtains the icon 
     * 
     * @param File object f
     * @return Icon
     */
    public Icon getIcon(File f) {
        String extension = getExtension(f);
        Icon icon = null;
        if (extension != null) {
            if (extension.equals("jpeg") ||
                extension.equals("jpg")) {
                icon = jpgIcon;
            } else if (extension.equals("gif")) {
                icon = gifIcon;
            } else if (extension.equals("tiff") ||
                       extension.equals("tif")) {
                icon = tiffIcon;
            } 
        }
        return icon;
    }
    
     
    /**
     * Return the extension of the file
     * @param File object f
     * @return extension
     */
    private String getExtension(File f) {

        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
