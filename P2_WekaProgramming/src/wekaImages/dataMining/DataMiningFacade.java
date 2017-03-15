package wekaImages.dataMining;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import weka.filters.unsupervised.instance.imagefilter.BinaryPatternsPyramidFilter;
import weka.filters.unsupervised.instance.imagefilter.ColorLayoutFilter;
import weka.filters.unsupervised.instance.imagefilter.EdgeHistogramFilter;
import weka.filters.unsupervised.instance.imagefilter.FCTHFilter;
import weka.filters.unsupervised.instance.imagefilter.FuzzyOpponentHistogramFilter;
import weka.filters.unsupervised.instance.imagefilter.GaborFilter;
import weka.filters.unsupervised.instance.imagefilter.JpegCoefficientFilter;
import weka.filters.unsupervised.instance.imagefilter.PHOGFilter;
import weka.filters.unsupervised.instance.imagefilter.SimpleColorHistogramFilter;
import weka.filters.unsupervised.instance.imagefilter.AbstractImageFilter;
import wekaImages.control.ImageFilter;
import wekaImages.control.IncompatibleAttributeException;
import wekaImages.control.MissingModelDataException;

/**
 * Class that implements the facade pattern
 * 
 * Its methods provide the main functionality of the program using the classes
 * that are necessary
 * 
 * @author José Francisco Díez
 *
 */
public class DataMiningFacade {

	private static DataMiningFacade facade;

	private final Filter[] filters = { new AutoColorCorrelogramFilter(), new BinaryPatternsPyramidFilter(),
			new ColorLayoutFilter(), new EdgeHistogramFilter(), new FCTHFilter(), new FuzzyOpponentHistogramFilter(),
			new GaborFilter(), new JpegCoefficientFilter(), new PHOGFilter(), new SimpleColorHistogramFilter(), };

	private Instances dataset;
	private Classifier classifier;
	private Clusterer clusterer;

	/**
	 * Obtain the static instance of DataMiningFacade PD Singleton
	 * 
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
	 * @param path
	 *            arff absolute path
	 */
	public void loadDataset(String path) {

		DataSource source;
		try {
			source = new DataSource(path);
			dataset = source.getDataSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Load a classifier stored in the file system using the absolute path
	 * 
	 * @param path
	 *            arff absolute path
	 */
	public void loadClassifier(String path) {

		try {
			classifier = (Classifier) weka.core.SerializationHelper.read(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Load a clusterer stored in the file system using the absolute path
	 * 
	 * @param path
	 *            arff absolute path
	 */
	public void loadClusterer(String path) {

		try {
			clusterer = (Clusterer) weka.core.SerializationHelper.read(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Saves the classifier in a fixed location
	 */
	public void saveClassifier() {

		try {
			weka.core.SerializationHelper.write("~/DataMining/P2_WekaProgramming/classifier.model", classifier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ruta
	}

	/**
	 * Saves the clusteter in a fixed location
	 */
	public void saveClusterer() {

		try {
			weka.core.SerializationHelper.write("~\DataMining\P2_WekaProgramming\clusterer.model", clusterer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ruta
	}

	/**
	 * Saves the dataset in a fixed location
	 */
	public void saveDataset() {

		String header = "@RELATION " + dataset.relationName() + "\n";
		for (int i = 0; i < dataset.numAttributes(); i++) {
			header += "@ATTRIBUTE " + dataset.attribute(i).toString()
					+ dataset.attribute(i).value(dataset.attribute(i).type()) + "\n"; // TODO
																						// add
			// value
		}

		File temp;
		try {
			temp = File.createTempFile("dataset", ".arff");

			// a�adir cabecera e instancias
			BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

			writer.write(header.toString());

			for (Instance instance : dataset) {
				writer.write(instance.toString());
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This methods trains a new classifier
	 * 
	 * @param imagePathsMap
	 *            a hash table that has as many keys as classes and each key is
	 *            associated with an arraylist with the absolute paths of all
	 *            images associated with that class.
	 * @param options
	 *            boolean array with the image filters to apply
	 */
	public void trainClassiffier(HashMap<String, ArrayList<String>> imagePathsMap, boolean[] options) {

		Instances inst = new Instances(loadDataset(path));
		MultiFilter multiFilter = new MultiFilter();

		Filter[] thisFilters = new Filter[options.length];
		int j = 0;
		for (int i = 0; i < 10; i++) {
			if (options[i]) {
				thisFilters[j] = filters[i];
				j++;
			}
		}

	}

	/**
	 * This methods trains a new clusterer
	 * 
	 * @param imagePathsMap
	 *            a hash table that has as many keys as classes and each key is
	 *            associated with an arraylist with the absolute paths of all
	 *            images associated with that class.
	 * @param options
	 *            boolean array with the image filters to apply
	 */
	public void trainClusterer(HashMap<String, ArrayList<String>> imagePathsMap, boolean[] options) {

	}

	/**
	 * This methods creates a new dataset
	 * 
	 * @param imagePathsMap
	 *            a hash table that has as many keys as classes and each key is
	 *            associated with an arraylist with the absolute paths of all
	 *            images associated with that class.
	 * @param options
	 *            boolean array with the image filters to apply
	 */
	public void buildDataset(HashMap<String, ArrayList<String>> imagePathsMap, boolean[] options) {

		String arff = "@RELATION auto \n" + "@ATTRIBUTE imageid STRING\n" + "@ATTRIBUTE class {";
		for (String clss : imagePathsMap.keySet()) {
			arff += clss + ", ";
		}
		arff.substring(0, arff.length() - 2);
		arff += "}";
		arff += "@DATA\n";
		for (String key : imagePathsMap.keySet()) {
			for (String route : imagePathsMap.get(key)) {
				arff += route + ", " + key + "\n";
			}
		}

		// TODO main 1 guardarlo en un fichero la cabecera y hacer tmpdata
		// main 4 new data meter los filtros
		File temp=;
		try {
			temp = File.createTempFile("temp", ".arff");

			// añadir cabecera e instancias
			BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

			writer.write(arff);
			writer.flush();
			writer.close();
			

			loadDataset(temp.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Filter[] thisFilters = new Filter[options.length];
		int j = 0;
		for (int i = 0; i < 10; i++) {
			if (options[i]) {
				thisFilters[j] = filters[i];
				j++;
			}
		}
		MultiFilter multiFilter = new MultiFilter();
		multiFilter.setFilters(thisFilters);
		MultiFilter.useFilter(dataset, multiFilter);

	}

	/**
	 * This method predicts the class of an image, using the features indicated
	 * in options and the classifier already built
	 * 
	 * @param imagePath
	 *            of the image file
	 * @param options
	 *            of the image filters
	 * @return the class name
	 * @throws MissingModelDataException
	 *             the classifier is not built
	 * @throws IncompatibleAttributeException
	 *             the options are not compatible with the already built
	 *             classifier
	 */
	public String predictImage(String imagePath, boolean[] options)
			throws MissingModelDataException, IncompatibleAttributeException {
		return "";

	}

	/**
	 * This method find the most similar images using the features indicated in
	 * options and the dataset already built
	 * 
	 * @param imagePath
	 *            of the image file
	 * @param options
	 *            of the image filters
	 * @return A hashmap with one key and the list of image paths associated to
	 *         this key
	 * @throws MissingModelDataException
	 *             the classifier is not built
	 * @throws IncompatibleAttributeException
	 *             the options are not compatible with the already built
	 *             classifier
	 */
	public HashMap<String, ArrayList<String>> findSimilarities(String imagePath, boolean[] options)
			throws MissingModelDataException, IncompatibleAttributeException {
		return null;

	}

	/**
	 * This method shows cluster assigments of the images contained in the
	 * dataset
	 * 
	 * @return A hashmap with one key per cluster and the list of image paths
	 *         that belongs to each cluster associated to this key
	 * @throws MissingModelDataException
	 *             the classifier is not built
	 * @throws IncompatibleAttributeException
	 *             the options are not compatible with the already built
	 *             classifier
	 */
	public HashMap<String, ArrayList<String>> viewClusters()
			throws MissingModelDataException, IncompatibleAttributeException {

		return null;

	}

}
