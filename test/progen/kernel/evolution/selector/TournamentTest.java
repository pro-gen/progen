package test.progen.kernel.evolution.selector;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.kernel.evolution.selector.Selector;
import progen.kernel.evolution.selector.Tournament;
import progen.kernel.evolution.selector.TournamentSizeException;
import progen.kernel.evolution.selector.TournamentSizeMandatoryException;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;
/**
 * Clase que realiza un conjunto de test sobre la clase {@link Tournament}
 * @author jirsis
 *
 */
public class TournamentTest{

    /** El torneo a probar */
    private Tournament selector;
    
    /**
     * Inicialización común del test
     */
    @Before
    public void setUp(){
	ProGenContext.makeInstance();
	ProGenContext.setProperty("progen.total.RPB", "0");
	ProGenContext.setProperty("progen.population.size", "10");
	ProGenContext.setProperty("tournament.params", "Tournament(size=3)");
	selector = new Tournament();
	Map<String, String> params = ProGenContext.getParameters("tournament.params");
	selector.setParams(params);
    }

    @Test
    public void testSelect() {
	Population population = new Population();
	List<Individual> selection = selector.select(population, 2);
	
	for(Individual individual : selection){
	    assertTrue(population.getIndividuals().contains(individual));
	}

	for(int i=0;i<selection.size()-1;i++){
	    assertTrue(selection.get(i).getAdjustedFitness() <= selection.get(i+1).getAdjustedFitness());			
	}
    }
    
    @Test
    public void testMakeSelector() {
	Map<String, String> params = ProGenContext.getParameters("tournament.params");
	Selector sel = Selector.makeSelector("Tournament", params);
	assertTrue(sel instanceof Tournament);
    }
    
    /**
     * Espera que salte una excepcion indicando que el tamaño del torneo no puede
     * ser mayor que el tamaño de la población
     */
    @Test(expected=TournamentSizeException.class)
    public void testBadTournamentConfig(){
	ProGenContext.setProperty("tournament.params", "Tournament(size=20)");
	selector = new Tournament();
	Map<String, String> params = ProGenContext.getParameters("tournament.params");
	selector.setParams(params);
    }

    /**
     * Espera que salte una excepción indicando que no se ha definido el tamaño
     * del torneo.
     */
    @Test(expected=TournamentSizeMandatoryException.class)
    public void testTournamentNoSize(){
	ProGenContext.setProperty("tournament.params", "Tournament");
	selector = new Tournament();
	Map<String, String> params = ProGenContext.getParameters("tournament.params");
	selector.setParams(params);
    }

}
