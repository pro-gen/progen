package progen.kernel.functions;

import java.util.List;
import java.util.Map;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de menor o igual que (<=) de dos variable
 * double.
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoubleLessEqualsThan extends NonTerminal {

  private static final long serialVersionUID = 8608975648558490149L;

  /**
   * Constructor por defecto.
   */
  public DoubleLessEqualsThan() {
    super("boolean$$double$$double", "<=");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    final Double operador1 = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
    final Double operador2 = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 <= operador2;
  }

}
