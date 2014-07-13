package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de exponenciación de dos números reales.
 * Tiene las misma propiedades que java.lang.Math.pow(a, b)
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoublePow extends NonTerminal {

  private static final long serialVersionUID = 7012785606965973266L;

  /**
   * Constructor por defecto.
   */
  public DoublePow() {
    super("double$$double$$double", "^");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    Double base = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
    Double exponent = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
    return Math.pow(base, exponent);
  }

}
