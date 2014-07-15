package progen.experimenter.property.condition;

import progen.kernel.error.Error;

/**
 * Clase que fabrica las distintas condiciones de los bucles, en función de los
 * parámetros que lo definen.
 * 
 * @author jirsis
 * @since 2.0
 */
public abstract class LoopCondition {

  /**
   * Tolerancia para la cual se considerará que se cumple la condición de
   * parada.
   */
  protected static final double TOLERANCE = 0.0000000001;

  private static final int ID_ERROR = 30;

  /**
   * Comprueba si el valor actual sigue cumpliendo la condición o no.
   * 
   * @param current
   *          Valor actual a comprobar.
   * @param end
   *          Valor final del bucle.
   * @return <code>true</code> si ha llegado a la condición de fin y
   *         <code>false</code> en caso contrario.
   */
  public abstract boolean end(double current, double end);

  /**
   * Método de generación de una instancia de un bucle. En función de si el
   * <i>comienzo</i> es mayor o menor que el <i>final</i>, devolverá una
   * instancia de un bucle que cumple la condición &gt;= o &lt;=. También
   * comprueba si con el incremento proporcionado se puede conseguir o no la
   * condición de parada.
   * 
   * @param start
   *          Valor inicial del bucle.
   * @param end
   *          Valor final del bucle.
   * @param increment
   *          Incremento en cada paso del bucle.
   * @return Una instancia que comprobará la condición de parada.
   * @throws IllegalArgumentException
   *           , en caso de el incremento no esté definido correctamente.
   */
  public static LoopCondition makeInstance(double start, double end, double increment) {
    LoopCondition condition = null;

    if (increment == 0) {
      throw new IllegalArgumentException(Error.get(ID_ERROR));
    }

    if (start < end) {
      condition = new LessEqualThanLoopCondition(increment);
    } else {
      condition = new GratherEqualThanLoopCondition(increment);
    }

    return condition;
  }
}
