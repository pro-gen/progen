package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Implementación de un operador de negación.
 * 
 * @author jirsis
 * @since 2.0
 */
public class Not extends NonTerminal {

  private static final long serialVersionUID = 1609608449917410151L;

  /**
   * Constructor por defecto.
   */
  public Not() {
    super("boolean$$boolean", "NOT");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    return !((Boolean) arguments.get(0).evaluate(userProgram, returnAddr));
  }
}
