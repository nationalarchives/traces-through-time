package nl.liacs.link.recordlinker;

import nl.liacs.link.Occurrences;
import nl.liacs.link.StatisticsFile;
import nl.liacs.link.distance.Distance;
import nl.liacs.link.result.Results;
/**
 * RecordLinker abstract class
 * 
 * @author Kleanthi
 * @author Benjamin
 *
 */
public abstract class RecordLinker {

    public static final int POPULATION_SIZE = 10000;
    protected Occurrences occurrences = new Occurrences();
    

    public RecordLinker() {

    }
    /* Getters and setters */

    
    public void setOccurrences(Occurrences occurrences) {
        this.occurrences = occurrences;
    }

    
    /* Abstract: subclasses must implement these. */

    public abstract void link(Results results);
    public abstract void link(Results results, Occurrences occurr2);
    
    public abstract void addTask(String fieldName, StatisticsFile statisticsFile, Distance<String> distance);

	
}
