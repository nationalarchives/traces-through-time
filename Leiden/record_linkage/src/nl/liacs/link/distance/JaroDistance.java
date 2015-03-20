package nl.liacs.link.distance;

/**
 * Jaro-Winkler Distance class Two values are considered similar if the weighted
 * combination of the matching characters and transpositions is greater than a
 * threshold.
 * 
 * @author Benjamin
 * @author Kleanthi
 */
public final class JaroDistance extends Distance<String> {

    public JaroDistance() {
    }

    public JaroDistance(final float threshold) {
        set(threshold);
    }

    @Override
    public void set(final float threshold) {
        if (threshold < 0 || threshold > 1) {
            System.out.println("Wrong threshold for Jaro-Winkler");
        } else {
            this.threshold = threshold;
        }
    }

    @Override
    public float get() {
        return this.threshold;
    }

    @Override
    public String print() {
        return Float.toString(this.threshold);
    }

    /**
     * Find common characters between fields
     *
     * @param str1, str2, halflen
     * @return as String the common characters
     */
    private static String commonChars(String str1, String str2, int halflen) {
        StringBuilder common = new StringBuilder();
        StringBuilder copy = new StringBuilder(str2);
        for (int i = 0; i < str1.length(); i++) {
            char ch = str1.charAt(i);
            boolean foundIt = false;
            for (int j = Math.max(0, i - halflen); !foundIt
                    && j < Math.min(i + halflen, str2.length()); j++) {
                if (copy.charAt(j) == ch) {
                    foundIt = true;
                    common.append(ch);
                    copy.setCharAt(j, '*');
                }
            }
        }
        return common.toString();
    }

    /**
     * Jaro-Winkler distance between two fields
     *
     * @param str1, str2
     * @return as float their distance
     */
    private static double JaroWinkler(String str1, String str2) {
        int halflen = ((str1.length() > str2.length()) ? str1.length() / 2 + 1
                : str2.length() / 2 + 1);
        String common1 = commonChars(str1, str2, halflen);
        String common2 = commonChars(str2, str1, halflen);
        if (common1.length() != common2.length()) {
            return 0;
        }
        if (common1.length() == 0 || common2.length() == 0) {
            return 0;
        }

        int transpositions = 0;
        for (int i = 0; i < common1.length(); i++) {
            if (common1.charAt(i) != common2.charAt(i)) {
                transpositions++;
            }
        }
        transpositions /= 2;

        double dist = (common1.length() / ((double) str1.length())
                + common2.length() / ((double) str2.length()) + (common1
                .length() - transpositions) / ((double) common1.length())) / 3.0;
        return dist;
    }

    private static int commonPrefixLength(int maxLength, String common1,
            String common2) {
        int n = Math.min(maxLength,
                Math.min(common1.length(), common2.length()));
        for (int i = 0; i < n; i++) {
            if (common1.charAt(i) != common2.charAt(i)) {
                return i;
            }
        }
        return n; // first n characters are the same
    }

    @Override
    public boolean isEqual(String s1, String s2) {
        double dist = JaroWinkler(s1, s2);//

        int prefLength = commonPrefixLength(4, s1, s2);
        dist = dist + prefLength * 0.1 * (1 - dist);

        if (s1.isEmpty() && s2.isEmpty()) { // both empty = Equal
            return true;
        } else if (s1.isEmpty() || s2.isEmpty()) { // one empty, other not
            // = Equal
            return true;
        } else {
            if (dist >= this.threshold) { // if the distance between two fields
                // is less than the threshold
                return true; // assume fields are equal
            } else { // otherwise, not equal
                return false;
            }
        }
    }
}
