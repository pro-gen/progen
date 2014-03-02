package progen.kernel.error;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que imprime por pantalla avisos de distinto tipo.
 * 
 * @author jirsis
 * 
 */
public class Warning {
    /**
     * Muetra por pantalla un aviso, al que es necesario proporcionarle el
     * código de aviso y el mensaje personalizado.
     * 
     * @param id
     *            Identificador del aviso
     * @param msg
     *            Mensaje personalizado
     */
    public static void show(int id, String msg) {
	ResourceBundle literals = ResourceBundle.getBundle(
		"progen.kernel.error.strings", Locale.getDefault());
	System.out.println(literals.getString("Warning" + id) + "[" + msg + "]");
    }

    /**
     * Muetra por pantalla un aviso, al que es necesario proporcionarle el
     * código de aviso.
     * 
     * @param id
     *            Identificador del aviso
     */
    public static void show(int id) {
	ResourceBundle literals = ResourceBundle.getBundle(
		"progen.kernel.error.strings", Locale.getDefault());
	System.out.println(literals.getString("Warning" + id));
    }
}
