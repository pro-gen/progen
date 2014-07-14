package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.functions.NonTerminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que implementa el operador de O lógico de dos variable booleanas, en
 * forma de cortocircuito. De esta forma, en cuanto una de los parámetros sea
 * verdadero, no se continua evaluando el otro parámetro.
 * 
 * @author jirsis
 * @since 2.0
 */
public class Or extends NonTerminal {

  private static final long serialVersionUID = 1392654173883119488L;

  /**
   * Constructor por defecto.
   */
  public Or() {
    super("boolean$$boolean$$boolean", "||");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final Boolean operador1 = (Boolean) arguments.get(0).evaluate(userProgram, returnAddr);
    final Boolean operador2 = (Boolean) arguments.get(1).evaluate(userProgram, returnAddr);
    return operador1 || operador2;
  }

}
