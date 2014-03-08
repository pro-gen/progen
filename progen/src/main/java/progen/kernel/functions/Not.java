package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Implementación de un operador de negación. 
 * @author jirsis
 * @since 2.0
 */
public class Not extends NonTerminal{
	
    /**
	 * Constructor por defecto.
	 */
	public Not(){
		super("boolean$$boolean", "NOT");
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.util.List, progen.userprogram.UserProgram, java.util.HashMap)
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
		return !((Boolean)arguments.get(0).evaluate(userProgram, returnAddr));
	}
}
		