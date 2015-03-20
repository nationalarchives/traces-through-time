package nl.liacs.link;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.liacs.link.blocking.Blocker;
import nl.liacs.link.blocking.FirstLetterBlocker;
import nl.liacs.link.blocking.NullBlocker;
import nl.liacs.link.distance.AbsoluteDistance;
import nl.liacs.link.distance.DamerauLevenshteinDistance;
import nl.liacs.link.distance.QGramsDistance;
import nl.liacs.link.distance.Distance;
import nl.liacs.link.distance.JaroDistance;
import nl.liacs.link.distance.LevenshteinDistance;
import nl.liacs.link.distance.SoundexDistance;
import nl.liacs.link.filter.BasicFilter;
import nl.liacs.link.filter.Filter;
import nl.liacs.link.filter.QualityFilter;
import nl.liacs.link.filter.QuantityFilter;
import nl.liacs.link.recordlinker.NaiveLinker;
import nl.liacs.link.recordlinker.RecordLinker;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Convenience class that handles the validation of the supplied configuration
 * elements and returns requested object instances.
 *
 * Instead of handling all the validating inline, this handler localizes
 * everything which keeps the code clean.
 * 
 * @author Benjamin
 * @author Kleanthi
 */
public class ConfigurationHandler {

    private final File file;             // reference to the configuration file
    private final Configuration config;  // Apache Commons Configuration
    private ConfigurationHandlerCheck configCheck;
    
    /**
     * Inner class that checks the validation of strings and string arrays
     * values of the configuration file attributes.
     * Prints appropriate messages if an attribute is empty or missing.
     */
    public class ConfigurationHandlerCheck{
        private String faultyString;
        private String faultyStringArray;
        
        ConfigurationHandlerCheck(){
            
        }
        /**
         * Remove empty or null strings from a String array.
         *
         * @param array, a String array
         * @returns an array object (a <String>[])
         */
        public String[] removeEmptyValues(String[] array){
            List<String> list = new ArrayList<String>(Arrays.asList(array));
            list.removeAll(Arrays.asList("", null));
            return list.toArray(new String[list.size()]);
        }
        /**
         * Remove duplicated strings from a String array.
         *
         * @param array, a String array
         * @returns an array object (a <String>[])
         */
        public String[] removeDuplicatedValues(String[] array){
            Set<String> stringSet = new HashSet<>(Arrays.asList(array));
            return stringSet.toArray(new String[0]);
        }
        /**
         * Check if a string array is empty 
         *
         * @param array, a String array
         * @returns true or false (a <boolean> object)
         */
        public boolean StringArrayEmpty(String[] array, String field){
            if(array.length == 0){
                this.faultyStringArray = field + " is either missing from the configuration file or is empty.";
                return true;
            }else {
                return false;
            }
        }
        
        /**
         * Check if a string array is empty or null 
         *
         * @param s, a String 
         * @returns true or false (a <boolean> object)
         */
        public boolean StringEmpty(String s, String field){
            if(s == null){
                this.faultyString = field + " is missing from the configuration.";
                return true;
            }else if(s.isEmpty()){
                this.faultyString = field + " is empty.";
                return true;
            }else
                return false;
        }
        /**
         * Print appropriate message for empty or missing string
         *
         */
        public void printString(){
            System.out.println(this.faultyString);
        }
        
        /**
         * Print appropriate message for empty or missing string array
         *
         */
        public void printStringArray(){
            System.out.println(this.faultyStringArray);
        }
    }
    
    /**
     * Create a ConfigurationHandler from a configuration file
     *
     * @param file File referencing the configuration
     * @throws ConfigurationException
     */
    ConfigurationHandler(final File file) throws ConfigurationException {
        if (!file.exists()) {
            String message = "Configuration file is missing.";
            throw new IllegalArgumentException(message);
        }

        this.file = file;
        this.config = new PropertiesConfiguration(file);
        this.configCheck = new ConfigurationHandlerCheck();
    }

    /**
     * Create a ConfigurationHandler from a configuration file
     *
     * @param file String referencing the configuration file path
     * @throws ConfigurationException
     */
    ConfigurationHandler(final String path) throws ConfigurationException {
        this.file = new File(path);
        this.config = new PropertiesConfiguration(this.file);
        this.configCheck = new ConfigurationHandlerCheck();
    }

    /**
     * Create a ConfigurationHandler from a configuration file
     *
     * @param file URL referencing the configuration file
     * @throws ConfigurationException
     */
    ConfigurationHandler(final URL url) throws ConfigurationException {
        this.file = new File(url.getFile());
        this.config = new PropertiesConfiguration(this.file);
        this.configCheck = new ConfigurationHandlerCheck();
    }
    
    
    /**
     * Create a StatisticsFile object for the specified key/attribute.
     *
     * @param key the field name
     * @return a StatisticsFile containing the statistics for the key
     */
    public final StatisticsFile getStatistics(String key) {
        StatisticsFile statistics;

        String s = this.config.getString(key.toLowerCase() + ".statistics.file");
        if (this.configCheck.StringEmpty(s,key.toLowerCase() + ".statistics.file")) {
            throw new IllegalArgumentException("Cannot retrieve statistics for field " + key + ". ");
        } else {
            statistics = new StatisticsFile(new File(s));
        }
        return statistics;
    }

    /**
     * Create a Occurrences object using the settings as specified in the
     * configuration.
     *
     * @return an Occurrences object (a List<Occurrence>)
     */
    public Occurrences getOccurrences() {
        Occurrences occurrences = new Occurrences();  // occurrences to return

        String fileFormat = this.config.getString("occurrences.format");
        if (this.configCheck.StringEmpty(fileFormat,"occurrences.format")) {
            this.configCheck.printString();
            throw new IllegalArgumentException("Cannot retrieve value for field occurrences.format");
        } else {
            if (fileFormat.equalsIgnoreCase("json")) {
                String filename = this.config.getString("occurrences.file");
                if (this.configCheck.StringEmpty(filename,"occurrences.file")) {
                    this.configCheck.printString();
                    throw new IllegalArgumentException("Cannot retrieve value for field occurrences.file");
                } else {
                    
                    occurrences.loadJSON(new File(filename));
                }
            } else {
                throw new IllegalArgumentException("File format " + fileFormat + " specified in configuration is unknown.");
            }
        }
        if (occurrences.size() == 0) {
            throw new IllegalArgumentException("Occurrences file is either missing or you have specified a wrong path.");
        }
        return occurrences;
    }

    /**
     * Create a Distance object using the settings as specified in the
     * configuration. First tries the attribute-specific distance and defaults
     * to a global setting if there is no such setting.
     *
     * @param s, the name of the distance
     * @return a Distance object
     */
    public Distance<String> getDistance(String s) {
        Distance<String> distance = null;  // distance to return

        String metric = this.config.getString(s + ".distance.metric");
        String defaultMetric = this.config.getString("distance.metric");
       
        boolean globalMetric = this.configCheck.StringEmpty(defaultMetric,"distance.metric");
        boolean attributeMetric = this.configCheck.StringEmpty(metric,s + ".distance.metric") ;
        
        if (attributeMetric == true && globalMetric == true) {  // both specific attribute metric and global metric is missing/empty
            this.configCheck.printString();
            throw new IllegalArgumentException("Cannot retrieve distance.metric attribute from the configuration file.");
            //specific attribute metric is present but global metric is empty/missing
            //or both specific attribute metric and global metric are present
        }else if((attributeMetric == false && globalMetric == true) || (attributeMetric == false && globalMetric == false)){
            distance = initDistance(metric); //use specific attribute metric
        }else if(attributeMetric == true && globalMetric == false){ //specific attribute metric is missing/empty but global metric is present
            distance = initDistance(defaultMetric); //use global attribute metric
        }

        return distance;
    }

    /**
     * Get list of attributes as specified in configuration file
     *
     * @return a list of attributes names (a <String[]>)
     */
    public String[] getAttributes() {
        String[] attributes = this.configCheck.removeDuplicatedValues(this.configCheck.removeEmptyValues(config.getStringArray("attributes.list"))); // attribute list to return
        
        if (this.configCheck.StringArrayEmpty(attributes,"attributes.list")) //attribute.list attribute is empty/missing
        {
            this.configCheck.printStringArray();
            throw new IllegalArgumentException("Cannot retrieve attributes.list attribute from the configuration file.");
        } else {
            for (String attr : attributes) //validate the attributes names specified in the configuration file
            {
                
                if (attr.equals("title")) {
                    continue;
                } else if (attr.equals("forename")) {
                    continue;
                } else if (attr.equals("article")) {
                    continue;
                } else if (attr.equals("surname")) {
                    continue;
                } else if (attr.equals("role")) {
                    continue;
                } else if (attr.equals("provenance")) {
                    continue;
                } else if (attr.equals("childOf")) {
                    continue;
                } else{ //wrong attribute name. check documentation 
                    throw new IllegalArgumentException("Illegal attribute " + attr + " specified in configuration is unknown.");
                }
            }

        }

        return attributes;
    }
    
    
    
    
    /**
     * Initialize a Distance object corresponding to String s.
     *
     * @param s, the name of the distance
     * @return a <Distance> object
     */
    private Distance<String> initDistance(String s) {
        Distance<String> distance;
        float threshold = getDistanceThreshold(s);
	    //validate distance name

        if (s.equalsIgnoreCase("absolute")) {
            distance = new AbsoluteDistance(threshold);
        } else if (s.equalsIgnoreCase("levenshtein")) {
            distance = new LevenshteinDistance(threshold);
        } else if (s.equalsIgnoreCase("soundex")) {
            distance = new SoundexDistance(threshold);
        } else if (s.equalsIgnoreCase("jaro")) {
            distance = new JaroDistance(threshold);
        } else if (s.equalsIgnoreCase("dameraulevenshtein")) {
            distance = new DamerauLevenshteinDistance(threshold);
        } else if (s.equalsIgnoreCase("qgrams")) {
            distance = new QGramsDistance(threshold);
        } else { //invalidate distance name - check documentation
            throw new IllegalArgumentException("Distance metric " + s + " specified in configuration is unknown.");
        }

        return distance;
    }

    /**
     * Return the distance threshold for the attribute with key s. First tries
     * attribute-specific threshold and defaults otherwise.
     *
     * @param s
     * @return the distance's threshold (a <float> number)
     */
    public float getDistanceThreshold(String distance) {

        float f = 0.0f;
        
        String threshold = this.config.getString(distance + ".distance.threshold");
        String defaultThreshold = this.config.getString("distance.threshold");
        
        boolean globalThreshold = this.configCheck.StringEmpty(defaultThreshold,"distance.threshold");
        boolean attributeThreshold = this.configCheck.StringEmpty(threshold,distance + ".distance.threshold") ;
        
        
        if (globalThreshold == true && attributeThreshold == true) {  // both specific attribute threshold and global threshold is missing/empty
            this.configCheck.printString();
            throw new IllegalArgumentException("Cannot retrieve distance.metric attribute from the configuration file.");
            //specific attribute threshold is present but global threshold is empty/missing
            //or both specific attribute threshold and global threshold are present
        }else if((attributeThreshold == false && globalThreshold == true) || (attributeThreshold == false && globalThreshold == false)){
            f = this.config.getFloat(distance + ".distance.threshold"); //use specific attribute threshold
        }else if(attributeThreshold == true && globalThreshold == false){ //specific attribute threshold is missing/empty but global threshold is present
            f = this.config.getFloat("distance.threshold"); //use global attribute threshold
        }

        return f;
    }

    /**
     * Return the confidence threshold for the linkage results.
     *
     * @return the confidence score threshold (a <float> number)
     */
    public float getConfidenceThreshold() {
        float thrs = 0.0f;
        String s = this.config.getString("confidence.threshold"); //confidence score threshold for output
        if (this.configCheck.StringEmpty(s,"confidence.threshold")) //confidence score attribute is missing/empty
        {
            thrs = 0.0f; //use default
        } else if(Float.parseFloat(s) < 0.0f){
            thrs = 0.0f;
        }else {//confidence score attribute 
            thrs = Float.parseFloat(s);
        }

        return thrs;
    }
    /**
     * Return the maximum size of results to be stored
     *
     * @return the max size of stored results (an <int> number)
     */
    public int getResultsMaxSize(int occurrenceSize) {
        int maxSize = 0;
        String s = this.config.getString("results.maxsize"); //max size of stored results
        if (this.configCheck.StringEmpty(s,"results.maxsize")) //max size attribute is missing or empty
        {
            maxSize = occurrenceSize; //use default == size of occurrences
        } else //max size attribute
        {
            maxSize = Integer.parseInt(s);
            if(maxSize <= 0 || maxSize > occurrenceSize) //max size attribute is not correct, use default
                maxSize = occurrenceSize;
        }

        return maxSize;
    }
    /**
     * Return blocker object as specified by the configuration file.
     *
     * @return 
     */
    public Blocker getBlocker() {
        Blocker blocker = null;
        String s = this.config.getString("blocker.type");
        if (this.configCheck.StringEmpty(s,"blocker.type")) {
            blocker = new NullBlocker(); 
        } else {
            if (s.equalsIgnoreCase("firstletter")) {
                blocker = new FirstLetterBlocker();
            } else if (s.equalsIgnoreCase("nullblocker")) {
                blocker = new NullBlocker();
            } else 
                throw new IllegalArgumentException("Wrong blocker name. Illegal attribute " + s + " specified in configuration is unknown."); 
        }

        return blocker;
    }
    
    /**
     * Return RecordLinker object as specified by the configuration file.
     *
     * @return
     */
    public RecordLinker getRecordLinker() {
        RecordLinker linker = null;

        String s = this.config.getString("linker.type");
        if (this.configCheck.StringEmpty(s,"linker.type")) {
            this.configCheck.printString();
            throw new IllegalArgumentException("Cannot retrieve linker.type attribute from the configuration file.");

        } else {
            if (s.equalsIgnoreCase("naive")) {
                linker = new NaiveLinker();
            } else {
                linker = null;
                String message = "Linker type " + s + " specified in configuration is unknown.";
                throw new IllegalArgumentException(message);
            }

        }

        return linker;
    }

    /**
     * Get list of filters as specified in configuration file
     *
     * @return a list of filters (a <ArrayList<Filter> object)
     */
    public ArrayList<Filter> getFilters() {
        String[] filters = this.configCheck.removeEmptyValues(config.getStringArray("filters.list"));
        filters = this.configCheck.removeDuplicatedValues(filters);
        
        ArrayList<Filter> Filters = null;
     
        if (this.configCheck.StringArrayEmpty(filters,"filters.list") == true){ //attribute.list attribute is missing or empty
            Filters = null;
        }else {
            Filters = new ArrayList<Filter>();
            for (String filter : filters){ //validate the filters names specified in the configuration file
                if (filter.equals("basic")) { //create basic filter
                    Filters.add(new BasicFilter());
                    
                } else if (filter.equals("quantity")) { //create quantity filter
                    String s = this.config.getString("quantity.filter.threshold");
                    if(this.configCheck.StringEmpty(s,"quantity.filter.threshold")){ //quantity filter threshold is missing
                        this.configCheck.printString();
                    }else{
                        String[] attributes = this.configCheck.removeDuplicatedValues(this.configCheck.removeEmptyValues(config.getStringArray("attributes.list"))); // attribute list for linking
                        Filters.add(new QuantityFilter(Integer.parseInt(s),attributes)); 
                    }
                    
                } else if (filter.equals("quality")) {
                    String[] attributes = this.configCheck.removeDuplicatedValues(this.configCheck.removeEmptyValues(config.getStringArray("attributes.list")));
                    String[] subAttributes = this.configCheck.removeDuplicatedValues(this.configCheck.removeEmptyValues(config.getStringArray("quality.filter.attributes")));
                    
                    if(this.configCheck.StringArrayEmpty(subAttributes,"quality.filter.attributes")){ //quality.filter.attributes attribute is missing/empty
                        this.configCheck.printStringArray();
                    }else{
                        Filters.add(new QualityFilter(subAttributes,attributes));      
                    }
                    
                } else //wrong filter name. check documentation 
                    System.out.println("Wrong filter name. Illegal attribute " + filter + " specified in configuration is unknown.");
                
            }

        }
       
        return Filters;
    }
    
    /**
     * Return Output file name as specified by the configuration file.
     *
     * @return the name of the output file (a <String> object)
     */
    public String getOutputFile() {
        String file = null;
        String fileFormat = this.config.getString("output.format");
        if (this.configCheck.StringEmpty(fileFormat,"output.format")) {
            this.configCheck.printString();
            throw new IllegalArgumentException("Cannot retrieve value for field output.format");
        } else {
            if (fileFormat.equalsIgnoreCase("json")) {
                String filename = this.config.getString("output.file");
                if (this.configCheck.StringEmpty(filename,"output.file")) {
                    this.configCheck.printString();
                    throw new IllegalArgumentException("Cannot retrieve value for field output.file");
                } else  
                	file = filename;
                    
                
            } else {
                throw new IllegalArgumentException("File format " + fileFormat + " specified in configuration is unknown.");
            }
        }
        

        return file;
    }

}
