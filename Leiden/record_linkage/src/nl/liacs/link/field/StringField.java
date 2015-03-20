package nl.liacs.link.field;

/** 
 * @author Benjamin
 * @author Kleanthi
 */
public class StringField extends Field<String> {
    public StringField() {
    }

    public StringField(final String string) {
        set(string);
        
    }

    /* The meaning of the empty string and null are the same, i.e. missing data. */
    public void set(final String string) {
        if (string != null && string.isEmpty()) {
            this.data = null;
        } else {
            this.data = string;
        }
        
	
    }
	
	 /* Getters and setters */
    public String get()
    {
        if (this.data == null)
            return "";
        else
            return this.data;
    }
    public String print() {
        String s;
        if (this.data == null) {
            s = "";
        } else {
            s = this.data;
        }
        return s;
    }

    
    /**
    *
    * @return  Convert field into low letters. Useful for titles
    */
    public String LowerCase() {
        return this.data.toLowerCase();
    }

 
    
}