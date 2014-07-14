/**
 * 
 */
package progen.kernel.evolution.operators;

import java.util.ArrayList;
import java.util.List;

import progen.kernel.evolution.GenneticOperator;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * Clase que implementa el operador genético de Reproducción. Lo que realiza
 * este operador es seleccionar un individuo a través del selector que se haya
 * escogido y se devuelve tal cual.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public class Reproduction extends GenneticOperator {

  @Override
  public List<Individual> evolve(Population population) {
    List<Individual> individuals = getSelector().select(population, 1);
    List<Individual> individualsMutate = new ArrayList<Individual>();

    individualsMutate.add(individuals.get(0));

    return individualsMutate;
  }

}
