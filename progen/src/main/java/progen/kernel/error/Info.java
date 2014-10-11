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
   * @param idInfo
   *          Identificador de la información
   * @param msg
   *          Mensaje personalizado
   */
  public static void show(int idInfo, String msg) {
    System.out.println(get(idInfo) + " [" + msg + "]");
  }

  /**
   * Muetra por pantalla un aviso, al que es necesario proporcionarle el código
   * de información.
   * 
   * @param idInfo
   *          Identificador de la información.
   */
  public static void show(int idInfo) {
    System.out.println(get(idInfo));
  }

  public static String get(int idInfo) {
    return literals.getString(INFO_LITERAL + idInfo);
  }
}
