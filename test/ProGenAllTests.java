package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.progen.kernel.KernelAllTests;
import test.progen.output.OutputAllTests;
import test.progen.context.ContextAllTests;
import test.progen.experimenter.ExperimenterAllTests;
import test.progen.roles.RolesAllTests;
import test.progen.userprogram.UserProgramAllTests;

/**
 * Clase que ejecuta todo el conjunto de teste del paquete correspondiente.
 * 
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	KernelAllTests.class,
	OutputAllTests.class,
	ContextAllTests.class,
	RolesAllTests.class,
	UserProgramAllTests.class,
	ExperimenterAllTests.class
})
public class ProGenAllTests {
    //This is a SuiteTest, and the implementation is empty.
}
