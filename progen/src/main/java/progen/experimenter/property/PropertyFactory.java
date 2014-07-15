package progen.experimenter.property;

import progen.context.ProGenContext;

/**
 * Fábrica que genera todos los tipos de iteradores de propiedades, en función
 * del separador que se utilice.
 * 
 * @author jirsis
 * @since 2.0
 */
public final class PropertyFactory {
  
  private PropertyFactory(){
    
  }

  /**
   * Devuelve el iterador de valores que puede tomar una propiedad, en función
   * del separador que se use al definir la propiedad. De tal forma, que si:
   * <ul>
   * <li>el carácter es <code>'|' ('&#92;u007c')</code> será una enumeración de
   * valores.</li>
   * <li>el carácter es <code>';' ('&#92;u003b')</code> generará un conjunto de
   * valores que serán contruidos como si fuera un bucle <code>for</code>.</li>
   * 
   * @param label
   *          La etiqueta que va a generar todos los valores
   * @return Una instancia con un iterador de valores acorde a la familia a la
   *         que pertenezca.
   */
  public static Property makeInstance(String label) {
    Property property = null;
    final String value = ProGenContext.getMandatoryProperty(label);

    if (value.contains(";")) {
      property = new LoopProperty(label);
    } else if (value.contains("|")) {
      property = new EnumerationProperty(label);
    } else {
      throw new IlegalPropertySeparatorException(label);
    }
    return property;
  }

}
