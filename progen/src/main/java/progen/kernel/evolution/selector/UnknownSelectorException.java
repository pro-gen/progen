/**
 * 
 */
package progen.kernel.evolution.selector;

import progen.kernel.error.Error;

/**
 * Excepción que se lanza cuando se intenta instanciar un selector que no existe.
 * 
 * @author jirsis
 * @since 2.0
 */
public class UnknownSelectorException extends RuntimeException {

	/** Para serialización */
	private static final long serialVersionUID = -2978138289477720126L;

	/**
	 * Constructor de la excepción en la que se recibe el nombre del selector
	 * que se intentó instanciar.
	 * @param name Nombre del selector que no existe.
	 */
	public UnknownSelectorException(String name) {
		super(Error.get(24).trim()+" ("+name+")");
	}


}
