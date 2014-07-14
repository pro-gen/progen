/**
 * 
 */
package progen.experimenter.property.condition;

import progen.kernel.error.Error;

/**
 * @author jirsis
 * @since 2.0
 */
public class GratherEqualThanLoopCondition extends LoopCondition {

  private static final int ID_ERROR = 30;

  /**
   * Constructor por defecto en el que se comprueba que el incremento sea
   * correcto para el tipo de condición.
   * 
   * @param increment
   *          El incremento a validar.
   */
  public GratherEqualThanLoopCondition(double increment) {
    if (increment >= 0) {
      throw new IllegalArgumentException(Error.get(ID_ERROR));
    }
  }

  @Override
  public boolean end(double current, double end) {
    return Math.abs(current - end) < LoopCondition.TOLERANCE;
  }

}
