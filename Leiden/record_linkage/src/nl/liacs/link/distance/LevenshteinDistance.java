package nl.liacs.link.distance;

/**
 * Levenshtein Distance class Two values are considered similar if the number of
 * single-character edits (insertions, deletions or substitutions) required to
 * transform the first value to the second value is less than a threshold.
 * 
 * @author Benjamin
 * @author Kleanthi
 */
public final class LevenshteinDistance extends Distance<String> {

    public LevenshteinDistance() {

    }

    public LevenshteinDistance(final float threshold) {
        set(threshold);
    }

    public void setThreshold(final float threshold) {
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
        int len0 = s1.length() + 1;
        int len1 = s2.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newCost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) {
            cost[i] = i;
        }

        for (int j = 1; j < len1; j++) {
            newCost[0] = j - 1;
            for (int i = 1; i < len0; i++) {
                int match = (s1.charAt(i - 1) == s2.charAt(
                        j - 1)) ? 0 : 1;

                final int costReplace = cost[i - 1] + match;
                final int costInsert = cost[i] + 1;
                final int costDelete = newCost[i - 1] + 1;

                newCost[i] = Math.min(Math.min(costInsert, costDelete),
                        costReplace);
            }
            int[] swap = cost;
            cost = newCost;
            newCost = swap;
        }
        if (cost[len0 - 1] <= this.threshold)// if the transpositions needed
        // between two fields are less than
        // the threshold
        {
            return true; // fields are accepted as equal
        } else {
            return false; // otherwise, not equal
        }
    }
}
