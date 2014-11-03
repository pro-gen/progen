package progen.roles.standalone;

import java.util.List;

import progen.roles.Dispatcher;
import progen.roles.ProGenFactory;
import progen.roles.Task;
import progen.roles.Worker;
import progen.userprogram.UserProgram;

/**
 * Implementación del rol de Dispatcher para que funcione en la misma máquina
 * virutal que el Client y el Worker. En este caso no hay ningún tipo de
 * comunicación entre los distintos roles, excepto las llamadas a los métodos
 * públicos de cada componente.
 * 
 * @author jirsis
 * @since 2.0
 */
public class DispatcherLocal implements Dispatcher {
  /**
   * Representación del único worker que tiene sentido a la hora de ejecutar en
   * local.
   */
  private Worker worker;
  /** Hilo que se encargará de calcular todas las tareas */
  private CalculateThread thread;

  /**
   * Constructor genérico de la clase.
   */
  public DispatcherLocal() {
    worker = ProGenFactory.makeInstance().makeWorker();
  }

  public void start() {
    // do nothing
  }

  public void addWorker(Worker worker) {
    // do nothing
  }

  public void asignTask(List<Task> tasks, UserProgram userProgram) {
    thread = new CalculateThread(tasks, worker, userProgram);
    thread.run();
    // new Thread(thread).start();
  }

  public List<Task> getResults() {
    return thread.getTasks();
  }

  public void stopTask() {

  }

  public int totalTasksDone() {
    return thread.getCompletedTasks();
  }

}
