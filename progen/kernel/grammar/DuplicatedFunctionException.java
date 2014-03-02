package progen.kernel.grammar;

import progen.kernel.error.Error;

/**
 * Excepción que se lanza cuando se intenta cargar dos veces la misma función en un mismo function-set.
 * @author jirsis
 * @since 2.0
 */
public class DuplicatedFunctionException extends RuntimeException {

	/** Para serialización*/
	private static final long serialVersionUID = 3751712694752739425L;

	/**
	 * Constructor de la excepción que recibe el identificador 
	 * del function-set donde se definió varias veces la misma función.
	 * @param message El identificador del function-set.
	 */
	public DuplicatedFunctionException(String message) {
		super(Error.get(1)+" "+message);
	}
	
}
