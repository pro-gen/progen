package progen.kernel.evolution.selector;

import java.util.ArrayList;
import java.util.List;

import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * Clase que implementa el operador de selección aleatoria.
 * Se seleccionarán tantos individuos como se hayan especificado, sin tener
 * en cuenta el fitness de cada uno de ellos.
 * 
 * @author jirsis
 *
 */
public class RandomSelector extends Selector{
	
	/*
	 * (non-Javadoc)
	 * @see progen.kernel.evolution.selector.Selector#select(progen.kernel.population.Population)
	 */
	@Override
	public List<Individual> select(Population pop, int howMany) {
		List<Individual> individuals = new ArrayList<Individual>();
		for(int i = 0;i<howMany;i++){
			individuals.add(pop.getIndividual((int)(Math.random()*pop.getIndividuals().size())).clone());
		}
		return individuals;
	}

}
