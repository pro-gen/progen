package progen.experimenter;

import progen.context.ProGenContext;

/**
 * Clase que ofrece de una forma centralizada la creación de los distintos tipos
 * de experimentos diponibles.
 * 
 * @author jirsis
 * @since 2.0
 */
public class ExperimenterFactory {

  private ExperimenterFactory(){
  
  }
  
  /**
   * Crea una instancia de un experimento simple o complejo, en función del
   * valor de la propiedad <i>progen.experimenter</i>
   * 
   * @return La instancia que definirá el experimento.
   */
  public static Experimenter makeInstance() {
    Experimenter experiment = null;
    final String experimenter = ProGenContext.getOptionalProperty("progen.experimenter", "off");
    if ("on".equals(experimenter)) {
      experiment = new MultipleExperimenter();
    } else {
      experiment = new SimpleExperimenter();
    }
    return experiment;
  }
}
