package progen.experimenter.property;

import progen.context.ProGenContext;
import progen.experimenter.property.condition.LoopCondition;
import progen.kernel.error.Error;

/**
 * Implementación de una propiedad de experimentos que genera valores como si
 * fuera un bucle for.
 * 
 * También es importante que el valor de la condición de finalización sea un
 * valor que sea generado al modificar el valor de la condición.
 * 
 * En el caso de el incremento acaben con el carácter
 * <code>'%' ('&#92;u0025')</code>, se considerará que se está iterando con
 * valores porcentuales, de tal forma, que al solicitar el valor actual, se
 * devolverá acabando con el carácter <code>'%' ('&#92;u0025')</code>. También
 * se considerará que el incremento porcentual se aplicará al rango especificado
 * como <code> final - inicio </code>
 * 
 * @author jirsis
 * @since 2.0
 */
public class LoopProperty implements Property {
  private static final int ID_ERROR = 29;
  private static final double HUNDRED_PERCENT = 100.0;
  private static final int NUMBER_OF_ARGUMENTS = 3;
  private static final String SPACE_SYMBOL = " ";
  private static final String PERCENT_SYMBOL = "%";
  private static final String SEMI_COLON_SYMBOL = ";";
  /** El nombre de la propiedad a la que se le darán distintos valores. */
  private String label;
  /** Indica si el valor es un porcentaje o no. */
  private boolean isPercent;

  /** Valor inicial de la propiedad. */
  private double start;
  /** Valor final de la propiedad. */
  private double end;
  /** Incremento que se aplicará para obtener el siguiente valor. */
  private double increment;

  /** Valor actual de la propiedad. */
  private double current;

  /** Condición de parada del bucle */
  private LoopCondition condition;

  /**
   * Constructor genérico de la clase, que recibe el nombre de la propiedad de
   * la que se obtendrán distintos valores.
   * 
   * @param label
   *          El nombre de la propiedad.
   */
  public LoopProperty(String label) {
    this.label = label.replace("progen.experimenter", "progen");

    final String[] loop = ProGenContext.getMandatoryProperty(label).split(SEMI_COLON_SYMBOL);
    if (loop.length != NUMBER_OF_ARGUMENTS) {
      throw new IllegalArgumentException(Error.get(29) + " (" + label + ")");
    }
    try {
      this.start = Double.parseDouble(loop[0].trim());
      this.current = this.start;
      this.end = Double.parseDouble(loop[1].trim());
      this.increment = Double.parseDouble(loop[2].trim().replaceAll(PERCENT_SYMBOL, ""));
      this.isPercent = loop[2].endsWith(PERCENT_SYMBOL);
      if (isPercent) {
        this.increment = (end - start) * increment / HUNDRED_PERCENT;
      }
      condition = LoopCondition.makeInstance(this.start, this.end, this.increment);
    } catch (NumberFormatException e) {
      final String msg = Error.get(ID_ERROR) + SPACE_SYMBOL + label + SPACE_SYMBOL + e.getLocalizedMessage();
      throw new IllegalArgumentException(msg);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(label + ": " + e.getMessage());
    }
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String getValue() {
    String value = current + "";
    if (isPercent) {
      value = (current * HUNDRED_PERCENT) + "";
      value += PERCENT_SYMBOL;
    }
    return value;
  }

  @Override
  public boolean hasNext() {
    return !condition.end(current, end);
  }

  @Override
  public String nextValue() {
    current += increment;
    return this.getValue();
  }

  @Override
  public void reset() {
    current = start;
  }

  @Override
  public String toString() {
    final StringBuilder loopProperty = new StringBuilder();
    loopProperty.append(start + SEMI_COLON_SYMBOL);
    loopProperty.append(end + SEMI_COLON_SYMBOL);
    loopProperty.append(increment);
    loopProperty.append("[" + current + "],");
    return loopProperty.toString();
  }
}
