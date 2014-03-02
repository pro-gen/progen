package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

public class BitXor extends NonTerminal{

	/**
	 * Default constructor.
	 */
	public BitXor(){
		super("int$$int$$int", "^");
	}
	
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String,Object> returnAddr){
		Integer child_1 = (Integer) arguments.get(0).evaluate(userProgram, returnAddr); 
		Integer child_2 = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);
 
		return (Integer) (child_1 ^ child_2);
	}


}	