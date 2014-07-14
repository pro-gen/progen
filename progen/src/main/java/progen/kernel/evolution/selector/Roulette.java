package progen.kernel.evolution.selector;

import java.util.ArrayList;
import java.util.List;

import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * Clase que implementa el comportamiento del selector de ruleta.
 * 
 * @author jirsis
 * @since 1.0
 * 
 */
public class Roulette extends Selector {

  @Override
  public List<Individual> select(Population pop, int howMany) {
    final List<Individual> selection = new ArrayList<Individual>();

    double totalAdjustedFitness = 0;

    double fitnessSelected;
    boolean rouletteSelected = false;
    double currentFitness;

    for (Individual individual : pop.getIndividuals()) {
      totalAdjustedFitness += individual.getAdjustedFitness();
    }

    for (int i = 0; i < howMany; i++) {
      fitnessSelected = Math.random();
      currentFitness = 0;
      rouletteSelected = false;
      for (int j = 0; !rouletteSelected && j < pop.getIndividuals().size(); j++) {
        currentFitness += pop.getIndividual(j).getAdjustedFitness() / totalAdjustedFitness;
        if (currentFitness >= fitnessSelected) {
          rouletteSelected = true;
          selection.add(pop.getIndividual(j).clone());
        }
      }
    }

    return selection;
  }

}
