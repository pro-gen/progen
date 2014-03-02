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
public class DUFc extends NonTerminal{
	
    /**
	 * Constructor. Passes to the upper class Function its arity, signature and symbol
	 */
	public DUFc(){
		super("boolean$$boolean$$boolean", "DU");
	}

	/**
	 * This operator checks if the first arg is lower than the second arg. Returns true in that case or false otherwise
	 * @param children the nodes hanging from the node containing this function, arguments for the operation.
	 * @return The result of the operation
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram,
			HashMap<String, Object> returnAddr) {
		BlockStacking sf = (BlockStacking)userProgram;
		int itermax=0;
		while(!(Boolean)(arguments.get(1).evaluate(userProgram, returnAddr)) && itermax<25 && sf._maxiterdu>0){
			sf._maxiterdu--;
			itermax++;
			arguments.get(0).evaluate(userProgram, returnAddr);
		};
		if(itermax==0){
			return (Boolean) true;
		}else{
			return (Boolean) false;
		}
	}
}
		