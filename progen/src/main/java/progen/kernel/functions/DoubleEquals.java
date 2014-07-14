package progen.kernel.functions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de igualdad de dos números reales.
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoubleEquals extends NonTerminal {

  private static final long serialVersionUID = -300163665525942491L;

  /**
   * Constructor por defecto.
   */
  public DoubleEquals() {
    super("boolean$$double$$double", "==");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final Double operador1 = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
    final Double operador2 = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
    
    return new BigDecimal(operador1.doubleValue()).compareTo(new BigDecimal(operador2.doubleValue())) == 0;
  }

}
