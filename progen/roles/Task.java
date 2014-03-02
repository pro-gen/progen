package progen.roles;

import java.io.Serializable;

import progen.userprogram.UserProgram;
/**
 * Interface que define los métodos que tendrán que implemenar las clases que se 
 * comporten como Task en la ejecución de ProGen.
 * @author jirsis
 * @since 2.0
 */
public interface Task extends Serializable {
	/** 
	 * Método que se llamará para evaluar la tarea.
	 */
	public void calculate(UserProgram userProgram);

	/**
	 * Forma de obtener los resultados calculados, una vez se haya terminado de 
	 * ejecutar la tarea.
	 * @return Resultados de la ejecución de la tarea.
	 */
	public Object getCalculateResult();
	/**
	 * Devuelve si la tarea se ha terminado de evaluar o no.
	 * @return <code>true</code> si la tarea está finalizada o <code>false</code> si se sigue
	 * calculando.
	 */
	public boolean isDone();

}
