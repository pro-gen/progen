package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de desplazamiento de n bits a la derecha,
 * manteniendo el signo.
 * 
 * @author jirsis
 * @since 2.0
 */
public class ShiftUnsignedRight extends NonTerminal {

  private static final long serialVersionUID = -2523894986080663790L;

  /**
   * Constructor por defecto.
   */
  public ShiftUnsignedRight() {
    super("int$$int$$int", ">>>");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    Integer operador1 = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);
    Integer operador2 = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 >>> operador2;
  }

}
