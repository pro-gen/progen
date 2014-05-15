package progen.kernel.evolution.operators;

import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;

public class TreeMutationTest {

  /**
   * Referencia al operador de cruce
   */
  private TreeMutation treeMutation;
  /**
   * Referencia a la población a cruzar.
   */
  private Population population;


  @Before
  public void setUp() throws Exception {
    ProGenContext.makeInstance();
    ProGenContext.setProperty("progen.total.RPB", 1 + "");
    ProGenContext.setProperty("progen.RPB0.functionSet", 0 + "");
    ProGenContext.setProperty("progen.functionSet0", "BitAndFc, BitOrFc, BitXor, BitNotFc, BitMultFc, BitSumFc, BitVrotdFc, A0, Hval, Bit32ERC");
    ProGenContext.setProperty("progen.functionSet0.return", "Integer");
    ProGenContext.setProperty("progen.experiment.file", "app.gpHashDyn.gpHashDyn.txt");
    ProGenContext.setProperty("progen.population.init-mode", "half&half$$grow=50%,full=0.5");
    ProGenContext.setProperty("progen.population.init-depth-interval", "2,10");

    treeMutation = new TreeMutation();
    Map<String, String> params = ProGenContext.getParameters("default.params");
    treeMutation.setSelector("RandomSelector", params);
    population = new Population(10);

  }

  @Test@Ignore
  public void testEvolve() {
    List<Individual> evolucion;
    evolucion = treeMutation.evolve(population);
    for (int i = 0; i < evolucion.size(); i++) {
      for (int j = 0; j < population.getIndividuals().size(); j++) {
        assertFalse(population.getIndividual(j).toString().equals(evolucion.get(i).toString()));
      }
    }
  }

}
