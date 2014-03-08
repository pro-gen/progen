/**
 * 
 */
package progen.context;

import progen.kernel.error.Error;
/**
 * Excepción que es lanzada cuando una opción es definida de forma errónea.
 * 
 * @author jirsis
 *
 */
public class MalformedParameterException extends RuntimeException {

	/** Para serialización. */
	private static final long serialVersionUID = -1647531135847340217L;

	/**
	 * Constructor de la excepción. Recibe el nombre de la propiedad que 
	 * está mal construída.
	 * @param message
	 */
	public MalformedParameterException(String message) {
		super(Error.get(8).trim()+" ("+message+")");
	}

}
