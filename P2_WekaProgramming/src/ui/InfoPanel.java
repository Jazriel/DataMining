/**
@file PanelInf.java

Descripci�n Archivo en el que se define la clase del panel inferior
			donde se muestra la matricula
*/
package wekaImages.ui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.image.*;
import java.util.*;

/**
 * This class is used to show information about the algorithms that are being running in each moment. 
 * 
 *
 * @author Jose Franciso Diez Pastor.
 * @version 1.0
 * @see javax.swing.JPanel
 */ 
 public class InfoPanel extends JPanel {
 	
 	
 	private JTextArea log;
 	
 	/** 
 	 * Default constructor
	 */ 	
 	public InfoPanel(){
 		// layout setup and component inicialization
 		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 		log= new JTextArea("  ",5,40);
 		
 		// panelScro1 contains the log TextArea and the scroll bars
 		JScrollPane panelScro1 = new JScrollPane();
    	panelScro1.setVerticalScrollBarPolicy(
      	ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
      	panelScro1.setHorizontalScrollBarPolicy(
      	ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
    	panelScro1.getViewport().add(log);
    	panelScro1.setPreferredSize(new Dimension(800, 60));
		panelScro1.setMinimumSize(new Dimension(800, 60));
		panelScro1.setMaximumSize(new Dimension(800, 60));
		
		
 		this.add(panelScro1);
 		
 	}
 	
	
	
 	/**
 	 * Shows text in the text area
 	 * @param text
 	 */
	public void setText(String text){
		log.setText(text);
		
	}
 	
 }