
package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa la función aritmética de restar dos números. Esta función
 * recibe dos números de tipo double y devuelve el resultado como double.
 * @author jirsis
 * @since 2.0
 */
public class DoubleMinus extends NonTerminal {

	/**
	 * Constructor de la función que define los valores por defecto,
	 * definiendo como símbolo '-'.
	 */
	public DoubleMinus() {
		super("double$$double$$double", "-");
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.util.HashMap, java.util.List, progen.userprogram.UserProgram)
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String,Object> returnAddr) {
		Double minuendo=(Double)arguments.get(0).evaluate(userProgram, returnAddr);
		Double sustraendo=(Double)arguments.get(1).evaluate(userProgram, returnAddr);
		return minuendo-sustraendo;
	}

}
