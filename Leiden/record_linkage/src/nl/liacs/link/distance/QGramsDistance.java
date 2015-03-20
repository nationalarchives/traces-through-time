package nl.liacs.link.distance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Damerau Levenshtein Distance class Two values are considered similar if the
 * normalized sum of the equal tri-gram between the vectors of the two values is
 * greater than a threshold.
 * 
 * @author Benjamin
 * @author Kleanthi
 */
public class QGramsDistance extends Distance<String> {

    public QGramsDistance() {

    }

    public QGramsDistance(final float Thrs) {
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

    /**
     * gets the similarity of the two strings using QGramsDistance.
     *
     * @param string1
     * @param string2
     * @return a value between 0-1 of the similarity
     */
    public boolean QGrams(final String string1, final String string2) {
        final ArrayList<String> str1Tokens = tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokenizeToArrayList(string2);

        final int maxQGramsMatching = str1Tokens.size() + str2Tokens.size();

        //return
        if (maxQGramsMatching == 0) {
            return false;
        } else {
            float dist = (maxQGramsMatching - getUnNormalisedSimilarity(string1, string2)) / (float) maxQGramsMatching;
            return dist >= this.threshold;
        }
    }

    /**
     * gets the un-normalised similarity measure of the metric for the given
     * strings.
     *
     * @param string1
     * @param string2
     *
     * @return returns the score of the similarity measure (un-normalised)
     */
    public float getUnNormalisedSimilarity(String string1, String string2) {
        final ArrayList<String> str1Tokens = tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokenizeToArrayList(string2);

        final Set<String> allTokens = new HashSet<>();
        allTokens.addAll(str1Tokens);
        allTokens.addAll(str2Tokens);

        final Iterator<String> allTokensIt = allTokens.iterator();
        int difference = 0;
        while (allTokensIt.hasNext()) {
            final String token = allTokensIt.next();
            int matchingQGrams1 = 0;
            for (String str1Token : str1Tokens) {
                if (str1Token.equals(token)) {
                    matchingQGrams1++;
                }
            }
            int matchingQGrams2 = 0;
            for (String str2Token : str2Tokens) {
                if (str2Token.equals(token)) {
                    matchingQGrams2++;
                }
            }
            if (matchingQGrams1 > matchingQGrams2) {
                difference += (matchingQGrams1 - matchingQGrams2);
            } else {
                difference += (matchingQGrams2 - matchingQGrams1);
            }
        }
        return difference;
    }

    @Override
    public String print() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEqual(String attr1, String attr2) {
        return QGrams(attr1, attr2);
    }

    /**
     * Return tokenized version of a string.
     *
     * @param input
     * @return tokenized version of a string
     */
    public static ArrayList<String> tokenizeToArrayList(final String input) {
        final ArrayList<String> returnVect = new ArrayList<>();

        int curPos = 0;
        final int length = input.length() - 2;
        while (curPos < length) {
            final String term = input.substring(curPos, curPos + 3);
            returnVect.add(term);
            curPos++;
        }

        return returnVect;
    }
}
