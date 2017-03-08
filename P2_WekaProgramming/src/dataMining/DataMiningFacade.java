package wekaImages.dataMining;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import weka.classifiers.Classifier;
import weka.clusterers.Clusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.imagefilter.AutoColorCorrelogramFilter;
import weka.filters.unsupervised.instance.imagefilter.JpegCoefficientFilter;
import weka.filters.unsupervised.instance.imagefilter.PHOGFilter;
import weka.filters.unsupervised.instance.imagefilter.SimpleColorHistogramFilter;
import wekaImages.control.IncompatibleAttributeException;
import wekaImages.control.MissingModelDataException;

/**
 * Class that implements the facade pattern

Its methods provide the main functionality of the program using the classes that are necessary
 * 
 * @author José Francisco Díez 
 *
 */
public class DataMiningFacade {

	private static DataMiningFacade facade;
	
	private Instances dataset;
	private Classifier classifier;
	private Clusterer clusterer;
	

	/**
	 * Obtain the static instance of DataMiningFacade
	 * PD Singleton
	 * @return instance
	 */
	public static DataMiningFacade getFacade() {

		if (facade == null) {

			facade = new DataMiningFacade();
		}
		return facade;
	}

	/**
	 * Private constructor PD Singleton
	 */
	private DataMiningFacade() {

	}
	
		
	//////////////
	/**
	 * Load a dataset stored in the file system using the absolute path
	 * 
	 * @param path arff absolute path
	 */
	public void loadDataset(String path){
		
	}
	
	/**
	 * Load a classifier stored in the file system using the absolute path
	 * 
	 * @param path arff absolute path
	 */
	public void loadClassifier(String path){
		
	}
	
	/**
	 * Load a clusterer stored in the file system using the absolute path
	 * 
	 * @param path arff absolute path
	 */
	public void loadClusterer(String path){
		
	}
	
	/**
	 * Saves the classifier in a fixed location
	 */
	public void saveClassifier(){
		
	}
	
	/**
	 * Saves the clusteter in a fixed location
	 */
	public void saveClusterer(){
		
	}
	
	/**
	 * Saves the dataset in a fixed location
	 */
	public void saveDataset(){
		
	}
	
	/**
	 * This methods trains a new classifier
	 * 
	 * @param imagePathsMap a hash table that has as many keys as classes and each key is associated
	 * with an arraylist with the absolute paths of all images associated with that class.
	 * @param options boolean array with the image filters to apply
	 */
	public void trainClassiffier(HashMap<String,ArrayList<String>> imagePathsMap, boolean[] options){
		
		
		
		
	}

	/**
	 * This methods trains a new clusterer
	 * 
	 * @param imagePathsMap a hash table that has as many keys as classes and each key is associated
	 * with an arraylist with the absolute paths of all images associated with that class.
	 * @param options boolean array with the image filters to apply
	 */
	public void trainClusterer(HashMap<String, ArrayList<String>> imagePathsMap, boolean[] options) {
		
		
	}
	
	/**
	 * This methods creates a new dataset
	 * 
	 * @param imagePathsMap a hash table that has as many keys as classes and each key is associated
	 * with an arraylist with the absolute paths of all images associated with that class.
	 * @param options boolean array with the image filters to apply
	 */
	public void buildDataset(HashMap<String, ArrayList<String>> imagePathsMap,
			boolean[] options) {
		
		
	}
	
	/**
	 * This method predicts the class of an image, using the features indicated in options and the 
	 * classifier already built 
	 * 
	 * @param imagePath of the image file
	 * @param options of the image filters
	 * @return the class name
	 * @throws MissingModelDataException the classifier is not built
	 * @throws IncompatibleAttributeException the options are not compatible with the already built classifier
	 */
	public String predictImage(String imagePath, boolean[] options) throws MissingModelDataException, IncompatibleAttributeException{
		return "";
		
	}
	
	/**
	 * This method find the most similar images using the features indicated in options and the 
	 * dataset already built 
	 * 
	 * @param imagePath of the image file
	 * @param options of the image filters
	 * @return A hashmap with one key and the list of image paths associated to this key
	 * @throws MissingModelDataException the classifier is not built
	 * @throws IncompatibleAttributeException the options are not compatible with the already built classifier
	 */
	public HashMap<String, ArrayList<String>> findSimilarities(String imagePath, boolean[] options) throws MissingModelDataException, IncompatibleAttributeException{
		return null;
		
	}
	
	/**
	 * This method shows cluster assigments of the images contained in the dataset
	 * @return A hashmap with one key per cluster and the list of image paths that belongs to each cluster associated to this key 
	 * @throws MissingModelDataException the classifier is not built
	 * @throws IncompatibleAttributeException the options are not compatible with the already built classifier
	 */
	public HashMap<String, ArrayList<String>> viewClusters() throws MissingModelDataException, IncompatibleAttributeException{
		
				
		return null;
		
	}

	
	
	
	
	

}
