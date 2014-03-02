package progen.kernel.error;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que imprime por pantalla información de distinto tipo.
 * 
 * @author jirsis
 * 
 */
public class Info {
    /**
     * Muetra por pantalla un aviso, al que es necesario proporcionarle el
     * código de información y el mensaje personalizado.
     * 
     * @param id
     *            Identificador de la información
     * @param msg
     *            Mensaje personalizado
     */
    public static void show(int id, String msg) {
	ResourceBundle literals = ResourceBundle.getBundle(
		"progen.kernel.error.strings", Locale.getDefault());
	System.out.println(literals.getString("Info" + id) + " [" + msg + "]");
    }

    /**
     * Muetra por pantalla un aviso, al que es necesario proporcionarle el
     * código de información.
     * 
     * @param id
     *            Identificador de la información.
     */
    public static void show(int id) {
	ResourceBundle literals = ResourceBundle.getBundle(
		"progen.kernel.error.strings", Locale.getDefault());
	System.out.println(literals.getString("Info" + id));
    }
    
    public static String get(int id) {
	ResourceBundle literals = ResourceBundle.getBundle(
		"progen.kernel.error.strings", Locale.getDefault());
	return literals.getString("Info" + id);
    }
}
