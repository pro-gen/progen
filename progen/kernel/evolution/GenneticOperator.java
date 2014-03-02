package progen.kernel.evolution;

import java.util.List;
import java.util.Map;

import progen.kernel.evolution.selector.NullSelector;
import progen.kernel.evolution.selector.Selector;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * Clase que define los métodos de acceso a los distintos operadores genéticos disponibles.
 * @author jirsis
 * @since 2.0
 *
 */
public abstract class GenneticOperator {
	/** Probabilidad de que un operador genético sea usado. */
	private double probability;
	/** Selector de individuos para ejecutar el operador genético. */
	protected Selector selector;
	
	/**
	 * Constructor genérico de la clase.
	 */
	public GenneticOperator(){
		probability=0;
		selector= new NullSelector();
	}
	
	/**
	 * Devuelve la probabilidad de uso del operador genético.
	 * @return la probabilidad de uso.
	 */
	public final double getProbability() {
		return probability;
	}

	/**
	 * Define la probabilidad con la que será usado un operador genético.
	 * @param probability La porbabilidad con la que será usado.
	 */
	public final void setProbability(double probability) {
		this.probability=probability;
	}
	
	/**
	 * Define el selector que utilizará el operador genético para escoger
	 * los inidividuos.
	 * @param name El nombre del selector.
	 * @param params Parámetros de configuración del selector.
	 */
	public final void setSelector(String name, Map<String, String> params){
		selector=Selector.makeSelector(name, params);
	}
	
	/**
	 * Forma de aplicar el operador genético sobre una población concreta. 
	 * @param population La población sobre la que se aplicará el operador.
	 * @return Conjunto de individuos nuevos tras la ejecución del operador.
	 */
	public abstract List<Individual> evolve(Population population);

	
}
