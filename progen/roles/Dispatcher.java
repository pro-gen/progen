/**
 * 
 */
package progen.roles;

import java.util.List;

import progen.userprogram.UserProgram;

/**
 * Interface que define los distintos métodos que serán necesarios para poder 
 * recibir información de un <code>Client</code> y repartirla a los <code>Worker</code>
 * que calcularán las tareas.
 *  
 * @author jirsis
 * @see Client
 * @see Worker
 */
public interface Dispatcher extends ExecutionRole {
    /**
     * Método que es el encargado de recibir el conjunto de tareas a evaluar y 
     * la función de evaluación de dichas tareas.
     * 
     * @param tasks Conjunto de tareas a evaluar.
     * @param userProgram representación del problema a solucionar y función 
     * concreta de evaluación de las tareas.
     */
    public void asignTask(List<Task> tasks, UserProgram userProgram);

    /**
     * Devuelve las tareas asignadas, una vez evaluadas. 
     * @return Conjunto de tareas evaluadas.
     */
    public List<Task> getResults();

    /**
     * Devuelve el número actual de tareas que se han terminado de evaluar.
     * @return El total de tareas que ya han sido evaluadas.
     */
    public int totalTasksDone();

    /**
     * Método que será llamada en el momento en el que se decida 
     * cancelar las tareas que se están evaluando.
     */
    public void stopTask();

    /**
     * Método que añadirá un <code>Worker</code> al <code>Dispatcher</code> de tal
     * forma que a la hora de repartir las tareas, también se le tendrá en cuenta
     * para que las evalúe. 
     * @param worker nuevo que recibirá tareas para su evaluación.
     */
    public void addWorker(Worker worker);

}
