package progen.context;

import progen.kernel.error.Error;

/**
 * Excepción que es lanzada cuando se define una opción cuyo valor no es uno de los 
 * admitidos como válido.
 * @author jirsis
 *
 */
public class UnknownPropertyException extends RuntimeException {

	private static final long serialVersionUID = -19384753823145179L;

	/**
	 * Constructor de la excepción que recibe como parámetro el conjunto de 
	 * valores válidos para la opción correspondiente.
	 * 
	 * @param message Conjunto de valores válidos.
	 */
	public UnknownPropertyException(String message) {
		super(Error.get(6) + "["+message+"]");
	}

}
