package progen.experimenter.property;

import progen.context.ProGenContext;

/**
 * Implementación de una propiedad de experimentos que es una enumeración de
 * elementos.
 * 
 * @author jirsis
 * @since 2.0
 */
public class EnumerationProperty implements Property {
  /** El nombre de la propiedad a la que se le darán distintos valores. */
  private String label;
  /** Índice del valor actual. */
  private int index;
  /** Valores que puede tener la propiedad. */
  private String [] values;

  /**
   * Constructor genérico de la clase, que recibe el nombre de la propiedad de
   * la que se obtendrán distintos valores.
   * 
   * @param label
   *          El nombre de la propiedad.
   */
  public EnumerationProperty(String label) {
    this.label = label.replace("progen.experimenter", "progen");
    this.index = 0;
    values = ProGenContext.getMandatoryProperty(label).split("\\|");
    if (values.length > 1) {
      for (int i = 0; i < values.length; i++) {
        values[i] = values[i].trim();
      }
    } else {
      throw new IlegalPropertySeparatorException(label);
    }
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String getValue() {
    return values[index];
  }

  @Override
  public boolean hasNext() {
    return index < values.length - 1;
  }

  @Override
  public String nextValue() {
    String value = null;
    if (++index < values.length) {
      value = values[index];
    }
    return value;
  }

  @Override
  public void reset() {
    index = 0;
  }

  @Override
  public String toString() {
    final StringBuilder enumerationProperty = new StringBuilder();
    for (String value : values) {
      enumerationProperty.append(value).append(";");
    }
    enumerationProperty.append("[" + values[index] + "],");
    return enumerationProperty.toString();
  }
}
