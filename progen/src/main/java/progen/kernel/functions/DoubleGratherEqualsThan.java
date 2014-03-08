package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de mayor o igual que (>=) de dos variable double.
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoubleGratherEqualsThan extends NonTerminal{

	/**
	 * Constructor por defecto.
	 */
	public DoubleGratherEqualsThan() {
		super("boolean$$double$$double", ">=");
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.util.List, progen.userprogram.UserProgram, java.util.HashMap)
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
		Double operador1 = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
		Double operador2 = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
		return operador1 >= operador2;
	}

}
