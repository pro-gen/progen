package progen.kernel.functions;

/**
 * Implementación concreta de un ERC que toma valores entre 0 y 1.
 * 
 * @author jirsis
 * @since 2.0
 */
public class ERC01 extends ERC {

    /**
     * Constructor por defecto.
     */
    public ERC01() {
	super("double");
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.kernel.functions.ERC#defineERC()
     */
    @Override
    protected Object defineERC() {
	return Math.random();
    }

    /*
     * (non-Javadoc)
     * @see progen.kernel.functions.ERC#printERC()
     */
    @Override
    protected String printERC() {
	return super.getValue().toString();
    }

    /*
     * (non-Javadoc)
     * @see progen.kernel.functions.ERC#clone()
     */
    @Override
    public ERC clone() {
	return new ERC01();
    }
}
