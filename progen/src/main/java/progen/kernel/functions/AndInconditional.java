package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de Y lógico de dos variable booleanas de tal 
 * forma que se evalúan los dos parámetros.
 * 
 * @author jirsis
 * @since 2.0
 */
public class AndInconditional extends NonTerminal{

	/**
	 * Constructor por defecto.
	 */
	public AndInconditional() {
		super("boolean$$boolean$$boolean", "&");
	}

	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
		Boolean operador1 = (Boolean) arguments.get(0).evaluate(userProgram, returnAddr);
		Boolean operador2 = (Boolean) arguments.get(1).evaluate(userProgram, returnAddr);
		return operador1 & operador2;
	}

}
