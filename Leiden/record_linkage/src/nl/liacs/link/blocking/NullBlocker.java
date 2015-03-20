package nl.liacs.link.blocking;

import java.util.ArrayList;
import java.util.Iterator;

import nl.liacs.link.Occurrence;
import nl.liacs.link.Occurrences;
/**
 * Class that includes all occurrences in one block.
 * An instance of this class is created when the user 
 * intentionally parameterize the blocker.type with "nullblocker"
 * or
 * unintentionally leaves the blocker.type empty
 * 
 * @author Benjamin
 * @author Kleanthi
 */
public class NullBlocker extends Blocker{

    public NullBlocker(){
        
    }
    
    /**
     * Create one block that includes all occurrences
     * 
     */
    @Override
    public void createBlocks() {
        this.blocks.add(0,new Occurrences());
        for(Iterator<Occurrence> iter = this.occurrences.iterator(); iter.hasNext();){
            Occurrence oc = iter.next();
            this.blocks.get(0).add(oc);
            iter.remove();
        }
        
        
    }
    /**
     * Get next block of occurrences
     * @return nextOccurrences, the next block of occurrences (an <Occurrences> object)
     */
    @Override
    public ArrayList<Occurrences> getNextBlocks() {
        ArrayList<Occurrences> currentOccurrences = new ArrayList<Occurrences>(2);
        if(this.currentBlock + 1 < this.blocks.size()){
            this.currentBlock++;
            currentOccurrences.add(0,this.blocks.get(this.currentBlock));
            currentOccurrences.add(1,null);
            return currentOccurrences;
            
        }else
            return null;
    }
    /**
     * Get number of blocks
     * @return blocksNumber, number of blocks 
     */
    @Override
    public int getBlocksNumber() {
        this.blocksNumber = this.blocks.size();
        return this.blocksNumber;
    }

}
