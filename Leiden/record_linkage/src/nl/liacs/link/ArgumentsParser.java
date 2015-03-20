package nl.liacs.link;

/**
 * Allows for easy configuration of arguments by the *developers* (not users).
 * NOTE: this class assumes that arguments are supplied as key-value pairs,
 * e.g. --conf /path/to/configuration.properties
 * 
 * @author Benjamin
 * @author Kleanthi
 */
public class ArgumentsParser {
    
    /* Put any flags that needs parsing here. */
    Flag[] flags = {
        new Flag("--conf", "-c")  // Path to the configuration file
    };
    
    ArgumentsParser(String[] args) {
        /* We assume that the input arguments are all key-value pairs. */
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("Provide command-line arguments as key-value pairs.");
        }
        
        /* Process arguments as pairs. */
        for (int i = 0; i < args.length; i += 2) {
            Flag flag = getFlag(args[i]);
            if (flag != null) {
                flag.setValue(args[i+1]);
            } else {
                // Non-specified flags are ignored and return an empty string on getValue()
            }
        }
    }
    
    private Flag getFlag(String flagName) {
        for (Flag flag : this.flags) {
            if (flag.check(flagName)) {
                return flag;
            }
        }
        return null;
    }
    
    private boolean isValidFlag(String flagName) {
        for (Flag flag : this.flags) {
            if (flag.check(flagName)) {
                return true;
            }
        }
        return false;
    }
    
    public String getValue(String flagName) {
        Flag flag = getFlag(flagName);
        if (flag != null) {
            return flag.getValue(flagName);
        } else {
            throw new IllegalArgumentException(flagName + " is not a valid flag.");
        }
    }
    
    /**
     * Flag is a simple key-value object with the option of adding an alias,
     * which is useful for supporting shorthand variants.
     */
    private class Flag {
        String flag;   // Name of the flag
        String alias;  // Optional alias of the flag
        String value;  // Value associated with the flag
        
        Flag(String flag, String alias) {
            this.flag = flag;
            this.alias = alias;
        }
        
        Flag(String flag) {
            this.flag = flag;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
        
        /**
         * Get the value associated with the flag.
         * @param flagName
         * @return the value associated with the flag
         */
        public String getValue(String flagName) {
            if (this.value != null) {
                return this.value;
            } else {
                return new String();
            }
        }
        
        /**
         * Check whether `token` is a key of this flag.
         * @param token
         * @return boolean indicating whether `token` is a key for this flag.
         */
        public boolean check(String token) {
            return this.flag.equals(token) || this.alias.equals(token);
        }
    }
}