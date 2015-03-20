package nl.liacs.link;

import java.util.*;
import java.util.Map.Entry;

import nl.liacs.link.exception.UnhandledFieldException;
import nl.liacs.link.field.IntegerField;
import nl.liacs.link.field.StringField;
import nl.liacs.link.util.StringUtils;
/**
 * Occurrence class :
 * Instead of having many different fields, HashMaps for each Type are stored now.
 * This lessens the amount of maintenance on the Occurrence class when new fields
 * are added or old ones removed.
 * 
 * @author Benjamin
 * @author Kleanthi
 */

public class Occurrence {
    static int _counts = 0;

    private Map<String, StringField> strings = new HashMap<>();
    private Map<String, IntegerField> integers = new HashMap<>();
	

    /* The fields of the new Occurrence instance are initialised using the Key and Type as
     * specified in OccurrenceBase. The values are retrieved from OccurrenceBase which is
     * constructed by the GSON library.
     *
     */
    public Occurrence(final OccurrenceBase occurrence, int ID) {
        Class<OccurrenceBase> c = OccurrenceBase.class;
        for (java.lang.reflect.Field field : c.getFields()) {
            
            try {
                if (field.getType() == String.class) {
                	this.strings.put(field.getName(), new StringField((String) field.get(occurrence)));
                    
                } else if (field.getType() == Integer.class) {
                    String s;
                    if (field.get(occurrence) == null) {
                        s = null;
                    } else {
                        s = field.get(occurrence).toString();
                    }
                    this.integers.put(field.getName(), new IntegerField(s));
                } 
                else {
                     System.out.println(field.getType());
                     throw new UnhandledFieldException();
                }
                
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Find and return the value of string field
     *
     * @param key, name of the string field
     * @return the value of the corresponding key (a <String> object)
     */

    public String getString(String key) {
        String s;

        if (this.strings.containsKey(key)) {
            s = this.strings.get(key).get();
        } else {
            s = "";  // default value
        }

        return s;
    }

    /**
     * Find and return the value of int field
     *
     * @param key, name of the int field
     * @return the value of the corresponding key (a <int> object)
     */
    public int getInteger(String key) {
        int i;

        if (this.integers.containsKey(key)) {
            i = this.integers.get(key).get();
        } else {
            i = -1;
        }
        return i;
    }
    /**
     * Compare two occurrences, return result (true or false)
     *
     * @param oc, the second Occurrence
     * @return true or false
     */
    public final boolean sameEntity(Occurrence oc) {
        boolean b;

        String id1 = this.getString("id");
        String id2 = oc.getString("id");
        if (id1.equals("") || id2.equals("")) {
            b = false;
        } else {
            b = (id1.equals(id2));
        }

        return b;
    }

    // gender
    public enum Gender {

        MALE, FEMALE, UNKNOWN;

        // also returns true on null
        static final boolean genderClash(final Gender one, final Gender other) {
            return (one == Gender.MALE && other == Gender.FEMALE)
                    || (one == Gender.FEMALE && other == Gender.MALE);
        }
    };

    
    
}
