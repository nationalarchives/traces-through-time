package nl.liacs.link.field;
/** 
 * @author Benjamin
 * @author Kleanthi
 */
public abstract class Field<T> {
    protected T data;

    /* Constructors */
    public Field() {
        this.data = null;
    }

    public Field(final T data) {
        set(data);
    }

    /* Getters and setters */
    public void set(final T data) {
        this.data = data;
    }

    public T get() {
        return this.data;
    }

    public final void clear() {
        this.data = null;
    }

    /* Unary comparisons */
    public final boolean isEmpty() {
        return this.data == null;
    }

    /* Abstract: subclasses must implement these. */
    public abstract String print();

	public abstract T LowerCase() ;
    
}