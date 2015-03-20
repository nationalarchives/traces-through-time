package nl.liacs.link.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import nl.liacs.link.Occurrence;
import nl.liacs.link.Occurrences;


/**
 * Class responsible for filtering out occurrences that 
 * do not have values for a particular subset of attributes (<obligatoryAttributes>).
 * <obligatoryAttributes> is predefined by the user.
 * 
 * QualityFilter extends the class Filter
 * 
 * @author Kleanthi
 * @author Benjamin
 */
public class QualityFilter extends Filter{
    
    
    private ArrayList<String> obligatoryAttributes = new ArrayList<String>(); //subset of attributes that must be filled in an occurrence
    private ArrayList<String> attributes = new ArrayList<String>();
    
    public QualityFilter(String[] obligatoryAttributes, String[] attributes){
        setAttributes(obligatoryAttributes, attributes);  
    }    
    
    public void setAttributes(String[] obligatoryAttributes, String[] attributes){
        ArrayList<String> subAttributes = new ArrayList<String>(Arrays.asList(obligatoryAttributes));
        ArrayList<String> fullAttributes = new ArrayList<String>(Arrays.asList(attributes));  
       
        this.attributes = fullAttributes;
        boolean isSubset = this.attributes.containsAll(subAttributes); 
        if (isSubset){//check if the set of filtering attributes are also included in the attribute list used for linking
            this.obligatoryAttributes = new ArrayList<String>(subAttributes);
        }                               
        else{ //wrong attribute name or empty quality.filter.attributes list. check documentation  
            this.obligatoryAttributes = null;
        } 
    }
    
    @Override
    public void filterOccurrences() {
       
        if(this.obligatoryAttributes == null)
            return;
        
        for(Iterator<Occurrence> iter = this.occurrences.iterator(); iter.hasNext();)
        {
            Occurrence oc = iter.next();
            int filledAttributes = 0;
            for(String attribute : this.obligatoryAttributes){ //iterate over the subset of obligatory attributes 
                if(!oc.getString(attribute).equals("")) //count the number of these attributes that are filled with a value
                    filledAttributes++;
            }
           
            if(filledAttributes != this.obligatoryAttributes.size())//if the occurrence doesn't include values for all the 
                iter.remove();                      //obligatory attributes, then it is excluded from the linking process   
          
        }
        
    }

}
