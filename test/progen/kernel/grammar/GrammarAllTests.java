package test.progen.kernel.grammar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.progen.kernel.grammar.validations.ValidationsAllTests;

/**
 * Clase que ejecuta todo el conjunto de teste del paquete correspondiente.
 * 
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ValidationsAllTests.class
})
public class GrammarAllTests {
    //do nothing, because this is a test suite
}
