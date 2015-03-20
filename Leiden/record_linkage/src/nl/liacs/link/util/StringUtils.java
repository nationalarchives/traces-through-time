package nl.liacs.link.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StringUtils {

    public static String join(String[] strings, String separator) {
        StringBuilder builder = new StringBuilder();
        for (String current : strings) {
            if (current != null && current.length() != 0) {
                if (builder.length() == 0) {  // first string added
                    builder.append(current);
                } else {                      // string already started
                    builder.append(separator).append(current);
                }
            }
        }
        return builder.toString();
    }

    public static String join(String[] strings) {
        return join(strings, " ");
    }
    
    public static String repeat(String str, int repeat) {
        String retString = "";
        if (str != null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < repeat; i++) {
                builder.append(str);
            }
            retString = builder.toString();
        }
        return retString;
    }
    
    public static String repeat(char ch, int repeat) {
        String retString = "";
        if (ch != '\0') {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < repeat; i++) {
                builder.append(ch);
            }
            retString = builder.toString();
        }
        return retString;
    }
    
    public static String getTimeStamp() {
        /* Get current time. */
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}