package test.progen.output;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.progen.output.dataCollectors.DataGeneratorsAllTests;
import test.progen.output.outputers.OutputersAllTests;
import test.progen.output.plugins.PluginsAllTests;

/**
 * Clase que ejecuta todo el conjunto de teste del paquete correspondiente.
 * 
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	DataGeneratorsAllTests.class,
	OutputersAllTests.class,
	PluginsAllTests.class
})
public class OutputAllTests{}