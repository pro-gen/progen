
package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa la función aritmética de restar dos números. Esta función
 * recibe dos números de tipo int y devuelve el resultado como int.
 * @author jirsis
 * @since 2.0
 */
public class IntMinus extends NonTerminal {

	/**
	 * Constructor de la función que define los valores por defecto,
	 * definiendo como símbolo '-'.
	 */
	public IntMinus() {
		super("int$$int$$int", "-");
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.util.HashMap, java.util.List, progen.userprogram.UserProgram)
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String,Object> returnAddr) {
		Integer minuendo=(Integer)arguments.get(0).evaluate(userProgram, returnAddr);
		Integer sustraendo=(Integer)arguments.get(1).evaluate(userProgram, returnAddr);
		return minuendo-sustraendo;
	}

}
