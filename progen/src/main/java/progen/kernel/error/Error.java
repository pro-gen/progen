package progen.kernel.error;

import progen.context.ProGenContext;
import progen.context.UninitializedPropertiesException;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * En esta clase es de donde se obtienen de una forma uniforme, los distintos
 * literales de error en los idiomas que estén disponibles, siguiendo la
 * nomenclatura ISO 639-1
 *
 * @author jirsis
 */
public class Error {

  /**
   * Objeto que representa la única instancia que existirá de este objeto al
   * ser implemetado con un Singleton
   */
  private static Error error;

  /**
   * Estructura de datos que accede a los literales almacenados en el
   * properties correspondiente.
   */
  private ResourceBundle literals;

  /**
   * Lenguaje por defecto de la que se cargarán los distintos literales.
   */
  private static final String DEFAULT_LANGUAGE = "en_US";

  /**
   * Constructor privado de la clase, en la que por defecto se cargan los
   * literales definidos en el fichero error_en_US.properties, si no se
   * especifica otro en el fichero de configuración general del programa, con
   * la propiedad <code>progen.language</code>
   */
  private Error() {
    Locale locale = new Locale(DEFAULT_LANGUAGE);
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
        String defaultLanguage= ProGenContext.getOptionalProperty("progen.language", DEFAULT_LANGUAGE);
        Locale locale = new Locale(defaultLanguage);
        Locale.setDefault(locale);
        error.literals = ResourceBundle.getBundle("progen.kernel.error.strings", locale);
      } catch (UninitializedPropertiesException e) {
        // do nothing, because the error has a default language
      }
    }
  }

  /**
   * Imprime por la salida de error el literal identificado con
   * <code>errorId</code>
   *
   * @param errorId el código de error a imprimir por pantalla
   */
  public static void println(int errorId) {
    if (error == null) {
      makeInstance();
    }
    System.err.println("ERROR: " + error.literals.getString("Error" + errorId));
  }

  /**
   * Obtiene la cadena con el texto del error identificado con
   * <code>errorId</code>
   *
   * @param errorId número identificador del error concreto.
   * @return la cadena que corresponde con el identificador pasado por
   * parámetro.
   */
  public static String get(int errorId) {
    if (error == null) {
      makeInstance();
    }
    return error.literals.getString("Error" + errorId);
  }

  public static void destroyMe(){
    error = null;
  }

}
