package nl.liacs.link.blocking;
import java.util.ArrayList;

import nl.liacs.link.Occurrences;
/**
 * Class responsible for dividing occurrences into blocks.
 * It allows to compare occurrences that only belong to 
 * the same block. Each subclass divides the set of 
 * occurrences using a different criterion. 
 * Each block of occurrences is processed separately.
 *
 * Blocking techniques minimise the computational time 
 * needed in order to produce linking results. It assumes that
 * occurrences of different blocks are highly unlikely
 * to refer to the same entity.
 * 
 * 
 * @author Benjamin
 * @author Kleanthi
 */


public abstract class Blocker {
    protected ArrayList<Occurrences> blocks = new ArrayList<Occurrences>();
    protected Occurrences occurrences = new Occurrences();
    protected int currentBlock = -1;
    protected int blocksNumber = -1;
    
    public Blocker(){
        
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
    
    /**
     * Assign a new set of occurrences
     *
     * @param occurrences, set of occurrences after filtering is performed
     */
    public void setBlocks(ArrayList<Occurrences> blocks){
        this.blocks = new ArrayList<Occurrences>(blocks);
    }
    
    
    /**
     * Return the set of blocks of occurrences
     *
     * @return set of blocks (an ArrayList<Occurrences> object) 
     */
    public ArrayList<Occurrences> getBlocks(){
        return this.blocks;
    }
    
    public abstract void createBlocks();
    public abstract ArrayList<Occurrences> getNextBlocks();
    public abstract int getBlocksNumber();
}
