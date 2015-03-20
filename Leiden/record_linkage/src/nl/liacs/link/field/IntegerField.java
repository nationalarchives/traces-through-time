package nl.liacs.link.field;

/** 
 * @author Benjamin
 * @author Kleanthi
 */
public class IntegerField extends Field<Integer> {
    public IntegerField() {

    }

    public IntegerField(final int data) {
        super(data);
    }

    public IntegerField(final String data) {
        set(data);
    }
     /* Getters and setters */
    public void set(final String data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = Integer.parseInt(data);
        }
    }
    public Integer get()
    {
        if (this.data == null)
            return -1;
        else
            return this.data;
    }
    public String print() {
        return Integer.toString(this.data);
    }

    @Override
    public Integer LowerCase() {
        // TODO Auto-generated method stub
        return null;
    }    
}