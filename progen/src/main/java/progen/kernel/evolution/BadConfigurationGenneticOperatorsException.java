package progen.kernel.evolution;

import progen.kernel.error.Error;

/**
 * Excepción que será lanzada en aquellos casos en los que suma de porcentajes
 * de uso de los distintos operadores genéticos no sume una probabilidad del 100%.
 * 
 * @author jirsis
 *
 */
public class BadConfigurationGenneticOperatorsException extends
		RuntimeException {
	/**
	 * Para serialización.
	 */
	private static final long serialVersionUID = -2829863018944253838L;

	/**
	 * Constructor que recibe el valor máximo de la probabilidad total.
	 * 
	 * @param message Porcentaje de uso total.
	 */
	public BadConfigurationGenneticOperatorsException(double message){
		super(Error.get(12).trim()+" ("+message*100+"% != 100%)");
	}
}
