package nl.liacs.link.blocking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import nl.liacs.link.Occurrence;
import nl.liacs.link.Occurrences;

/**
 * Class responsible for dividing occurrences into blocks.
 * Each block contains occurrences that have the same first
 * letter in the surname attribute.
 * 
 * 
 * @author Benjamin
 * @author Kleanthi
 */

public class FirstLetterBlocker extends Blocker{
    
    //utility hashmap that maps a character to the corresponding Occurrences object in the occurrences' blocks 
    private HashMap<String, Integer> LetterToIndex = new HashMap<String, Integer>(); 
    
    
    public FirstLetterBlocker(){
        
    }
    /**
     * Get number of blocks
     * @return blocksNumber, number of blocks
     */
    @Override
    public int getBlocksNumber(){
        this.blocksNumber = this.blocks.size();
        return this.blocksNumber;
    }
    /* Internal utility getters and setters */
    /**
     * Get a block of occurrences using index
     *
     * @param blockIndex, the index of the block 
     */
    private Occurrences getBlock(Integer blockIndex){
        return this.blocks.get(blockIndex);
       
    }
    /**
     * Create a new block of occurrences
     *
     * @param blockIndex, the index of the new block in the arraylist of the blocks
     */
    private void setBlock(Integer blockIndex){
        this.blocks.add(blockIndex, new Occurrences());
       
    }
    /**
     * Add new occurrence to an existing block of occurrences
     *
     * @param blockIndex, the index of the corresponding block 
     * @param oc, the new Occurrence object 
     */
    private void setOccurrence(Occurrence oc, Integer BlockIndex){
        //create a new Occurrences block
        this.blocks.get(BlockIndex).add(oc);
    
    }
    /**
     * Get the index of a block using the utility hashmap object.
     * LetterToIndex contains pairs of (firstLetter,blockIndex)
     * that map a first letter character to the appropriate 
     * Occurrences object in the occurrences' blocks. 
     * 
     * @param firstLetter, the first letter of the surname 
     * @return the index of the block
     */
    private int getUtilityIndex(char firstLetter){
        boolean index = this.LetterToIndex.containsKey(Character.toString(firstLetter));
        if(index){
            return this.LetterToIndex.get(Character.toString(firstLetter));
        }else{
            return -1;
        }
        
    }
    /**
     * Create new entry in the utility hashmap.
     * Map a new character to a block.
     */
    private void setUtilityIndex(char firstLetter, Integer index){
        this.LetterToIndex.put(Character.toString(firstLetter), index);
   
    }
    
    /**
     * Create block with all the occurrences with surname
     * 
     * 
     */
    private Occurrences addAllOccurrences(){
        int index = getUtilityIndex('*');
        Occurrences namedOccurrences = new Occurrences();
        int currentPosition = 0;
        for(Iterator<Occurrences> iter = this.blocks.iterator(); iter.hasNext();){
            Occurrences oc = iter.next();
            if(currentPosition != index){
                namedOccurrences.addAll(oc);    
                iter.remove();
            }
            currentPosition++;
        }  
        return namedOccurrences;
    }
    
    /**
     * Separate occurrences into blocks using their first letter.
     * Use special character <*> for occurrences without a surname.
     * 
     */
    @Override
    public void createBlocks(){
        
        int index = 0;
        for(Iterator<Occurrence> iter = this.occurrences.iterator(); iter.hasNext();){
            
            Occurrence oc = iter.next();
            String surname = oc.getString("surname").toUpperCase();
            
            if(!surname.equals("")){ //the occurrence includes a value for surname
                char firstLetter = surname.charAt(0);//get first letter of surname
                //check if a block for this letter already exists
                int BlockIndex = getUtilityIndex(firstLetter);
                if(BlockIndex == -1){ //create new block for this letter and add the occurrence
                    
                    setUtilityIndex(firstLetter, index);
                    setBlock(index);
                    setOccurrence(oc, index);
                    index++;
                }else{//there is already a block for this letter
                    //retrieve the index from the utility hashmap
                    setOccurrence(oc, BlockIndex); 
                }
                
            }else{//in case the occurrence doesn't include a surname
                int BlockIndex = getUtilityIndex('*');
                
                if(BlockIndex == -1){//use special character for these occurrences, and follow the above pattern
                    
                    setUtilityIndex('*', index);
                    setBlock(index);
                    setOccurrence(oc, index);  
                    index++;
                    
                }else{
                    setOccurrence(oc, BlockIndex);
                }
                
                
            }
            iter.remove();
            
            
        }
        
    }
    
    /**
     * Get next block(s) of occurrences for linking
     * @return currentOccurrences, the next block(s) of occurrences (an <Occurrences> object)
     */
    @Override
    public ArrayList<Occurrences> getNextBlocks(){
        
        ArrayList<Occurrences> currentOccurrences = new ArrayList<Occurrences>(2);
        if(this.currentBlock + 1 < this.blocks.size()){
            this.currentBlock++;
            
            currentOccurrences.add(0,getBlock(this.currentBlock));
            currentOccurrences.add(1,null);
            return currentOccurrences;
            
        }else if(this.currentBlock + 1 == this.blocks.size()){
            int index = getUtilityIndex('*');
            this.currentBlock++;
            
            if(index != -1){
                Occurrences occ = getBlock(index);
                currentOccurrences.add(0,addAllOccurrences());
                currentOccurrences.add(1,occ);

                return currentOccurrences;
            }
            else
                return null;
            
        }else
            return null;
   
    }
    
    
}
