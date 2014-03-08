package progen.kernel.functions;

/**
 * Identifica el valor booleano de FALSO
 * 
 * @author jirsis
 * @since 2.0
 */
public class False extends Terminal {

    /**
     * Constructor por defecto.
     */
    public False() {
	super("boolean", "FALSE");
	super.setValue(Boolean.FALSE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.kernel.functions.Terminal#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
	//do nothing
    }

}
