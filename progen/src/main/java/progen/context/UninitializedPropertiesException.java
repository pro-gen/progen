package progen.context;

import progen.kernel.error.Error;
/**
 * Excepción que se lanza cuando se ejecuta cualquier método de ProGenProperties y no 
 * se ha inicializado antes.
 * @author jirsis
 *
 * @see progen.context.ProGenContext#makeInstance(String)
 */
public class UninitializedPropertiesException extends RuntimeException {

	/** Para serialización. */
	private static final long serialVersionUID = -1926903181381689034L;

	/**
	 * Constructor genérico de la excepción. 
	 */
	public UninitializedPropertiesException() {
		super(Error.get(19).trim());
	}


}
