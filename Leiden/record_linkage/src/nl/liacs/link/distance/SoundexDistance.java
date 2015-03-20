package nl.liacs.link.distance;

/**
 * Soundex Distance class Two values are considered similar if the homophones
 * values are the same or if one of the values is missing
 */
public final class SoundexDistance extends Distance<String> {

    public SoundexDistance() {

    }

    public SoundexDistance(final float threshold) {
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

    /**
     * Compute soundex code for a letter
     *
     * @param <char> c
     * @return corresponding soundex number as String
     */
    private static String getCode(char c) {
        switch (c) {
            case 'B':
            case 'F':
            case 'P':
            case 'V':
                return "1";
            case 'C':
            case 'G':
            case 'J':
            case 'K':
            case 'Q':
            case 'S':
            case 'X':
            case 'Z':
                return "2";
            case 'D':
            case 'T':
                return "3";
            case 'L':
                return "4";
            case 'M':
            case 'N':
                return "5";
            case 'R':
                return "6";
            default:
                return "";
        }
    }

    /**
     * Compute soundex code for field
     *
     * @param s
     * @return corresponding soundex code as String
     */
    public static String soundexCode(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        String previous = "";

        /* Add capitalized first letter as beginning of the hash. */
        if (s.length() > 0) {
            stringBuilder.append(Character.toUpperCase(s.charAt(0)));
        }

        for (int i = 1; i < s.length(); i++) {
            String current = getCode(s.toUpperCase().charAt(i));
            if (current.length() > 0 && !current.equals(previous)) {
                stringBuilder.append(current);
            }
            previous = current;
        }

        stringBuilder.append("0000");
        return stringBuilder.toString().substring(0, 4);
    }

    @Override
    public boolean isEqual(String s1, String s2) {
        String str1 = soundexCode(s1);// convert attributes into soundex code
        String str2 = soundexCode(s2);

        boolean res;
        if (str1.equals("0000") && str2.equals("0000")) { // both empty = Equal
            res = true;
        } else if (str1.equals("0000") || str2.equals("0000")) { // one empty, other not = equal
            res = true;
        } else {
            res = str1.equals(str2); // other cases: check to see if Equal
        }

        return res;
    }
}
