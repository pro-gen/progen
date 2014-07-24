package progen.kernel.functions;

import java.util.List;
import java.util.Map;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de multiplicación de dos números reales.
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoubleMult extends NonTerminal {

  private static final long serialVersionUID = -4700661737586770471L;

  /**
   * Constructor por defecto
   */
  public DoubleMult() {
    super("double$$double$$double", "*");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    final Double multiplicando = (Double) arguments.get(0).evaluate(userProgram, returnAddr);
    final Double multiplicador = (Double) arguments.get(1).evaluate(userProgram, returnAddr);
    return multiplicando * multiplicador;
  }

}
