package userprogram;

import java.util.List;
import java.util.Map;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

public class FunctionExample extends NonTerminal{
  private static final long serialVersionUID = -7682117254567382076L;

  public FunctionExample() {
    super("boolean$$boolean$$boolean", "TEST");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    return "eval";
  }

}
