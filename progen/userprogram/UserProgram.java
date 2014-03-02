package progen.userprogram;

import progen.ProGenException;
import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.kernel.population.UnknownUserProgramException;

/**
 * Clase que define la funcionalidad básica de un programa implementado por un usuario.
 * Desde esta implementación concreta, se definirá específicamente la función de evaluación de individuos.
 * 
 * @author jirsis
 * @since 2.0
 */
public abstract class UserProgram {

	/**
	 * Inicializa el atributo <code> userProgram </code>. 
	 * Crea una nueva instancia a partir de la propiedad definida como 
	 * <i>"progen.experiment.file"</i>
	 * @return Una instancia de <code>userProgram</code>
	 */
	public static UserProgram getUserProgram(){
		UserProgram userProgram=null;
		String userPackage = ProGenContext.getMandatoryProperty("progen.user.home");
		String userProgramClass;
		String path[] = userPackage.split("\\.");

		userProgramClass = path[path.length - 1].substring(0, 1).toUpperCase();
		userProgramClass += path[path.length - 1].substring(1);

		try {
			userProgram=(UserProgram) Class.forName(userPackage + userProgramClass).newInstance();
		} catch (InstantiationException e) {
			throw new ProGenException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new ProGenException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new UnknownUserProgramException(userPackage + userProgramClass);
		}
		
		return userProgram;
	}
	
	/**
	 * Método de inicialización del UserProgram concreto de cada usuario que será llamado una única vez antes
	 * de empezar a evaluar y ejectuar las distintas generaciones de un experimento concreto.
	 */
	public void initialize(){
	    //
	}
	
	/**
	 * Método de finalización del UserProgram concreto de cada usuario que será llamado una única vez antes
	 * de empezar a evaluar y ejectuar las distintas generaciones de un experimento concreto.
	 * @param best El mejor individuo, para retocar algún parámetro.
	 * @return Devuelve la representación modificada del mejor individuo.
	 */
	public String postProcess(Individual best) {
		return "";
	}
	
	/**
	 * Forma de calcular la función de fitness de un individuo concreto.
	 * @param individual Individuo del que se tiene que devolver un valor de fitness.
	 * @return Valor del raw-fitness del individuo. Tiene que ser un valor de tal forma
	 * que si devuelve un valor de 0, es que se ha encontrado al individuo perfecto.
	 */
	public abstract double fitness(Individual individual);
	
}
