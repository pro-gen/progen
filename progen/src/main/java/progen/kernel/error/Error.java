package progen.kernel.error;

import java.util.Locale;
import java.util.ResourceBundle;

import progen.context.ProGenContext;
import progen.context.UninitializedContextException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * En esta clase es de donde se obtienen de una forma uniforme, los distintos
 * literales de error en los idiomas que estén disponibles, siguiendo la
 * nomenclatura ISO 639-1
 * 
 * @author jirsis
 */
public final class Error {

  private static final String ERROR_LITERAL = "Error";

  private static final String PROGEN_KERNEL_ERROR_STRINGS_BUNDLE = "progen.kernel.error.strings";

  /**
   * Lenguaje por defecto de la que se cargarán los distintos literales.
   */
  private static final String DEFAULT_LANGUAGE = "en_US";

  /**
   * Objeto que representa la única instancia que existirá de este objeto al ser
   * implemetado con un Singleton
   */
  private static Error error;

  /**
   * Estructura de datos que accede a los literales almacenados en el properties
   * correspondiente.
   */
  private ResourceBundle literals;

  /**
   * Constructor privado de la clase, en la que por defecto se cargan los
   * literales definidos en el fichero error_en_US.properties, si no se
   * especifica otro en el fichero de configuración general del programa, con la
   * propiedad <code>progen.language</code>
   */
  private Error() {
    final Locale locale = new Locale(DEFAULT_LANGUAGE);
    Locale.setDefault(locale);
    literals = ResourceBundle.getBundle(PROGEN_KERNEL_ERROR_STRINGS_BUNDLE);
  }

  /**
   * Método para inicializar el Singleton de esta clase.
   */
  @SuppressFBWarnings("ISC_INSTANTIATE_STATIC_CLASS")
  public static synchronized void makeInstance() {
    if (error == null) {
      error = new Error();
      try {
        final String defaultLanguage = ProGenContext.getOptionalProperty("progen.language", DEFAULT_LANGUAGE);
        System.err.println(defaultLanguage);
        final Locale locale = new Locale(defaultLanguage);
        Locale.setDefault(locale);
        error.literals = ResourceBundle.getBundle(PROGEN_KERNEL_ERROR_STRINGS_BUNDLE, locale);
      } catch (UninitializedContextException e) {
        // do nothing, because the error has a default language
      }
    }
  }

  /**
   * Imprime por la salida de error el literal identificado con
   * <code>errorId</code>
   * 
   * @param errorId
   *          el código de error a imprimir por pantalla
   */
  public static void println(int errorId) {
    if (error == null) {
      makeInstance();
    }
    System.err.println("ERROR: " + error.literals.getString(ERROR_LITERAL + errorId));
  }

  /**
   * Obtiene la cadena con el texto del error identificado con
   * <code>errorId</code>
   * 
   * @param errorId
   *          número identificador del error concreto.
   * @return la cadena que corresponde con el identificador pasado por
   *         parámetro.
   */
  public static String get(int errorId) {
    if (error == null) {
      makeInstance();
    }
    return error.literals.getString(ERROR_LITERAL + errorId);
  }

  public static void destroyMe() {
    error = null;
  }

}
