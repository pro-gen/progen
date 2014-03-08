package app.blockStacking;

import progen.kernel.functions.Terminal;


/**
 * This class implements a double variable. Executing it just returns the double value wrapped to be an object.
 * @author Alberto Vegas Estrada
 * @version 2.0
 * @since 1.0
 */
public class CS extends Terminal{

	/**
	 * Constructor. Passes to the upper class Function its arity, signature and symbol and also initializes value of the 
	 * the variable to 0. User can change this value from its program using the setValue(variableName, variableValue) 
	 * function
	 */
	public CS(){
		super("char", "CS");
		setValue((Character)null);
	}
}	