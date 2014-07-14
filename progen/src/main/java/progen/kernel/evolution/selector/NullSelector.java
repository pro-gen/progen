package progen.kernel.evolution.selector;

import java.util.ArrayList;
import java.util.List;

import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * Implementación de un selector que no devuelve ningún individuo.
 * 
 * @author jirsis
 * @since 2.0
 */
public class NullSelector extends Selector {

  @Override
  public List<Individual> select(Population pop, int howMany) {
    return new ArrayList<Individual>(howMany);
  }

}
