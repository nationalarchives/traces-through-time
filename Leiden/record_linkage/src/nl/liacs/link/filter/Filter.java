package nl.liacs.link.filter;

import nl.liacs.link.Occurrences;
/**
 * Class responsible for filtering occurrences that do 
 * not satisfy particular conditions.
 *
 * Instead of performing linking using all the occurrences
 * the Filter class identify occurrences that provide
 * insufficient information.
 * 
 * @author Kleanthi
 * @author Benjamin
 */

public abstract class Filter {
    
    protected Occurrences occurrences = new Occurrences(); 
    
    /**
     * Create a Filter variable
     *
     */
    public Filter(){
       
    }
    
    /* Getters and setters */
    
    /**
     * Return the set of occurrences
     *
     * @return set of occurrences (a <Occurrences> object) 
     */
    public Occurrences getOccurrences() {
        return this.occurrences;
    }
    
    /**
     * Assign a new set of occurrences
     *
     * @param occurrences, set of occurrences after filtering is performed
     */
    public void setOccurrences(Occurrences occurrences) {
        this.occurrences = new Occurrences(occurrences);
    
    }
    
    
    /* Abstract: subclasses must implement these. */
    public abstract void filterOccurrences();
    
}
