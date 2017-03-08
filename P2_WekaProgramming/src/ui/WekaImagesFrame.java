/**
@file VentanaFrame.java

Descripci�n Archivo en el que se define la clase de la ventana principal
*/
package wekaImages.ui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import wekaImages.control.Mediator;





/**
 * The main graphical class of the interface. This class declares and setups al components
 *
 * @author Jose Franciso Diez Pastor.
 * @version 1.0
 * @see javax.swing.JFrame
 */
public class WekaImagesFrame extends JFrame {
  
      
  private JMenuBar menuBar;
  private JMenu trainJMenu;
  private JMenu useJMenu; 
  private JMenu fileJMenu;
  
  
  private JMenuItem saveClassMenuItem;
  private JMenuItem saveClusMenuItem;
  private JMenuItem saveDataMenuItem;
  private JMenuItem loadMenuItem;
  
  
  private JMenuItem predictMenuItem;
  private JMenuItem similaritiesMenuItem;
  private JMenuItem clusterMenuItem;

  private JMenuItem buildClassMenuItem;
  private JMenuItem buildClusMenuItem;
  private JMenuItem buildDataMenuItem;
  
  
  
  
  
  
  private InfoPanel infoPanel;
  private CentralPanel centralPanel;
  private OptionsPanel optionsPanel;
  
  private Mediator mediator;
  
  private JToolBar exeToolBar ;
  private JButton exeButton;
  
  
  
       

    

  /**
   * Default constructor
   */
  public WekaImagesFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      initialize();
      repaint();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Initialize the graphical interface components
   *
   * @throws Exception if one error happens
   */
  private void initialize() throws Exception  {
    //inicializacion de cada uno de los componentes
    
    menuBar = new JMenuBar();
    trainJMenu =new JMenu("Train");
    useJMenu =new JMenu("Use");
    fileJMenu =new JMenu("File");
    
    
    
    buildClassMenuItem = new JMenuItem("Build Classifier");
    buildClusMenuItem=new JMenuItem("Build Clustering");
    buildDataMenuItem=new JMenuItem("Build DataSet");

    predictMenuItem = new JMenuItem("Classify Image");    
    similaritiesMenuItem = new JMenuItem("Find Similar Images");  	
  	clusterMenuItem = new JMenuItem("Show Clusters");
    
    
  	saveClassMenuItem=new JMenuItem("Save Classifier");
    saveClusMenuItem=new JMenuItem("Save Cluster");
    saveDataMenuItem=new JMenuItem("Save Dataset");
    loadMenuItem =new JMenuItem("Load data/models");
    
    
    
    
    //Creates the menu bar
    menuBar.add(trainJMenu);
    menuBar.add(useJMenu);
    menuBar.add(fileJMenu);
    
    
    
    trainJMenu.add(buildClassMenuItem);
    trainJMenu.add(buildClusMenuItem);
    trainJMenu.add(buildDataMenuItem);
    
    useJMenu.add(predictMenuItem);
    useJMenu.add(similaritiesMenuItem);
    useJMenu.add(clusterMenuItem);
    
    fileJMenu.add(saveClassMenuItem);
    fileJMenu.add(saveClusMenuItem);
    fileJMenu.add(saveDataMenuItem);
    fileJMenu.add(loadMenuItem);
    
	setJMenuBar(menuBar);
	// set up dimension and title 
	this.setSize(new Dimension(800,600));
    this.setTitle("Weka Images 0.1");
    
	// initialize the graphical componets 
    infoPanel =new InfoPanel();
    centralPanel = new CentralPanel();
    exeToolBar = new JToolBar();
    optionsPanel = new OptionsPanel();
    
    exeButton = new JButton("Execute");
    exeToolBar.add(exeButton);
    
    
  	 	
  	mediator= new Mediator();
  	
  	// Locates the components in the graphical window  	    
	this.setLayout(new BorderLayout(0,0));
	this.add(exeToolBar,BorderLayout.NORTH);
	this.add(centralPanel,BorderLayout.CENTER);
	this.add(infoPanel,BorderLayout.SOUTH);
	this.add(optionsPanel,BorderLayout.EAST);
	
	
	
	
	// register the components in the mediator 	
    mediator.regBuildClass(buildClassMenuItem);
    mediator.regBuildClus(buildClusMenuItem);
    mediator.regBuildData(buildDataMenuItem); 
    
    mediator.regPredict(predictMenuItem); 
    mediator.regSimilarities(similaritiesMenuItem); 
    mediator.regViewClus(clusterMenuItem); 
    
    
    
    mediator.regSaveClass(saveClassMenuItem); 
    mediator.regSaveClus(saveClusMenuItem); 
    mediator.regSaveData(saveDataMenuItem);
    mediator.regLoad(loadMenuItem);
    
    
    mediator.regInfoPanel(infoPanel);
    mediator.regCentralPanel(centralPanel);
    mediator.regOptionsPanel(optionsPanel);
    
    mediator.regExeButton(exeButton);
    
    mediator.registraVentanaFrame(this);
    
    }
    //Modified to exit when the window closes 
  	protected void processWindowEvent(WindowEvent e) {
    	super.processWindowEvent(e);
    	if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      		System.exit(0);
    	}
  	}
	public void revalidate() {
		this.invalidate();
		this.validate();
		
	}
 
}



 
    


  
  
  
