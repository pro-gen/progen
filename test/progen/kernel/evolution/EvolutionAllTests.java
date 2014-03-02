package test.progen.kernel.evolution;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.progen.kernel.evolution.operators.OperatorsAllTests;
import test.progen.kernel.evolution.selector.SelectorAllTests;

/**
 * Clase que ejecuta todo el conjunto de teste del paquete correspondiente.
 * 
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	OperatorsAllTests.class,
	SelectorAllTests.class
})
public class EvolutionAllTests {
    //do nothing, because this is a test suite
}
