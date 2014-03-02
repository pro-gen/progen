/**
 * 
 */
package test.progen.kernel.evolution.operators;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.kernel.evolution.operators.OnePointCrossover;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * @author jirsis
 * @since 2.0
 *
 */
public class OnePointCrossoverTest {
	/** Referencia al operador de cruce */
	private OnePointCrossover crossover;
	/** Referencia a la población a cruzar. */
	private Population population;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ProGenContext.makeInstance();
		ProGenContext.setProperty("progen.total.RPB", 1+"");
		ProGenContext.setProperty("progen.RPB0.functionSet", 0+"");
		ProGenContext.setProperty("progen.functionSet0", "DoublePlus, DoubleMinus, DoubleX");
		ProGenContext.setProperty("progen.functionSet0.return", "double");
		ProGenContext.setProperty("progen.population.init-mode", "half&half(grow=50%,full=0.5)");
		ProGenContext.setProperty("progen.population.init-depth-interval", "2,10");

		crossover = new OnePointCrossover();
		Map<String, String> params = ProGenContext.getParameters("default.params");
		crossover.setSelector("RandomSelector", params);
		population=new Population(10);
		
	}

	/**
	 * Test method for {@link progen.kernel.evolution.operators.StandardCrossover#evolve(progen.kernel.population.Population)}.
	 */
	@Test
	public void testEvolveRandomInit() {
		List<Individual> evolucion; 
		evolucion=crossover.evolve(population);
		for(int i=0;i<evolucion.size();i++){
			for(int j=0;j<population.getIndividuals().size();j++){
				assertFalse(population.getIndividual(j)==evolucion.get(i)
						& population.getIndividual(j).toString().equals(evolucion.get(i).toString()));
			}
		}
	}

	@Test
	public void testEvolveGrowInit() {
		ProGenContext.setProperty("progen.population.init-mode", "grow");
		ProGenContext.setProperty("progen.population.init-depth-interval", "4,10");
		List<Individual> evolucion; 
		evolucion=crossover.evolve(population);
		for(int i=0;i<evolucion.size();i++){
			for(int j=0;j<population.getIndividuals().size();j++){
				assertFalse(population.getIndividual(j)==evolucion.get(i)
						& population.getIndividual(j).toString().equals(evolucion.get(i).toString()));
			}
		}
	}
	
	@Test
	public void testDepth10() {
		ProGenContext.setProperty("progen.population.init-depth-interval", "10");
		testEvolveGrowInit();
	}
}
