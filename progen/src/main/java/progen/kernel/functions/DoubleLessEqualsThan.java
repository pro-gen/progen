package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

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
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    Double operador1 = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
    Double operador2 = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 <= operador2;
  }

}
