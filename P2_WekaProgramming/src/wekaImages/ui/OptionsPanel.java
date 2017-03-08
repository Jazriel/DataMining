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
 * This class is used to select the image descriptor filter to use.
 *
 * @author Jose Franciso Diez Pastor.
 * @version 1.0
 * @see javax.swing.JPanel
 */
 
 public class OptionsPanel extends JPanel {
 	
	private JCheckBox[] checkList;
	private String[] optionNames = {"Auto color Correlogram",
									"Binary Patters Pyramid",
									"ColorLayout",
									"Edge Histogram",
									"FCTH",
									"Fuzzy Opponent",
									"Gabor",
									"JPEG Coefficients",
									"PHOG",
									"Simple Color"
									};
 	
 	
 	/** 
	 * Default constructor
	 */ 	
 	public OptionsPanel(){
 		
 		int numOptions = optionNames.length;
 		checkList = new JCheckBox[numOptions];
 		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

 	     Listener listener = new Listener();

 	        for (int i = 0; i < numOptions; ++i) {
 	            checkList[i] = new JCheckBox(optionNames[i]);
 	            checkList[i].addItemListener(listener);
 	            this.add(checkList[i]);
 	        }
 	        
 	       checkList[0].setSelected(true);
 	        
 	   

 	        
 	    }
 	
 	/**
 	 * Returns a boolean array with the selected options
 	 * 
 	 * @return boolean array
 	 */
 	public boolean[] getSelectedOptions(){
 		boolean[] selectedOptions = new boolean[checkList.length];
 		
 		for (int i=0; i<checkList.length; i++){
 			if(checkList[i].isSelected()){
 				selectedOptions[i]=true;
 			}
 		}
 		
 		return selectedOptions;
 		
 	}

 	    

 	/**
 	 * Listener to force that al least one option is choosed
 	 * 
 	 * @author José Francisco Díez
 	 *
 	 */
 	private class Listener implements ItemListener {


	        private int selectionCounter = 0;

	        @Override
	        public void itemStateChanged(ItemEvent e) {
	            JCheckBox source = (JCheckBox) e.getSource();

	            if (source.isSelected()) {
	                selectionCounter++;
	                
	            }
	            else {
	                selectionCounter--;
	                // check for less than min selections:
	                if (selectionCounter < 1)
	                   source.setSelected(true);
	            }
	        }
	    }
	
	

 	
 	
 }