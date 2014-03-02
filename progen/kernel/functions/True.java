package progen.kernel.functions;

/**
 * Identifica el valor booleano de VERDADERO
 * @author jirsis
 * @since 2.0
 */
public class True extends Terminal{

	/**
	 * Constructor por defecto.
	 */
	public True() {
		super("boolean", "TRUE");
		super.setValue(Boolean.TRUE);
	}

}
