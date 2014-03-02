package test.progen.experimenter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.progen.experimenter.property.EnumerationPropertyTest;
import test.progen.experimenter.property.LoopPropertyTest;
import test.progen.experimenter.property.PropertyFactoryTest;
import test.progen.experimenter.property.condition.GratherEqualThanLoopConditionTest;
import test.progen.experimenter.property.condition.LessEqualThanLoopConditionTest;
import test.progen.experimenter.property.condition.LoopConditionTest;


/**
 * Clase que ejecuta todo el conjunto de test del paquete correspondiente.
 * 
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExperimentFactoryTest.class,
	SimpleExperimenterTest.class,
	MultipleExperimenterTest.class,
	PropertyFactoryTest.class,
	EnumerationPropertyTest.class,
	LoopPropertyTest.class,
	GratherEqualThanLoopConditionTest.class,
	LessEqualThanLoopConditionTest.class,
	LoopConditionTest.class
})
public class ExperimenterAllTests {
    //do nothing, because this is a test suite
}
