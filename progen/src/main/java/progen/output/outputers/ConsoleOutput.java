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
  private ResourceBundle literals;

  /**
   * Constructor por defecto. Se inicializa la variable de literales.
   */
  public ConsoleOutput() {
    literals = ResourceBundle.getBundle("progen.output.outputers.literals", Locale.getDefault());
  }

  @Override
  public void close() {
    // do nothing
  }

  @Override
  public void init() {
    // do nothing
  }

  public ResourceBundle getLiterals() {
    return literals;
  }

}
