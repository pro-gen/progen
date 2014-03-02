package progen.kernel.evolution.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * Implementación de la forma de seleccionar individuos como torneo. Esta forma
 * de seleccionar individuos de la población consiste en elegir de forma
 * aleatoria una muestra de tantos individuos como esté definido en el tamaño
 * del selector y luego, se devolverá ese conjunto ordenado en función del valor
 * del adjustedFitness de cada uno de ellos; quedando en las primeras posiciones
 * aquellos individuos que tenga este valor mayor.
 * 
 * @author jirsis
 * @since 1.0
 * 
 */
public class Tournament extends Selector {

    /** Tamaño del torneo */
    private int size;

    /*
     * (non-Javadoc)
     * 
     * @see
     * progen.kernel.evolution.selector.Selector#select(progen.kernel.population
     * .Population)
     */
    @Override
    public List<Individual> select(Population pop, int howMany) {
	List<Individual> selection = new ArrayList<Individual>();
	List<Individual> selectedIndividuals = new ArrayList<Individual>();
	List<Integer>	 indexes = new ArrayList<Integer>(); 
	Random prng = new Random();

	
	generateIndexes(pop, indexes);
	for (int i = 0; i < howMany; i++) {
	    selection.clear();

	    for (int j = 0; j < size; j++) {
		int position = prng.nextInt(indexes.size());
		int index = indexes.get(position);
		selection.add(pop.getIndividual(index).clone());
		indexes.remove(position);
	    }

	    Collections.sort(selection);

	    selectedIndividuals.add(selection.get(0));
	}

	return selectedIndividuals;
    }

    private void generateIndexes(Population pop, List<Integer> indexes) {
	for (int i = 0; i < pop.getIndividuals().size(); i++) {
	    indexes.add(i);
	}
    }

    /*
     * (non-Javadoc)
     * @see progen.kernel.evolution.selector.Selector#setParams(java.util.Map)
     */
    @Override
    public void setParams(Map<String, String> params){
	try{
	    size = Integer.parseInt(params.get("size"));
	    if (size > Integer.parseInt(ProGenContext.getMandatoryProperty("progen.population.size"))){
		throw new TournamentSizeException();
	    }
	}catch (java.lang.NumberFormatException e){
	    throw new TournamentSizeMandatoryException();
	}
    }

}
