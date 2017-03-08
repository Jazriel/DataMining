package wekaImages;
import javax.swing.*; 

import wekaImages.ui.WekaImagesFrame;

/**
 * MainWindow.java
 *	Main class that launch the application
 * 
 *
 * @author Jose Francisco Diez 
 * @version 1.00 
 */
public class MainWindow {
    
    public static void main(String[] args) {
    	
    	 SwingUtilities.invokeLater(new Runnable()
    	 {
    	 public void run()
    	 {
    	 WekaImagesFrame frame = new WekaImagesFrame();
         
         // Show frame.
         frame.show();
         frame.repaint();
    	 }
    	 });
    	
    	
        
    }
}
