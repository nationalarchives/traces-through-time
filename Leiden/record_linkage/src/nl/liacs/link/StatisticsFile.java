package nl.liacs.link;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * StatisticsFile class handles the statistics file for the set of linking attributes.
 *
 * @author Benjamin
 * @author Kleanthi
 */
public class StatisticsFile extends HashMap<String, Float> {
    private static final long serialVersionUID = 1L;
    private static final String DELIMITER = "\t";

    StatisticsFile() {
        super();
    }
	
    StatisticsFile(int initialCapacity) {
        super(initialCapacity);
    }

    StatisticsFile(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    StatisticsFile(Map<String, Float> m) {
        super(m);
    }

    /**
     * Create from a delimited file consisting of two columns.
     * @param file
     */
    StatisticsFile(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(DELIMITER);
                if (columns.length == 2) {
                    this.put(columns[0].toLowerCase(), Float.parseFloat(columns[1]));
                } else {
                    System.out.println("Error: invalid input file.");
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	/**
	 * Find and return the probability of a field's value
	 * @param key, the value of the field
	 * @return the probability of the corresponding field value (a <Float> number)
	 */
    @Override
    public Float get(Object key) {
        String s = (String)key;
        return super.get(s.toLowerCase());
    }
	/**
	 * Check if there is a priori knowledge of a field's statistics
	 * @param key, the value of the field
	 * @return <boolean> value
	 */
    @Override
    public boolean containsKey(Object key) {
        String s = (String)key;
        return super.containsKey(s.toLowerCase());
    }
}