package progen.kernel.error;

import java.util.Locale;
import java.util.ResourceBundle;

import progen.context.ProGenContext;
import progen.context.UninitializedPropertiesException;

/**
 * En esta clase es de donde se obtienen de una forma uniforme, los distintos
 * literales de error en los idiomas que estén disponibles, siguiendo la
 * nomenclatura ISO 639-1
 * 
 * @author jirsis
 * 
 */
public class Error {
    /**
     * Estructura de datos que accede a los literales almacenados en el
     * properties correspondiente.
     */
    private ResourceBundle literals;

    /**
     * Objeto que representa la única instancia que existirá de este objeto al
     * ser implemetado con un Singleton
     */
    private static Error error;

    /** Lengua por defecto de la que se cargarán los distintos literales. */
    private String defaultLanguage;

    /**
     * Constructor privado de la clase, en la que por defecto se cargan los
     * literales definidos en el fichero error_en_US.properties, si no se
     * especifica otro en el fichero de configuración general del programa, con
     * la propiedad <code>progen.language</code>
     */
    private Error() {
	Locale locale;
	defaultLanguage = "en_US";
	locale = new Locale(defaultLanguage);
	Locale.setDefault(locale);
	literals = ResourceBundle.getBundle("progen.kernel.error.strings");
    }

    /**
     * Método para inicializar el Singleton de esta clase.
     */
    public synchronized static void makeInstance() {
	if (error == null) {
	    error = new Error();
	    try {
		error.defaultLanguage = ProGenContext.getOptionalProperty(
			"progen.language", error.defaultLanguage);
		Locale locale = new Locale(error.defaultLanguage);
		Locale.setDefault(locale);
		error.literals = ResourceBundle.getBundle(
			"progen.kernel.error.strings", locale);
	    } catch (UninitializedPropertiesException e) {
		// do nothing, because the error has a default language
	    }
	}
    }

    /**
     * Imprime por la salida de error el literal identificado con
     * <code>errorId</code>
     * 
     * @param errorId
     *            el código de error a imprimir por pantalla
     */
    public static void println(int errorId) {
	if (error == null) {
	    makeInstance();
	}
	System.err.println("ERROR: "
		+ error.literals.getString("Error" + errorId));
    }

    /**
     * Obtiene la cadena con el texto del error identificado con
     * <code>errorId</code>
     * 
     * @param errorId
     *            número identificador del error concreto.
     * @return la cadena que corresponde con el identificador pasado por
     *         parámetro.
     */
    public static String get(int errorId) {
	if (error == null) {
	    makeInstance();
	}
	return error.literals.getString("Error" + errorId);
    }

}
