package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

public class BitVrotd extends NonTerminal {

  private static final long serialVersionUID = 6785455860346276489L;

  public BitVrotd() {
    super("int$$int", "vrotd");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final Integer child_1 = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);

    return (Integer) (Integer.rotateRight(child_1, 1));
  }

}