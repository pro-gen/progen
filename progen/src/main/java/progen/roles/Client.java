package progen.roles;

/**
 * Interface que define los métodos que tendrán que implementar los distintos
 * tipos de cliente que existan.
 * 
 * @author jirsis
 * 
 */
public interface Client extends ExecutionRole {
  /**
   * Forma de inicialiar y/o localizar el dispatcher que será el encargado de
   * repartir las distintas tareas para su posterior evaluación.
   * 
   * @return El dispatcher inicializado.
   */
  public Dispatcher initDispatcher();

}
