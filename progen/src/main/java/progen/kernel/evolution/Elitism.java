package progen.kernel.evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * Implementación del elitismo como forma de propagar automáticamente los
 * mejores individuos de cada generación. Para ello es necesario definir la
 * propiedad de <code>progen.gennetic.elitism.size</code> con un valor numérico
 * o porcentual. Si es un porcentaje, se podrá definir acabado en % o como si
 * fuera una probabilidad definida en el intervalo [0,1). Para el caso especial
 * del valor '1', se considerará que únicamente se está eligiendo un único
 * individuo para que promocione a la siguiente generación, de tal forma, que si
 * se desea que pase la población entera, será necesario definirlo como 100%.
 * 
 * @author jirsis
 * 
 */
public class Elitism {

    /** Población sobre la que se actuará */
    private Population population;

    /** Tamaño de la selección */
    private double size;

    /**
     * Constructor por defecto
     * 
     * @param population
     *            Población de la que se aplicará el proceso de elitismo.
     */
    public Elitism(Population population) {
	this.population = population;
	size = ProGenContext.getOptionalPercent("progen.gennetic.elitism.size",
		"0%");
	if (size < 1) {
	    size = population.getIndividuals().size() * size;
	}
    }

    /**
     * Devuelve una lista que contiene los <code>n</code> mejores individuos de
     * la población.
     * 
     * @return El conjunto de individuos que han promocionado directamente a la
     *         siguiente generación.
     */
    public List<Individual> propagate() {
	List<Individual> newPopulation = new ArrayList<Individual>();
	List<Individual> individuals = new ArrayList<Individual>();
	
	for (int i = 0; i < population.getIndividuals().size(); i++) {
	    individuals.add(population.getIndividual(i).clone());
	}

	Collections.sort(individuals);

	for (int i = 0; i < size; i++) {
	    newPopulation.add(individuals.get(i));
	}
	return newPopulation;
    }
}
