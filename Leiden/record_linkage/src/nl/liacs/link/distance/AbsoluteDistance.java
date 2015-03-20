package nl.liacs.link.distance;

/**
 * Absolute Distance class : Two values are considered similar if the actual
 * values are the same or if one of the values is missing
 * 
 * @author Benjamin
 * @author Kleanthi
 */

public final class AbsoluteDistance extends Distance<String> {

    public AbsoluteDistance() {
    }

    public AbsoluteDistance(final float threshold) {
        set(threshold);
    }

    @Override
    public void set(final float threshold) {
        this.threshold = threshold;
    }

    @Override
    public float get() {
        return this.threshold;
    }

    @Override
    public String print() {
        return Float.toString(this.threshold);
    }

    @Override
    public boolean isEqual(String s1, String s2) {
        boolean res;
        if (s1.isEmpty() && s2.isEmpty()) { // both empty = Equal
            res = true;
        } else if (s1.isEmpty() || s2.isEmpty()) { // one empty, other not = Equal
            res = true;
        } else {
            res = s1.equals(s2); // other cases: check to see if Equal
        }
        return res;
    }
}
