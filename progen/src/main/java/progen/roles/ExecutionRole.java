/**
 * 
 */
package progen.roles;

/**
 * Interfaz que define el método que se ejecutará de forma general a los
 * distintos tipos de roles.
 * 
 * @author jirsis
 * @since 2.0
 */
public interface ExecutionRole{

    /**
     * Método que se ejecutará para que empiece a funcionar la instancia de
     * ProGen con el rol asignado.
     */
    public void start();
}
