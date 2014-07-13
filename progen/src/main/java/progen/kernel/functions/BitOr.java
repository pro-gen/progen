package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de O lógico de dos variable enteras a nivel
 * de bit.
 * 
 * @author jirsis
 * @since 2.0
 */
public class BitOr extends NonTerminal {

  private static final long serialVersionUID = -5062340026961268360L;

  /**
   * Constructor por defecto.
   */
  public BitOr() {
    super("int$$int$$int", "|");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    Integer operador1 = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);
    Integer operador2 = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 | operador2;
  }

}
