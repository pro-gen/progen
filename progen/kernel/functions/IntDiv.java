
package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa la función aritmética de dividir dos números. Esta función
 * recibe dos números de tipo entero y devuelve el resultado como double.
 * 
 * Al ser una operación que puede no estar definida, cuando el divisor sea 0, 
 * se devolverá el valor infinito (Double.POSITIVE_INFINITY)
 * @author jirsis
 * @since 2.0
 */
public class IntDiv extends NonTerminal {
	/**
	 * Constructor de la función que define los valores por defecto,
	 * definiendo como símbolo '/'.
	 */
	public IntDiv() {
		super("double$$int$$int", "/");
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.util.HashMap, java.util.List, progen.userprogram.UserProgram)
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String,Object> returnAddr) {
		Integer dividendo=(Integer)arguments.get(0).evaluate(userProgram, returnAddr);
		Integer divisor=(Integer)arguments.get(1).evaluate(userProgram, returnAddr);
		if(divisor!=0){
			return ((double)dividendo)/divisor;
		}else{
			return Double.POSITIVE_INFINITY;
		}
	}

}
