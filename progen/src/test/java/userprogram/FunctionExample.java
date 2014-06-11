package userprogram;

import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

public class FunctionExample extends NonTerminal{

  
  public FunctionExample() {
    super("boolean$$boolean$$boolean", "TEST");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    return "eval";
  }

}
