package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de desigualdad de dos números enteros.
 * 
 * @author jirsis
 * @since 2.0
 */
public class IntNotEquals extends NonTerminal {

  private static final long serialVersionUID = 5107500932103001490L;

  /**
   * Constructor por defecto.
   */
  public IntNotEquals() {
    super("boolean$$int$$int", "!=");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    Integer operador1 = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);
    Integer operador2 = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1.intValue() != operador2.intValue();
  }

}
