package app.blockStacking;

import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * This class implements the function "Lower than".
 * 
 * @author Alberto Vegas Estrada
 * @version 2.0
 * @since 1.0
 */
public class MTFc extends NonTerminal {

    /**
     * Constructor. Passes to the upper class Function its arity, signature and
     * symbol
     */
    public MTFc() {
	super("boolean$$char", "MT");
    }

    /**
     * This operator checks if the first arg is lower than the second arg.
     * Returns true in that case or false otherway
     * 
     * @param children
     *            the nodes hanging from the node containing this function,
     *            arguments for the operation.
     * @return The result of the operation
     */
    @Override
    public Object evaluate(List<Node> arguments, UserProgram userProgram,
	    HashMap<String, Object> returnAddr) {
	// If arg0 is in stack, move top of the stack to table, if not, nothing
	BlockStacking sf = (BlockStacking) userProgram;
	Character value = ((Character) arguments.get(0).evaluate(userProgram, returnAddr));
	boolean isInStack = false;
	for (int i = 0; i < sf._sizeProblem && !isInStack; i++) {
	    if (((Character) (sf._stack[sf._currentCase][i])).equals(value)) {
		isInStack = true;
		int topStack = -1;
		for (int j = 0; j < sf._sizeProblem && topStack == -1; j++) {
		    if (!((Character) (sf._stack[sf._currentCase][j]))
			    .equals((Character) '-')) {
			topStack = j;
		    }
		}
		for (int j = 0; j < sf._sizeProblem; j++) {
		    if (((Character) (sf._table[sf._currentCase][j]))
			    .equals((Character) '-')) {
			sf._table[sf._currentCase][j] = sf._stack[sf._currentCase][topStack];
			sf._stack[sf._currentCase][topStack] = '-';
			//sf.setSensors(sf._currentCase); //FIXME: adaptar a
			// ProGen2
			return (Boolean) true;
		    }
		}
	    }
	}
	return (Boolean) false;
    }
}
