/**
@file Mediador.java

 */
package wekaImages.control;

import javax.swing.*;
import javax.swing.filechooser.*;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.awt.event.*;

import wekaImages.dataMining.DataMiningFacade;
import wekaImages.ui.*;

/**
 * Implements Design Pattern Mediator, it comunicates with user interface classes and facade
 *
 * @author Jose Franciso Diez Pastor.
 * @version 1.0
 */

public class Mediator {
	private InfoPanel infoPanel;
	private CentralPanel centralPanel;
	private OptionsPanel optionsPanel;

	private JMenuItem buildClassItem;
	private JMenuItem buildClusItem;
	private JMenuItem buildDataItem;

	private JMenuItem predictItem;
	private JMenuItem similaritiesItem;
	private JMenuItem viewClusItem;
	
	private JMenuItem saveClassItem;
	private JMenuItem saveClusItem;
	private JMenuItem saveDataItem;
	private JMenuItem loadItem;

	private JButton exeButton;

	private WekaImagesFrame window;
	
	private String imagePath;
	private HashMap<String,ArrayList<String>> imagePathsMap;
	
	private DataMiningFacade facade = DataMiningFacade.getFacade();
	
	
	/**
	 * Register the option panel
	 * 
	 * @param optionsPanel
	 */
	public void regOptionsPanel(OptionsPanel optionsPanel) {
		this.optionsPanel = optionsPanel;
	}//

	/**
	 * Register de central panel
	 * @param centralPanel
	 */
	public void regCentralPanel(CentralPanel centralPanel) {
		this.centralPanel = centralPanel;
	}//

	/**
	 * Register de information panel
	 * @param infoPanel
	 */
	public void regInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}//

	/**
	 * Register de execution button
	 * @param button
	 */
	public void regExeButton(JButton button) {

		exeButton = button;
		
	}

	/**
	 * Register the build classifier item 
	 * @param menuitem
	 */
	public void regBuildClass(JMenuItem menuitem) {
		buildClassItem = menuitem;
		buildClassItem.addActionListener(new CommandLoadFiles(0));
	}//

	/**
	 * Register the build clusterer item
	 * @param menuitem
	 */
	public void regBuildClus(JMenuItem menuitem) {
		buildClusItem = menuitem;
		buildClusItem.addActionListener(new CommandLoadFiles(1));
	}//

	
	/**
	 * Register the build dataset item
	 * @param menuitem
	 */
	public void regBuildData(JMenuItem menuitem) {
		buildDataItem = menuitem;
		buildDataItem.addActionListener(new CommandLoadFiles(2));
	}//
	
	/**
	 * Register the predict image item
	 * @param menuitem
	 */
	public void regPredict(JMenuItem menuitem) {
		predictItem = menuitem;
		predictItem.addActionListener(new CommandLoadImg(0)); 
	}//

	/**
	 * Register the find similarities item
	 * @param menuitem
	 */
	public void regSimilarities(JMenuItem menuitem) {
		similaritiesItem = menuitem;
		similaritiesItem.addActionListener(new CommandLoadImg(1)); 
	}//

	/**
	 * Register the view clustering item
	 * @param menuitem
	 */
	public void regViewClus(JMenuItem menuitem) {
		viewClusItem = menuitem;
		viewClusItem.addActionListener(new CommandViewClus()); 
	}//
	
	/**
	 * Register the save classifier item
	 * @param menuitem
	 */
	public void regSaveClass(JMenuItem menuitem) {
		saveClassItem = menuitem;
		saveClassItem.addActionListener(new CommandSaveClass()); 
	}//
	
	/**
	 * Register the save clustering item
	 * @param menuitem
	 */
	public void regSaveClus(JMenuItem menuitem) {
		saveClusItem = menuitem;
		saveClusItem.addActionListener(new CommandSaveClus()); 
	}//
	
	/**
	 * Register the save data item
	 * @param menuitem
	 */
	public void regSaveData(JMenuItem menuitem) {
		saveDataItem = menuitem;
		saveDataItem.addActionListener(new CommandSaveData()); 
	}//
	
	/**
	 * Register the load data/classifier/clusterer
	 * @param menuitem
	 */
	public void regLoad(JMenuItem menuitem) {
		loadItem = menuitem;
		loadItem.addActionListener(new CommandLoad()); 
	}//

	

	/**
	 * Obtain all directories in a folder
	 * @param folder
	 * @return arraylist of files
	 */
	private ArrayList<File> getDirectories(File folder) {
		ArrayList<File> directories = new ArrayList<File>(); 
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) { 
				directories.add(listOfFiles[i]);
				
			} 
			
		}
		return directories;
	}

	/**
	 * Obtains all files in a folder
	 * @param folder
	 * @return arraylist of files
	 */
	private ArrayList<String> getFiles(File folder) {
		ArrayList<String> files = new ArrayList<String>(); 
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) { 
				files.add(listOfFiles[i].getAbsolutePath());
				
			} 
			
		}
		return files;
	}

	/**
	 * Choose a directory and returns all directories in the first level
	 * and all files in that first level directories.
	 * 
	 * @return hashmap, where the key in the name of the directory and the value is the path on the files contained in that diretory
	 */
	private HashMap<String,ArrayList<String>> directoryChooser() {
		
		HashMap<String,ArrayList<String>> allFiles = new HashMap<String,ArrayList<String>>();
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		File selectedDirectory=null;
		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
			selectedDirectory = chooser.getSelectedFile();
						
			ArrayList<File> directories = getDirectories(selectedDirectory);
			
			for (File dir : directories){
				
				
				ArrayList<String> files = getFiles(dir);
				allFiles.put(dir.getName(), files);
				
				
			}
		}
			
		else {
			infoPanel.setText("No Selection ");
			
		}
		return allFiles;
		
		
	}


	/**
	 * Register the principal frame
	 *
	 * @param ventana
	 *            
	 */
	public void registraVentanaFrame(WekaImagesFrame ventana) {
		this.window = ventana;

	}// registraVentanaFrame

	
	/**
	 * Command for loading files.
	 * It also add the next action listener for the exec button
	 * 
	 * @author jose
	 *
	 */
	private class CommandLoadFiles extends Command {
		
		private int type;
		
		public CommandLoadFiles(int type){
			super();
			this.type = type;
		}
		
		public void runCommand() {
			imagePathsMap = directoryChooser(); 
			centralPanel.setMultipleImages(imagePathsMap);
			
			window.revalidate();
			window.repaint();
			ActionListener[] listeners = exeButton.getActionListeners();
			for(ActionListener l :listeners){
				exeButton.removeActionListener(l);
			}
			if (type==0)
				exeButton.addActionListener(new ComandTrainClass());
			else if(type==1){
				exeButton.addActionListener(new ComandTrainClus());
			}else{
				exeButton.addActionListener(new ComandGenerateData());
			}

		}
	}

	
	
	/**
	 * Comand for training a classifier
	 * 
	 * @author jose
	 *
	 */
	private class ComandTrainClass extends Command {
		public void runCommand() {
			
			boolean[] options = optionsPanel.getSelectedOptions();
			infoPanel.setText("Training classifier, please wait...");
			facade.trainClassiffier(imagePathsMap,options);
			infoPanel.setText("classifier Trained");

		}
	}
	
	
	
	
	/**
	 * Comand for generating data
	 * 
	 * @author jose
	 *
	 */
	private class ComandGenerateData extends Command {
		public void runCommand() {
			
			boolean[] options = optionsPanel.getSelectedOptions();
			infoPanel.setText("Building dataset, please wait...");
			facade.buildDataset(imagePathsMap,options);
			infoPanel.setText("Dataset Created");

		}
	}
	
	/**
	 * Comand for training a clusterer
	 * 
	 * @author jose
	 *
	 */
	private class ComandTrainClus extends Command {
		public void runCommand() {
			
			boolean[] options = optionsPanel.getSelectedOptions();
			infoPanel.setText("Training cluster, please wait...");
			facade.trainClusterer(imagePathsMap,options);
			infoPanel.setText("Cluster Trained");

		}
	}
	
	/**
	 * Comand for loading a image
	 * It also adds the next listener for the exec button, the predict image listener
	 * 
	 * @author jose
	 *
	 */
	private class CommandLoadImg extends Command {
		
		private int type;
		
		public CommandLoadImg(int type){
			super();
			this.type = type;
		}
		
		public void runCommand() {

			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File("."));

			fc.setDialogTitle("Choose Img");
			fc.addChoosableFileFilter(new ImageFilter());
			fc.setFileView(new ImageFileView());
			fc.setAccessory(new ImagePreview(fc));
			fc.setMultiSelectionEnabled(false);

			int result = fc.showDialog(window, "Load Img");
			File imagenCargada = fc.getSelectedFile();
			
			

			if (result == JFileChooser.APPROVE_OPTION) {
				imagePath = (imagenCargada.getPath());
				centralPanel.setImage(imagePath);
				
				
				ActionListener[] listeners = exeButton.getActionListeners();
				for(ActionListener l :listeners){
					exeButton.removeActionListener(l);
				}
				if (type==0)
					exeButton.addActionListener(new ComandPredict());
				else{
					exeButton.addActionListener(new ComandSimilarities());
				}
				

			}
		}
	}
	
	
	/**
	 * Command for predicting the class of a new image
	 * 
	 * @author jose
	 *
	 */
	private class ComandPredict extends Command {
		public void runCommand() {
			
		infoPanel.setText("Classify Image");
		try {
			boolean[] options = optionsPanel.getSelectedOptions();
			String name = facade.predictImage(imagePath,options);
			infoPanel.setText(name);
			window.revalidate();
			window.repaint();
		} catch (MissingModelDataException e) {
			infoPanel.setText("Dataset and/or model are missing");
		}catch (IncompatibleAttributeException e) {
			infoPanel.setText("Attributes are not compatible");
		}

		}
	}
	
	/**
	 * Command for finding similarities of a given image
	 * 
	 * @author jose
	 *
	 */
	private class ComandSimilarities extends Command {
		public void runCommand() {
			
			infoPanel.setText("Find Similarities");
			try {
				boolean[] options = optionsPanel.getSelectedOptions();
				imagePathsMap = facade.findSimilarities(imagePath,options);
				
				if (imagePathsMap!=null){
					centralPanel.setMultipleImages(imagePathsMap);
				}
				
				window.revalidate();
				window.repaint();
				
			} catch (MissingModelDataException e) {
				infoPanel.setText("Dataset and/or model are missing");
			} catch (IncompatibleAttributeException e) {
				infoPanel.setText("Attributes are not compatible");
			}

		}
	}
	
	/**
	 * Command for visualizing a cluster
	 * @author jose
	 *
	 */
	private class CommandViewClus extends Command {
		public void runCommand() {
			
			infoPanel.setText("View Cluster");
			try {
				imagePathsMap = facade.viewClusters();
				if (imagePathsMap!=null){
					centralPanel.setMultipleImages(imagePathsMap);
				}
				
				window.revalidate();
				window.repaint();
				
			} catch (MissingModelDataException e) {
				infoPanel.setText("Dataset and/or model are missing");
			}catch (IncompatibleAttributeException e) {
				infoPanel.setText("Attributes are not compatible");
			}

		}
	}
	
	/**
	 * Command for saving a new classifier
	 * 
	 * @author jose
	 *
	 */
	private class CommandSaveClass extends Command {
		public void runCommand() {
			
			infoPanel.setText("Saving classifier");
			facade.saveClassifier();
			infoPanel.setText("classifier saved");

		}
	}
	
	/**
	 * 
	 * Command for saving a clusterer
	 * @author jose
	 *
	 */
	private class CommandSaveClus extends Command {
		public void runCommand() {
			
			infoPanel.setText("Saving cluster");
			facade.saveClusterer();
			infoPanel.setText("cluster saved");

		}
	}
	
	/**
	 * Command for saving a dataset
	 * @author jose
	 *
	 */
	private class CommandSaveData extends Command {
		public void runCommand() {
			
			infoPanel.setText("Saving Data");
			facade.saveDataset();
			infoPanel.setText("Data saved");

		}
	}
	
	/**
	 * Command for loading dataset/classifier/clusterer models
	 * 
	 * @author jose
	 *
	 */
	private class CommandLoad extends Command {
		public void runCommand() {
			
						
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File("."));

			fc.setDialogTitle("Choose Dataset");			

			int result = fc.showDialog(window, "Choose Dataset");
			File dataFile = fc.getSelectedFile();			
			

			if (result == JFileChooser.APPROVE_OPTION) {
				String dataPath = (dataFile.getPath());
				infoPanel.setText("Load dataset "+dataPath);
				facade.loadDataset(dataPath);			

			}
			
			fc.setDialogTitle("Choose Classifier");			

			result = fc.showDialog(window, "Choose Classifier");
			File classFile = fc.getSelectedFile();			
			

			if (result == JFileChooser.APPROVE_OPTION) {
				String classPath = (classFile.getPath());

				infoPanel.setText("Load classifier "+classPath);
				facade.loadClassifier(classPath);			

			}
			
			fc.setDialogTitle("Choose Clusterer");			

			result = fc.showDialog(window, "Choose Clusterer");
			File clusFile = fc.getSelectedFile();			
			

			if (result == JFileChooser.APPROVE_OPTION) {
				String clusPath = (clusFile.getPath());

				infoPanel.setText("Load clusterer "+clusPath);
				facade.loadClusterer(clusPath);			

			}

		}
	}
	
	
	

	


}