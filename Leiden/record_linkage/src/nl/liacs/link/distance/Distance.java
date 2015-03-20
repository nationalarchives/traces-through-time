package nl.liacs.link.distance;

/**
 * Distance class : Abstract class
 * 
 * @author Benjamin
 * @author Kleanthi
 *
 */
public abstract class Distance<T> {

    protected float threshold = -1.0f;

    public Distance() {
    }

    public Distance(final float threshold) {
        set(threshold);
    }

    /* Getters and setters */

    /*
     * set threshold for accepting two attributes as equal 
     * set to 0 for absolute and soundex
     */
    public void set(final float threshold) {
        this.threshold = threshold;
    }

    /**
     *
     * @return return Threshold for comparison (as float).
     */
    public float get() {
        return this.threshold;
    }

    public final void clear() {
        this.threshold = -1;
    }

    /* Unary comparisons */
    public final boolean isEmpty() {
        return (this.threshold == -1);
    }

    /* Abstract: subclasses must implement these. */
    public abstract String print();

    public abstract boolean isEqual(T attr1, T attr2);
}
