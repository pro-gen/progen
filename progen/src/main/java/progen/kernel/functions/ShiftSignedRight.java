package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de desplazamiento de n bits a la derecha.
 * 
 * @author jirsis
 * @since 2.0
 */
public class ShiftSignedRight extends NonTerminal {

  private static final long serialVersionUID = -7855826753947292960L;

  /**
   * Constructor por defecto.
   */
  public ShiftSignedRight() {
    super("int$$int$$int", ">>");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final Integer operador1 = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);
    final Integer operador2 = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 >> operador2;
  }

}
