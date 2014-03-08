/**
 * 
 */
package progen.output.outputers;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author jirsis
 * @since 2.0
 *
 */
public abstract class ConsoleOutput implements Outputer {
	
	/** Almacén de todos los literales de texto que aparecerán en la salida. */
	protected ResourceBundle literals;
	
	/**
	 * Constructor por defecto. Se inicializa la variable de literales.
	 */
	public ConsoleOutput(){
		literals=ResourceBundle.getBundle("progen.output.outputers.literals", Locale.getDefault());
	}

	/* (non-Javadoc)
	 * @see progen.output.outputers.Outputer#close()
	 */
	public void close() {
		//do nothing
	}

	/* (non-Javadoc)
	 * @see progen.output.outputers.Outputer#init()
	 */
	public void init() {
		//do nothing
	}
}
