package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de O lógico de dos variable booleanas de tal
 * forma que se evalúan los dos parámetros.
 * 
 * @author jirsis
 * @since 2.0
 */
public class OrInconditional extends NonTerminal {

  private static final long serialVersionUID = -1118116212156665961L;

  /**
   * Constructor por defecto.
   */
  public OrInconditional() {
    super("boolean$$boolean$$boolean", "|");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final Boolean operador1 = (Boolean) arguments.get(0).evaluate(userProgram, returnAddr);
    final Boolean operador2 = (Boolean) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 | operador2;
  }

}
