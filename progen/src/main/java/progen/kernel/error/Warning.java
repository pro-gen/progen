package progen.kernel.error;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que imprime por pantalla avisos de distinto tipo.
 * 
 * @author jirsis
 */
public final class Warning {
  
  private static final String WARNING_LITERAL = "Warning";
  private static ResourceBundle literals;
  
  static{
    literals = ResourceBundle.getBundle("progen.kernel.error.strings", Locale.getDefault());
  }
  
  private Warning(){
    
  }
  
  /**
   * Muetra por pantalla un aviso, al que es necesario proporcionarle el código
   * de aviso y el mensaje personalizado.
   * 
   * @param idWarning
   *          Identificador del aviso
   * @param msg
   *          Mensaje personalizado
   */
  public static void show(int idWarning, String msg) {
    System.out.println(literals.getString(WARNING_LITERAL + idWarning) + "[" + msg + "]");
  }

  /**
   * Muetra por pantalla un aviso, al que es necesario proporcionarle el código
   * de aviso.
   * 
   * @param idWarning
   *          Identificador del aviso
   */
  public static void show(int idWarning) {
    System.out.println(literals.getString(WARNING_LITERAL + idWarning));
  }
}
