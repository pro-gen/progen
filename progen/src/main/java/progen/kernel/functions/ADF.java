package progen.kernel.functions;

import java.util.List;
import java.util.Map;

import progen.context.ProGenContext;
import progen.kernel.tree.Node;
import progen.kernel.tree.Tree;
import progen.userprogram.UserProgram;

/**
 * @author jirsis
 * 
 */
public class ADF extends NonTerminal {

  private static final long serialVersionUID = -1338537410471402589L;
  
  /**
   * Árbol que define el ADF concreto de esta instancia.
   */
  private Tree adf;

  /**
   * @param symbol
   * @throws NumberFormatException
   */
  public ADF(String symbol) {
    super(ProGenContext.getMandatoryProperty("progen." + symbol + ".interface"), symbol);
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    for (int i = 0; i < arguments.size(); i++) {
      returnAddr.put(super.getSymbol() + "-" + "ARG" + i, arguments.get(i));
    }

    return adf.evaluate(userProgram, returnAddr);
  }

  /**
   * Establece que árbolo concreto será almacenado en un ADF.
   * 
   * @param tree
   */
  public void setADFTree(Tree tree) {
    this.adf = tree;
  }

}
