package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de menor que (<) de dos variable double.
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoubleLessThan extends NonTerminal {

  private static final long serialVersionUID = -3991342948028466248L;

  /**
   * Constructor por defecto.
   */
  public DoubleLessThan() {
    super("boolean$$double$$double", "<");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final Double operador1 = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
    final Double operador2 = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 < operador2;
  }

}
