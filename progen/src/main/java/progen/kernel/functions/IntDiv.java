package progen.kernel.functions;

import java.util.List;
import java.util.Map;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa la función aritmética de dividir dos números. Esta
 * función recibe dos números de tipo entero y devuelve el resultado como
 * double.
 * 
 * Al ser una operación que puede no estar definida, cuando el divisor sea 0, se
 * devolverá el valor infinito (Double.POSITIVE_INFINITY)
 * 
 * @author jirsis
 * @since 2.0
 */
public class IntDiv extends NonTerminal {
  private static final long serialVersionUID = -3498040852200230614L;

  /**
   * Constructor de la función que define los valores por defecto, definiendo
   * como símbolo '/'.
   */
  public IntDiv() {
    super("double$$int$$int", "/");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    final Integer dividendo = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);
    final Integer divisor = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);
    double result;
    if (divisor == 0) {
      result = Double.POSITIVE_INFINITY;
    } else {
      result = ((double) dividendo) / divisor;
    }
    return result;
  }

}
