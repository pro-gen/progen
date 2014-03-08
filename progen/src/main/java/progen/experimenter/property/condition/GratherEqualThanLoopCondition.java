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

    /**
     * Constructor por defecto en el que se comprueba que el incremento sea
     * correcto para el tipo de condición.
     * 
     * @param increment
     *            El incremento a validar.
     */
    public GratherEqualThanLoopCondition(double increment) {
	if (increment >= 0) {
	    throw new IllegalArgumentException(Error.get(30));
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.experimenter.LoopCondition#end(double, double)
     */
    @Override
    public boolean end(double current, double end) {
	return Math.abs(current - end) < LoopCondition.TOLERANCE;
    }

}
