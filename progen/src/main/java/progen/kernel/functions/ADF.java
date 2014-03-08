package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.context.ProGenContext;
import progen.kernel.tree.Node;
import progen.kernel.tree.Tree;
import progen.userprogram.UserProgram;

/**
 * @author jirsis
 *
 */
public class ADF extends NonTerminal {

	/**
	 * Árbol que define el ADF concreto de esta instancia.
	 */
	private Tree adf;
	
	/**
	 * @param symbol
	 * @throws NumberFormatException 
	 */
	public ADF(String symbol) {
		super(ProGenContext.getMandatoryProperty("progen."+symbol+".interface"), symbol);
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.functions.Function#evaluate(java.util.HashMap, java.util.List, progen.userprogram.UserProgram)
	 */
	@Override
	public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String,Object> returnAddr) {
		for(int i=0;i<arguments.size();i++){
			returnAddr.put(super.getSymbol()+"-"+"ARG"+i, arguments.get(i));
		}
		
		return adf.evaluate(userProgram, returnAddr);
	}

	/**
	 * Establece que árbolo concreto será almacenado en un ADF.
	 * @param tree
	 */
	public void setADFTree(Tree tree) {
		this.adf=tree;
	}
	

}

