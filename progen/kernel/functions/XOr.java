package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador XOr lógico de dos variable booleanas
 * 
 * @author jirsis
 * @since 2.0
 */
public class XOr extends NonTerminal {

	/**
	 * Constructor por defecto.
	 */
	public XOr() {
		super("boolean$$boolean$$boolean", "^");
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.util.List, progen.userprogram.UserProgram, java.util.HashMap)
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
		Boolean operador1 = (Boolean) arguments.get(0).evaluate(userProgram, returnAddr);
		Boolean operador2 = (Boolean) arguments.get(1).evaluate(userProgram, returnAddr);
		return operador1 ^ operador2;
	}

}
