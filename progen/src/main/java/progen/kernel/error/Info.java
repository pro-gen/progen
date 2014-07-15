package progen.kernel.error;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que imprime por pantalla información de distinto tipo.
 * 
 * @author jirsis
 */
public final class Info {
  
  private static final String INFO_LITERAL = "Info";
  private static final String PROGEN_KERNEL_ERROR_STRINGS_BUNDLE = "progen.kernel.error.strings";

  private static ResourceBundle literals ;
  
  static{
    literals = ResourceBundle.getBundle(PROGEN_KERNEL_ERROR_STRINGS_BUNDLE, Locale.getDefault());
  }
  
  private Info(){
    
  }
  
  /**
   * Muetra por pantalla un aviso, al que es necesario proporcionarle el código
   * de información y el mensaje personalizado.
   * 
   * @param id
   *          Identificador de la información
   * @param msg
   *          Mensaje personalizado
   */
  public static void show(int id, String msg) {
    System.out.println(get(id) + " [" + msg + "]");
  }

  /**
   * Muetra por pantalla un aviso, al que es necesario proporcionarle el código
   * de información.
   * 
   * @param id
   *          Identificador de la información.
   */
  public static void show(int id) {
    System.out.println(get(id));
  }

  public static String get(int id) {
    return literals.getString(INFO_LITERAL + id);
  }
}
