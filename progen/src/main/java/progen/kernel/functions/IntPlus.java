package progen.kernel.functions;

import java.util.List;
import java.util.Map;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de suma de dos números enteros.
 * 
 * @author jirsis
 * @since 2.0
 */
public class IntPlus extends NonTerminal {

  private static final long serialVersionUID = -5220745637971553171L;

  /**
   * Constructor por defecto.
   */
  public IntPlus() {
    super("int$$int$$int", "+");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    final Integer sumando1 = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);
    final Integer sumando2 = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);
    return sumando1 + sumando2;
  }

}
