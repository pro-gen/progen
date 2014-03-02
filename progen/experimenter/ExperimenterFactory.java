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

    /**
     * Crea una instancia de un experimento simple o complejo, en función del
     * valor de la propiedad <i>progen.experimenter</i>
     * 
     * @return La instancia que definirá el experimento.
     */
    public static Experimenter makeInstance() {
	Experimenter experiment = null;
	String experimenter = ProGenContext.getOptionalProperty("progen.experimenter", "off"); 
	if (experimenter.equals("on")) {
	    experiment = new MultipleExperimenter();
	} else {
	    experiment = new SimpleExperimenter();
	}
	return experiment;
    }
}
