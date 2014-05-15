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

@Ignore
public class PointMutationTest {

  private PointMutation mutation;

  @Before
  public void setUp() throws Exception {
    ProGenContext.makeInstance();
    ProGenContext.setProperty("progen.population.init-mode", "half&half(grow=50%,full=0.5)");
    ProGenContext.setProperty("progen.population.init-depth-interval", "3,5");
    ProGenContext.setProperty("progen.functionSet0", "DoublePlus, DoubleMinus, DoubleX, DoubleY");
    ProGenContext.setProperty("progen.functionSet0.return", "double");
    ProGenContext.setProperty("progen.total.RPB", "1");
    ProGenContext.setProperty("progen.RPB0.functionSet", "0");
    ProGenContext.setProperty("progen.total.operators", "1");

    mutation = new PointMutation();
    Map<String, String> params = ProGenContext.getParameters("default.params");
    mutation.setSelector("RandomSelector", params);
  }

  @Test
  public void testEvolve() {
    Population population = new Population(1000);
    List<Individual> evolucion;
    evolucion = mutation.evolve(population);
    assertFalse(population.getIndividual(0).toString().equals(evolucion.get(0).toString()));
  }

}
