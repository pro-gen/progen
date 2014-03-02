package test.progen.kernel.functions;

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
	//logical test
	TrueTest.class,
	FalseTest.class,
	AndInconditionalTest.class,
	AndTest.class,
	OrInconditionalTest.class,
	OrTest.class,
	XOrTest.class,
	NotTest.class,
	NullFunctionTest.class,
	//double test
	DoubleXTest.class,
	DoubleYTest.class,
	DoubleGratherEqualsThanTest.class,
	DoubleGratherThanTest.class,
	DoubleLessEqualsThanTest.class,
	DoubleLessThanTest.class,
	DoubleEqualsTest.class,
	DoubleNotEqualsTest.class,
	DoublePlusTest.class,
	DoubleMinusTest.class,
	DoubleMultTest.class,
	DoubleDivTest.class,
	DoublePowTest.class,
	//int test
	IntNTest.class,
	IntMTest.class,
	IntGratherEqualsThanTest.class,
	IntGratherThanTest.class,
	IntLessEqualsThanTest.class,
	IntLessThanTest.class,
	IntEqualsTest.class,
	IntNotEqualsTest.class,
	IntPlusTest.class,
	IntMinusTest.class,
	IntMultTest.class,
	IntDivTest.class,
	IntPowTest.class,
	//bit test
	BitAndTest.class,
	BitOrTest.class,
	BitXorTest.class,
	BitMultTest.class,
	BitVrotdTest.class,
	ShiftSignedLeftTest.class,
	ShiftSignedRightTest.class,
	ShiftUnsignedRightTest.class,
	//ERCs test
	//not yet implemented
	Bit32ERCTest.class,
	ERC01Test.class,
	ERCTest.class,
	//function abstract factory test
	//not yet implemented
	FunctionTest.class
})
public class FunctionsAllTests {
    //do nothing, because this is a test suite
}
