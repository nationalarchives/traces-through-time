package nl.liacs.link.distance;

import java.util.HashMap;

/**
 * Damerau Levenshtein Distance class It is a modification of the Levenshtein
 * Distance, that additionally defines as a possible single-character edit the
 * transposition of two adjacent characters.
 * 
 * @author Benjamin
 * @author Kleanthi
 */
public final class DamerauLevenshteinDistance extends Distance<String> {

    public DamerauLevenshteinDistance() {

    }

    public DamerauLevenshteinDistance(final float Thrs) {
        set(Thrs);
    }

    public void set(final String Thrs) {
        if (Thrs == null) {
            this.threshold = -1.0f;
        } else {
            this.threshold = Float.parseFloat(Thrs);
        }
    }

    @Override
    public float get() {
        return this.threshold;
    }

    public boolean DL(String str1, String str2) {

        final int INFINITY = str1.length() + str2.length();
        int[][] H = new int[str1.length() + 2][str2.length() + 2];
        H[0][0] = INFINITY;

        for (int i = 0; i <= str1.length(); i++) {
            H[i + 1][1] = i;
            H[i + 1][0] = INFINITY;
        }
        for (int j = 0; j <= str2.length(); j++) {
            H[1][j + 1] = j;
            H[0][j + 1] = INFINITY;
        }

        HashMap<Character, Integer> DA = new HashMap<>();
        for (int q = 0; q < str1.length(); q++) {
            if (!DA.containsKey(str1.charAt(q))) {
                DA.put(str1.charAt(q), 0);
            }
        }

        for (int q = 0; q < str2.length(); q++) {
            if (!DA.containsKey(str2.charAt(q))) {
                DA.put(str2.charAt(q), 0);
            }
        }

        for (int i = 1; i <= str1.length(); i++) {
            int DB = 0;
            for (int j = 1; j <= str2.length(); j++) {

                int i1 = DA.get(str2.charAt(j - 1));
                int j1 = DB;
                int d = ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1);
                if (d == 0) {
                    DB = j;
                }
                H[i + 1][j + 1] = Math.min(Math.min(Math.min(H[i][j] + d, //substitution
                        H[i + 1][j] + 1), // insertion
                        H[i][j + 1] + 1), // deletion
                        H[i1][j1] + (i - i1 - 1) + 1 + (j - j1 - 1));
            }
            DA.put(str1.charAt(i - 1), i);
        }
        if (H[str1.length() + 1][str2.length() + 1] <= this.threshold)//if the transpositions needed between two fields are less than the threshold
        {
            return true;		//fields are accepted as equal
        } else {
            return false;	//otherwise, not equal
        }
    }

    @Override
    public String print() {
        return Float.toString(this.threshold);
    }

    @Override
    public boolean isEqual(String str1, String str2) {
        return DL(str1, str2);
    }
}
