package nl.liacs.link.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import nl.liacs.link.Occurrence;
import nl.liacs.link.Occurrences;


/**
 * Class responsible for filtering occurrences out that 
 * have less than <maxFilledAttributes> attributes filled.
 * 
 *
 * QuantityFilter extends the class Filter
 * 
 * @author Kleanthi
 * @author Benjamin
 */
public class QuantityFilter extends Filter{
    
    private int maxFilledAttributes = 0; //number of attributes that should be filled in order to keep an occurrence
    private ArrayList<String> attributes = new ArrayList<String>();  //attributes taking place in the linking process
    
    
    public QuantityFilter(int maxFilledAttributes, String[] attributes){
        this.attributes = new ArrayList<String>(Arrays.asList(attributes));
        setmaxFilledAttributes(maxFilledAttributes);
        
        
    }
    public void setmaxFilledAttributes(int maxFilledAttributes){
        if(maxFilledAttributes > 0 && maxFilledAttributes <= this.attributes.size())
            this.maxFilledAttributes = maxFilledAttributes;
        else
            this.maxFilledAttributes = 1;
    }
    @Override
    /**
     * Extract occurrences with less than <maxFilledAttributes>
     * filled. Update occurrences set.
     */
    
    public void filterOccurrences() {
        
        
        for(Iterator<Occurrence> iter = this.occurrences.iterator(); iter.hasNext();)
        {
            Occurrence oc = iter.next();
            int filledAttributes = 0;
            for(String attribute : this.attributes){ //iterate over the set of attributes that are included in the linking process
                if(!oc.getString(attribute).equals(""))//count the number of attributes that are filled with a value
                    filledAttributes++; 
            }
            
            if(filledAttributes < this.maxFilledAttributes) //if the number of filled attributes is less than the
                iter.remove();                      //maxFilledAttributes threshold, then the occurrence will be filtered out
   
        }
        
    }

}
