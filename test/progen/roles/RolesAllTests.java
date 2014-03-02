package test.progen.roles;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.progen.roles.distributed.DistributedAllTests;
import test.progen.roles.standalone.StandAloneAllTests;

/**
 * Clase que ejecuta todo el conjunto de teste del paquete correspondiente.
 * 
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ProGenFactoryTest.class, 
    StandAloneAllTests.class,
    DistributedAllTests.class

})
public class RolesAllTests {

}
