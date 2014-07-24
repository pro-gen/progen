package progen.kernel.functions;

import java.util.List;
import java.util.Map;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa la función aritmética de restar dos números. Esta
 * función recibe dos números de tipo double y devuelve el resultado como
 * double.
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoubleMinus extends NonTerminal {

  private static final long serialVersionUID = 3546129932362573302L;

  /**
   * Constructor de la función que define los valores por defecto, definiendo
   * como símbolo '-'.
   */
  public DoubleMinus() {
    super("double$$double$$double", "-");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    final Double minuendo = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
    final Double sustraendo = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
    return minuendo - sustraendo;
  }

}
