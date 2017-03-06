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
 * This class is used to visualize one or multiple images
 *  *
 * @author Jose Franciso Diez Pastor.
 * @version 1.0
 * @see javax.swing.JPanel
 */ 
 public class CentralPanel extends JPanel {
 	
 	
 	
 	
 	/** Default constructor
	 *
	 */ 	
 	public CentralPanel(){
 		
 		
 	}
 	
	
	/**
	 * Shows an image
	 * 
	 * @param path of the image
	 */
	public void setImage(String path){
		this.removeAll();
		ImagePanel imgPanel = new ImagePanel();
		imgPanel.setSize(this.getSize());
		imgPanel.setImagen(path);
		this.setLayout(new GridLayout(1,1));
		this.add(imgPanel);
		this.repaint();
		
	}


	/**
	 * Shows multiple images.
	 * 
	 * @param allFiles is a hashmap, with as many keys as different classes. Each key is
	 * associated with an arraylist with the path of the image
	 */
	public void setMultipleImages(HashMap<String,ArrayList<String>> allFiles) {
		this.removeAll();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		
		
			Set<String> dirsNames = allFiles.keySet();
			
			for (String dirName : dirsNames){
				
				ArrayList<String> images = allFiles.get(dirName);
				int numRows = (int)Math.max(Math.ceil(images.size()/5),2);
				System.out.println(images.size()+" "+numRows);
				
				JPanel imagesPanel = new JPanel();
				imagesPanel.setLayout(new GridLayout(numRows,5));
				
				int width = 5*160;
				int height = numRows*120;
				
				imagesPanel.setMinimumSize(new Dimension(width,height));
				imagesPanel.setMaximumSize(new Dimension(width,height));
				imagesPanel.setPreferredSize(new Dimension(width,height));
				
				for(String imagePath : images){
					ImagePanel imgPanel = new ImagePanel();
					
					imgPanel.setImagen(imagePath);
					
					imgPanel.setPreferredSize(new Dimension(80,60));
					imgPanel.setMinimumSize(new Dimension(80,60));
					imagesPanel.add(imgPanel);
					
				}
				
			
			
			
			JScrollPane scroll = new JScrollPane(imagesPanel);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			
			
			tabbedPane.addTab(dirName, scroll);
			
		}
		

		tabbedPane.repaint();
		
		this.setLayout(new GridLayout(1,1));
		this.add(tabbedPane);
		
		this.repaint();
		
	}
 	
 }