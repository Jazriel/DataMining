package wekaImages.control;
import javax.swing.*;
import java.beans.*;
import java.awt.*;
import java.io.File;
/**
 * Displays a preview of the currently selected image in the JFileChooser
 *
 * @author Jose Francisco Diez
 * @version 1.0
 */
public class ImagePreview extends JComponent 
                           implements PropertyChangeListener {
    ImageIcon thumbnail = null;
    File f = null;
    
    /**
     * Default constructor
     *
     * @param fc JFileChooser that uses the ImagePreview
     */                         
    public ImagePreview(JFileChooser fc) {
        setPreferredSize(new Dimension(100, 50));
        fc.addPropertyChangeListener(this);
    }
	/**
	 * Load an image in the FileChooser
	 *
	 */
    public void loadImage() {
        if (f == null)
            return;
 
        ImageIcon tmpIcon = new ImageIcon(f.getPath());
        if (tmpIcon.getIconWidth() > 90) {
            thumbnail = new ImageIcon(tmpIcon.getImage().
                                      getScaledInstance(90, -1,
                                                        Image.SCALE_DEFAULT));
        } else {
            thumbnail = tmpIcon;
        }
    }
	/**
	 * Method that implements PropertyChangeListener and is executed every time 
	 * the JFileChooser state changes
	 *
	 * @param e Property that must be changed
	 */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();
        if (prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
            f = (File) e.getNewValue();
            if(isShowing()) {
                loadImage();
                repaint();
            }
        }
    }
	/**
	 * Repaint the preview
	 * 
	 * @param g objeto de tipo Graphics
	 */
    public void paint(Graphics g) {
        if (thumbnail == null) {
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
        }
    }
}
