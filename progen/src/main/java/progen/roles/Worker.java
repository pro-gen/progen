package progen.roles;

import progen.userprogram.UserProgram;

/**
 * Interface que define los métodos que tendrán que implemenar las clases para
 * que ProGen se comporte como un Worker.
 * 
 * @author jirsis
 * @since 2.0
 */
public interface Worker extends ExecutionRole {
  /**
   * Ejecuta y calcula el resultado de una tarea.
   * 
   * @param task
   *          La tarea a calcular.
   */
  void calculate(Task task, UserProgram userProgram);

  /**
   * Devuelve un identificador de worker que tendrá que ser único para cada uno
   * de los workers que estén registrados en un dispatcher concreto.
   * 
   * @return La representación en forma de String del identificador del worker.
   */
  String getId();

  /**
   * Devuelve la tarea que está asignada a un worker concreto durante su
   * ejecución.
   * 
   * @return La tarea concreta.
   */
  Task getTask();

  /**
   * Duelve el puerto en el que está escuchando el registry en el que se haya
   * registrado dicho worker.
   * 
   * @return El puerto en el que espera las peticiones.
   * @see java.rmi.registry.Registry
   */
  int getPort();
}
