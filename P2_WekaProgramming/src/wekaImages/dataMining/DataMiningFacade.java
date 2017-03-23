package wekaImages.dataMining;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.clusterers.Clusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ManhattanDistance;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.neighboursearch.LinearNNSearch;
import weka.core.neighboursearch.NearestNeighbourSearch;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.imagefilter.AbstractImageFilter;
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

	private final AbstractImageFilter[] filters = { new AutoColorCorrelogramFilter(), new BinaryPatternsPyramidFilter(),
			new ColorLayoutFilter(), new EdgeHistogramFilter(), new FCTHFilter(), new FuzzyOpponentHistogramFilter(),
			new GaborFilter(), new JpegCoefficientFilter(), new PHOGFilter(), new SimpleColorHistogramFilter(), };

	private Instances dataset = null;
	private Classifier classifier = null;
	private Clusterer clusterer = null;

	private Map<Instance, String> routes = new HashMap<>();
	private Map<String, String> paths = new HashMap<>();

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
			e.printStackTrace();
		}

	}

	/**
	 * Saves the classifier in a fixed location
	 */
	public void saveClassifier() {

		try {
			weka.core.SerializationHelper.write("./models/classifier.model", classifier);
		} catch (Exception e) {
			e.printStackTrace();

		} // ruta
	}

	/**
	 * Saves the clusteter in a fixed location
	 */
	public void saveClusterer() {

		try {
			weka.core.SerializationHelper.write("./models/clusterer.model", clusterer);

		} catch (Exception e) {
			e.printStackTrace();
		} // ruta
	}

	/**
	 * Saves the dataset in a fixed location
	 */
	public void saveDataset() {

		
		try {
			File file = new File("./models/dataset.arff");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write(dataset.toString());
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
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

		buildDataset(imagePathsMap, options);

		classifier = new J48();

		try {
			classifier.buildClassifier(dataset);
		} catch (Exception e) {
			e.printStackTrace();
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
		buildDataset(imagePathsMap, options);
		
		dataset.setClassIndex(-1); 
		// dataset.deleteAttributeAt(dataset.numAttributes()-1); si lo borras falla
		
		
		clusterer = new SimpleKMeans();
		try {
			clusterer.buildClusterer(dataset);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		arff = arff.substring(0, arff.length() - 2);
		arff += "}\n";
		arff += "@DATA\n";
		for (String key : imagePathsMap.keySet()) {
			for (String route : imagePathsMap.get(key)) {
				arff += route + ", " + key + "\n";
			}
		}

		// filtrar directamente.
		File temp;
		try {
			temp = File.createTempFile("temp", ".arff");
			temp.deleteOnExit();
			// a�adir cabecera e instancias
			BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

			writer.write(arff);
			writer.flush();
			writer.close();

			loadDataset(temp.getCanonicalPath());

			dataset = filterDataset(dataset, options);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Instances createInstance(String imagePath) {
		String arff = "@RELATION auto \n" + 
				"@ATTRIBUTE imageid STRING\n"+
				"@ATTRIBUTE class {car, plane, train}\n";
		// TODO atributos de clase dinamicos.
		arff += "@DATA\n";

		arff += imagePath + ", car\n";

		// filtrar directamente.
		File temp;
		try {
			temp = File.createTempFile("temp", ".arff");
			temp.deleteOnExit();
			// a�adir cabecera e instancias
			BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

			writer.write(arff);
			writer.flush();
			writer.close();

			DataSource source = new DataSource(temp.getCanonicalPath());
			return source.getDataSet();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Instances filterDataset(Instances dataset, boolean[] options) throws Exception {
		int len = 0;
		for (boolean b : options) {
			if(b){
				len++;
			}
		}
		AbstractImageFilter[] thisFilters = new AbstractImageFilter[len];
		int j = 0;
		for (int i = 0; i < 10; i++) {
			if (options[i]) {
				thisFilters[j] = filters[i];
				thisFilters[j].setImageDirectory("");
				j++;
			}
		}
		MultiFilter multiFilter = new MultiFilter();
		multiFilter.setFilters(thisFilters);
		multiFilter.setInputFormat(dataset);
		Instances filteredDataset = Filter.useFilter(dataset, multiFilter);
		
		for (Instance instance : filteredDataset) {
			String aux = instance.stringValue(instance.attribute(0));
			aux+="";
			routes.put(instance, aux);
		}
		filteredDataset.deleteAttributeAt(0);
		
		for (Instance instance : filteredDataset) {
			paths.put(instance.toString(), routes.get(instance));
		}
		filteredDataset.setClassIndex(filteredDataset.numAttributes()-1);
		return filteredDataset;
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

		checkClassifier();
		
		try {
			Instance instance = filterDataset(createInstance(imagePath), options).firstInstance();
			
			double instanceClass = classifier.classifyInstance(instance);
			
			return dataset.classAttribute().value((int)(instanceClass));
		} catch (Exception e) {
			throw new IncompatibleAttributeException();
		}

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

		NearestNeighbourSearch nnSearch = new LinearNNSearch();
		try {
			nnSearch.setDistanceFunction(new ManhattanDistance());
			nnSearch.setInstances(dataset);

			Instance instance = filterDataset(createInstance(imagePath), options).firstInstance();
			// Obtiene los 5 vecinos m�s cercanos
			Instances neighbours = nnSearch.kNearestNeighbours(instance, 5);

			HashMap<String, ArrayList<String>> ret = new HashMap<>();
			ArrayList<String> ar = new ArrayList<>();
			ret.put(imagePath, ar);
			for (Instance i : neighbours) {
				ar.add(paths.get(i.toString()));
			}
			return ret;
		} catch (Exception e) {
			throw new IncompatibleAttributeException();
		}
	}

	/**
	 * This method shows cluster assignments of the images contained in the
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
		checkClusterer();

		HashMap<String, ArrayList<String>> clustered = new HashMap<>();

		for (Instance instance:dataset) {
			int cluster;
			try {
				cluster = clusterer.clusterInstance(instance);
				String key = String.valueOf(cluster);
				ArrayList<String> clusterList = clustered.get(key);
				String path = paths.get(instance.toString());

				if (clusterList != null) {
					clusterList.add(path);
				} else {
					ArrayList<String> aux = new ArrayList<String>();
					aux.add(path);
					clustered.put(key, aux);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return clustered;

	}

	private void checkClassifier() throws MissingModelDataException {
		if (classifier == null) {
			throw new MissingModelDataException();
		}
	}

	private void checkClusterer() throws MissingModelDataException {
		if (clusterer == null) {
			throw new MissingModelDataException();
		}
	}

}
