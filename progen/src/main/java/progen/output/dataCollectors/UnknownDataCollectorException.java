package progen.output.dataCollectors;

import progen.kernel.error.Error;
/**
 * Excepción que será lanzada cuando se intente crear un DataCollector que no existe en
 * la colección de los que están disponibles.
 * @author jirsis
 * @since 2.0
 * @see progen.output.dataCollectors
 */
public class UnknownDataCollectorException extends RuntimeException {

	/** Para serialización */
	private static final long serialVersionUID = -7276399548518521418L;
	
	/**
	 * Constructor de la excepción en la que se recibe como parámetro el nombre
	 * del DataCollector que se intentó instanciar.
	 * @param msg Nombre del DataCollector que se intentó instanciar.
	 */
	public UnknownDataCollectorException(String msg) {
		super(Error.get(22).trim()+"("+msg+")");
	}

	
}
