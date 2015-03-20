package nl.liacs.link.exception;

public class InitializationException extends RuntimeException {

    /**
     * @author Benjamin
     * @author Kleanthi
     */
    private static final long serialVersionUID = 1L;
    
    public InitializationException() {
        super();
    }
    
    public InitializationException(String message) {
        super(message);
    }

}
