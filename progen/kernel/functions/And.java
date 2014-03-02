package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de Y lógico de dos variable booleanas, en forma
 * de cortocircuito. De esta forma, en cuanto una de los parámetros sea falso, no 
 * se continua evaluando el otro parámetro.
 * 
 * @author jirsis
 * @since 2.0
 */
public class And extends NonTerminal{

	/**
	 * Constructor por defecto.
	 */
	public And() {
		super("boolean$$boolean$$boolean", "&&");
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.util.List, progen.userprogram.UserProgram, java.util.HashMap)
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
		Boolean operador1 = (Boolean) arguments.get(0).evaluate(userProgram, returnAddr);
		Boolean operador2 = (Boolean) arguments.get(1).evaluate(userProgram, returnAddr);
		return operador1 && operador2;
	}

}
