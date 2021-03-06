package progen.kernel.functions;

import java.util.List;
import java.util.Map;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

public class BitXor extends NonTerminal {

  private static final long serialVersionUID = -1388739101239005254L;

  /**
   * Default constructor.
   */
  public BitXor() {
    super("int$$int$$int", "^");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    final Integer child1 = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);
    final Integer child2 = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);

    return (Integer) (child1 ^ child2);
  }

}