package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador XOr lógico de dos variable booleanas
 * 
 * @author jirsis
 * @since 2.0
 */
public class XOr extends NonTerminal {

  private static final long serialVersionUID = -8744255479896714450L;

  /**
   * Constructor por defecto.
   */
  public XOr() {
    super("boolean$$boolean$$boolean", "^");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final Boolean operador1 = (Boolean) arguments.get(0).evaluate(userProgram, returnAddr);
    final Boolean operador2 = (Boolean) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 ^ operador2;
  }

}
