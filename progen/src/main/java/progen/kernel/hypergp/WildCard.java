/**
 * 
 */
package progen.kernel.hypergp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.Function;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * 
 * 
 * @author jirsis
 * 
 */
public class WildCard extends Function {

  private static final long serialVersionUID = -7893266618897015503L;
  private List<Function> functions;

  public WildCard(Function function) {
    super(function.getSignature(), "=");
    functions = new ArrayList<Function>();
    addFunction(function);
  }

  public void addFunction(Function function) {
    functions.add(function);
  }

  @Override
  public Object evaluate(List<Node> argumets, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    System.err.println("WildCard.evaluate NOT YET IMPLEMENTED");
    return null;
  }

}
