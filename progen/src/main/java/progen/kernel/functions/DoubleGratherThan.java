package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de mayor que (>) de dos variable double.
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoubleGratherThan extends NonTerminal {

  private static final long serialVersionUID = -7916481459069772192L;

  /**
   * Constructor por defecto.
   */
  public DoubleGratherThan() {
    super("boolean$$double$$double", ">");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final Double operador1 = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
    final Double operador2 = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 > operador2;
  }

}
