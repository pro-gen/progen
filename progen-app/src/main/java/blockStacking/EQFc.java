package app.blockStacking;

import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * This class implements the function "Lower than".
 * @author Alberto Vegas Estrada
 * @version 2.0
 * @since 1.0
 */
public class EQFc extends NonTerminal{
	
    /**
	 * Constructor. Passes to the upper class Function its arity, signature and symbol
	 */
	public EQFc(){
		super("boolean$$boolean$$boolean", "EQ");
	}

	/**
	 * This operator checks if the first arg is lower than the second arg. Returns true in that case or false otherway
	 * @param children the nodes hanging from the node containing this function, arguments for the operation.
	 * @return The result of the operation
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram,
			HashMap<String, Object> returnAddr) {
		arguments.get(0).evaluate(userProgram, returnAddr);
		arguments.get(1).evaluate(userProgram, returnAddr);
		return (Boolean) true;
	}

}
		