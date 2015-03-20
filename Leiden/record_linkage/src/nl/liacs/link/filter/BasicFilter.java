package nl.liacs.link.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.liacs.link.Occurrence;
import nl.liacs.link.Occurrences;
import nl.liacs.link.field.StringField;


/**
 * Class responsible for filtering out occurrences that do 
 * not include basic information such as "forename"
 * and "surname".
 * It performs a basic filtering processing.
 *
 * BasicFilter extends the class Filter
 * 
 * @author Kleanthi
 * @author Benjamin
 */
public class BasicFilter extends Filter{

    
    public BasicFilter(){
    }
   
    @Override
    /**
     * Extract occurrences whose attributes "forename" and "surname"
     * are not filled. Update occurrences set.
     */
    public void filterOccurrences() {
        
        for(Iterator<Occurrence> iter = this.occurrences.iterator(); iter.hasNext();)
        {
            Occurrence oc = iter.next();
            if(!oc.getString("forename").equals("") && !oc.getString("surname").equals(""))
                continue;
              
            else
                iter.remove();
            
                
        }

    }

}
