package nl.liacs.link;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;

/* Wrapper around ArrayList for clear and concise naming and easy extension. */

public class Occurrences extends ArrayList<Occurrence> {
    private static final long serialVersionUID = 1L;

    /* Constructors are copies of those of ArrayList */
    public Occurrences() {
        super();
    }
    
    Occurrences(int initialCapacity) {
        super(initialCapacity);
    }
    
    public Occurrences(Collection<Occurrence> c) {
        super(c);
    }

	/** 
 	 * Load, read and save a set of occurrences included in <file>
 	 * @param name of occurrences file (a <File> object)
 	 */
    public void loadJSON(File file) {
        BufferedReader reader = null;
        OccurrenceBase[] occurrences = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            occurrences = new Gson().fromJson(reader, OccurrenceBase[].class);
            for (int i = 0; i < occurrences.length; i++) {
                final OccurrenceBase occurrence = occurrences[i];
                
                this.add(new Occurrence(occurrence,i));
            }
        } catch (FileNotFoundException e) {
            System.out.format("FileNotFoundException : %s%n%s%n", file,
                    e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out
                            .format("IOException while closing BufferedReader(%s)%n%s%n",
                                    file, e.getMessage());
                }
            }
        }
    }
}