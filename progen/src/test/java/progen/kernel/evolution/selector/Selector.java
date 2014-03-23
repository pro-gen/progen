package progen.kernel.evolution.selector;

import progen.kernel.population.Individual;
import progen.userprogram.UserProgram;

public class Selector extends UserProgram {

  @Override
  public double fitness(Individual individual) {
    individual.setVariable("dX", 1.0);
    individual.setVariable("dY", 1.0);
    double resultado = (Double) individual.evaluate(this);
    return Math.max(0.0, resultado);
  }

}
