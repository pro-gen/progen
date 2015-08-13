package progen.kernel.evolution.operators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import progen.context.ProGenContext;
import progen.kernel.evolution.selector.NullSelector;
import progen.kernel.evolution.selector.RandomSelector;
import progen.kernel.evolution.selector.Selector;
import progen.kernel.grammar.Grammar;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * @author jirsis
 * @since 2.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProGenContext.class, Selector.class})
public class StandardCrossoverTest {
  private StandardCrossover crossover;

  private Population population;

  @Before
  public void setUp() throws Exception {
    crossover = new StandardCrossover();
    crossover.setSelector("RandomSelector", null);

    mockProGenContext();
    mockPopulation();
  }

  @Test
  public void testConstructor() {
    crossover = new StandardCrossover();
    assertEquals(0.0, crossover.getProbability(), 0.1);
    assertTrue(crossover.getSelector() instanceof NullSelector);
  }

  @Test
  public void testEvolve() {
    List<Individual> evolucion = crossover.evolve(population);
    assertEquals(2, evolucion.size());
    assertNotEquals(population.getIndividual(0), evolucion.get(0));
    assertNotEquals(population.getIndividual(1), evolucion.get(1));
  }
  
  @Test(expected = SelectorSizeIncorrectValueException.class)
  public void invalidSelector(){
    mockStatic(Selector.class);
    RandomSelector selector = mock(RandomSelector.class);
    when(ProGenContext.getOptionalProperty("progen.language", "en_US")).thenReturn("en_US");
    when(selector.select(population, 2)).thenReturn(new ArrayList<Individual>());
    when(Selector.makeSelector("RandomSelector", null)).thenReturn(selector);
    crossover.setSelector("RandomSelector", null);
    crossover.evolve(population);
  }

  private void mockPopulation() {
    population = mock(Population.class);
    List<Individual> individuals = mockIndividuals();
    when(population.getIndividuals()).thenReturn(individuals);
    when(population.getIndividual(any(Integer.class))).thenReturn(individuals.get(0));
  }

  private void mockProGenContext() {
    mockStatic(ProGenContext.class);
    ProGenContext context = mock(ProGenContext.class);
    when(ProGenContext.makeInstance(any(String.class))).thenReturn(context);
    when(ProGenContext.getMandatoryProperty("progen.total.RPB")).thenReturn("1");
    when(ProGenContext.getMandatoryProperty("progen.RPB0.functionSet")).thenReturn("0");
    when(ProGenContext.getMandatoryProperty("progen.functionSet0.return")).thenReturn("int");
    when(ProGenContext.getMandatoryProperty("progen.functionSet0")).thenReturn("IntMult, IntMinus, IntN");
    when(ProGenContext.getOptionalProperty("progen.user.home", "progen.kernel.functions.")).thenReturn("progen.kernel.functions.");
    when(ProGenContext.getOptionalProperty("progen.total.RPB", 1)).thenReturn(1);
    when(ProGenContext.getOptionalProperty("progen.population.init-mode", "half&half")).thenReturn("full");
    when(ProGenContext.getOptionalProperty("progen.population.max-nodes", Integer.MAX_VALUE)).thenReturn(Integer.MAX_VALUE);
    when(ProGenContext.getOptionalProperty("progen.max-attempts", 100)).thenReturn(10);
    when(ProGenContext.getMandatoryProperty("progen.population.init-depth-interval")).thenReturn("2,10");
  }

  private List<Individual> mockIndividuals() {
    List<Individual> population = new ArrayList<Individual>();
    Map<String, Grammar> grammars = new HashMap<String, Grammar>();
    grammars.put("RPB0", new Grammar("RPB0"));
    population.add(new Individual(grammars));
    return population;
  }

}
