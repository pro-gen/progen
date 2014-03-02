package progen.kernel.functions;

import java.util.Random;

/**
 * ERC que define un valor aleatorio de 32 bits.
 * 
 * @author David Fernandez García & César Estébanez Tascón
 * @since 1.0
 * @version 2.0
 * 
 */
public class Bit32ERC extends ERC {

    /**
     * Constructor que especifica que este ERC será de tipo int
     */
    public Bit32ERC() {
	super("int");
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.kernel.functions.ERC#defineERC()
     */
    @Override
    protected Object defineERC() {
	return new Integer(new Random().nextInt());
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.kernel.functions.ERC#printERC()
     */
    @Override
    protected String printERC() {
	return "0x" + Integer.toHexString((Integer) getValue());
    }

    /*
     * (non-Javadoc)
     * @see progen.kernel.functions.ERC#clone()
     */
    @Override
    public ERC clone() {
	return new Bit32ERC();
    }

}
