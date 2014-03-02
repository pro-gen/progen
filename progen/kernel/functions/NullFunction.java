/**
 * 
 */
package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Implementación de una función nula, que se utiliza a la hora de 
 * definir y actualizar los nodos en algunas circunstancias.
 * Es una forma de tener un nodo con una función que no ejecuta nada.
 * 
 * @author jirsis
 * @since 2.0
 *
 */
public class NullFunction extends Terminal {

	/**
	 * Constructor de la clase que crea una función nula a partir de un símbolo pasado por
	 * parámetro.
	 * @param symbol Identificador de esta función.
	 */
	public NullFunction(String symbol) {
		super("", symbol);
		
	}

	/* (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.lang.Object[], java.lang.Object[])
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Terminal#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		//do nothing
	}

}
