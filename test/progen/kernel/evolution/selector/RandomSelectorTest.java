package test.progen.kernel.evolution.selector;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;

import progen.context.ProGenContext;
import progen.kernel.evolution.selector.RandomSelector;
import progen.kernel.evolution.selector.Selector;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;

public class RandomSelectorTest{

    private RandomSelector selector;

    private int howMany;
    
    @Before
    public void setUp() throws Exception {
	selector = new RandomSelector();
	howMany=4;
    }

    public void testSelect() {
	ProGenContext.makeInstance("master_standalone.txt");
	Population population = new Population(20);
	List<Individual> selection = selector.select(population, howMany);

	assertTrue(selection.size() == howMany);

	for (Individual individual : selection) {
	    assertTrue(population.getIndividuals().contains(individual));
	}
    }
    public void testMakeSelector() {
	Map<String, String> params = ProGenContext.getParameters("default.params");
	Selector random = Selector.makeSelector("RandomSelector", params);
	assertTrue(random instanceof RandomSelector);
    }

}
