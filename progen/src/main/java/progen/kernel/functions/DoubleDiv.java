package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa la función aritmética de dividir dos números. Esta
 * función recibe dos números de tipo double y devuelve el resultado como
 * double.
 * 
 * Al ser una operación que puede no estar definida, cuando el divisor sea 0, se
 * devolverá el valor infinito (Double.POSITIVE_INFINITY)
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoubleDiv extends NonTerminal {
  private static final long serialVersionUID = 8116097113843317595L;

  /**
   * Constructor de la función que define los valores por defecto, definiendo
   * como símbolo '/'.
   */
  public DoubleDiv() {
    super("double$$double$$double", "/");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final Double dividendo = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
    final Double divisor = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
    Double result;
    if (divisor != 0) {
      result = dividendo / divisor;
    } else {
      result = Double.POSITIVE_INFINITY;
    }
    return result;
  }

}
