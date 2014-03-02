package test.progen.kernel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.progen.kernel.error.ErrorAllTests;
import test.progen.kernel.functions.FunctionsAllTests;
import test.progen.kernel.grammar.GrammarAllTests;
import test.progen.kernel.hypergp.HyperGPAllTests;
import test.progen.kernel.population.PopulationAllTests;
import test.progen.kernel.tree.TreeAllTests;

/**
 * Clase que ejecuta todo el conjunto de teste del paquete correspondiente.
 * 
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ErrorAllTests.class,
	FunctionsAllTests.class,
	GrammarAllTests.class,
	HyperGPAllTests.class,
	PopulationAllTests.class,
	TreeAllTests.class
})
public class KernelAllTests {}
