package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de igualdad de dos números reales.
 * @author jirsis
 * @since 2.0
 */
public class DoubleEquals extends NonTerminal {

	/**
	 * Constructor por defecto.
	 */
	public DoubleEquals() {
		super("boolean$$double$$double", "==");
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.util.List, progen.userprogram.UserProgram, java.util.HashMap)
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
		Double operador1 = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
		Double operador2 = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
		return operador1.doubleValue() == operador2.doubleValue();
	}

}
