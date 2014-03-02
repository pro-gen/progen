package test.progen.output.outputers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Clase que ejecuta todo el conjunto de teste del paquete correspondiente.
 * 
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	FormatterTest.class,
	StandardConsoleTest.class
})
public class OutputersAllTests {}
