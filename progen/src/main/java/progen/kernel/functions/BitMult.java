package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * @author Alberto Vegas Estrada
 * @version 1.0
 */
public class BitMult extends NonTerminal {

  private static final int NUMBER_OF_BITS = 32;
  private static final long serialVersionUID = -7114934864153434702L;

  /**
   * Default constructor.
   */
  public BitMult() {
    super("int$$int$$int", "*");
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
    final int x = (Integer) arguments.get(0).evaluate(userProgram, returnAddr);
    final int y = (Integer) arguments.get(1).evaluate(userProgram, returnAddr);

    Integer result = 0;

    for (int i = 0; i < NUMBER_OF_BITS; i++) {
      // Generate a mask with a 1 in position i
      final int mask = 1 << i;
      // Make X AND mask. Then count the number of bits set to 1 in the result.
      // There
      // will be one or zero bits set to 1. If there is one, that means that
      // there is
      // a 1 in position x_i, or a 0 otherwise.
      final int count = Integer.bitCount(mask & x);
      // If there were a 1 in x_i, then add to result the value 2^i * Y (or
      // which is the same Y << i)
      if (count > 0) {
        result += y << i;
      }
    }

    return (Integer) result;
  }

}