/**
@file PanelVisor.java

Descripci�n Archivo en el que se define la clase usada para visualizar imagenes
*/
package wekaImages.ui;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class extends JPanel and allows to show an image
 *
 * @author Jose Franciso Diez Pastor.
 * @version 1.0
 * @see javax.swing.JPanel
 */
public class ImagePanel extends JPanel{
	
	private Image imagen;
	
	/**
	 * Default constructor
	 */
	public ImagePanel(){

	}
	/**
	 * This method is used to update the image to show
	 * @param path of the image file
	 */
	public void setImagen(String path){
				
			imagen = getToolkit().getImage(path);
			repaint();
		
	}
	
	
	/**
	 * This method is used to update the image to show
	 * @param url of the image file
	 */
	public void setImagen(URL url){
				
			imagen = getToolkit().getImage(url);
			repaint();
		
	}
	
	/**
	 * This method is used to update the image to show
	 * @param image java object
	 */
	public void setImagen(Image imagen){
					
			this.imagen = imagen;
			repaint();
		
		}

	/**
	 * Returns the image currently in the viewer 
	 * @return imagen current image
	 * @see java.awt.Image
	 */
	public Image getImagen(){
		return imagen;
	}
	
	
	public synchronized void paint(Graphics g){
		if(imagen != null){
			g.drawImage(imagen,0,0,this.getSize().width,
					this.getSize().height,this);
		}
	}
}