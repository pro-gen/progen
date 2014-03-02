package progen.context;

import progen.kernel.error.Error;

/**
 * Excepción que es lanzada cuando se detecte que hay propiedades definidas varias veces 
 * en los distintos ficheros de configuración.
 * 
 * @author jirsis 
 *
 */
public class DuplicatedPropertyException extends RuntimeException {

	/**
	 * Para serialización.
	 */
	private static final long serialVersionUID = 2058933988829590605L;

	
	/**
	 * Constructor de la clase. Recibe el nombre de la propiedad que está duplicada.
	 * @param message Propiedad duplicada.
	 */
	public DuplicatedPropertyException(String message) {
		super(Error.get(17).trim()+ "("+message+")");
	}

}
