package progen.kernel.functions;

import java.util.List;
import java.util.Map;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de multiplicación de dos números enteros.
 * 
 * @author jirsis
 * @since 2.0
 */
public class IntMult extends NonTerminal {

  private static final long serialVersionUID = 312912623565842815L;

  /**
   * Constructor por defecto
   */
  public IntMult() {
    super("int$$int$$int", "*");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    final Integer multiplicando = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);
    final Integer multiplicador = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);
    return multiplicando * multiplicador;
  }

}
