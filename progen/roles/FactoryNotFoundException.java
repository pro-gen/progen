package progen.roles;

import progen.kernel.error.Error;
/**
 * Excepción que es lanzada cuando se define en el fichero de configuración maestro
 * unos valores de las propiedades 
 * <code>progen.role.factory</code> y <code>progen.role.factory.class</code>
 * que no especifican la localización de un fábrica de roles válida.
 * 
 * @author jirsis
 * @since 2.0
 */
public class FactoryNotFoundException extends RuntimeException {
	
	/** Para serialización */
	private static final long serialVersionUID = 4495563008103796113L;
	/**
	 * Constructor de la excepción en la que recibe el nombre completo de donde se esperaba
	 * encontrar la implementación de la fábrica concreta.
	 * 
	 * @param msg nombre de clase donde se encontraba la implemenación de la fábrica concreta.
	 */
	public FactoryNotFoundException(String msg) {
		super(Error.get(20));
	}

}
