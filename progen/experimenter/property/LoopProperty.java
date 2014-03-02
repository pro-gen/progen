/**
 * 
 */
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
     *            El nombre de la propiedad.
     */
    public LoopProperty(String label) {
	this.label = label.replace("progen.experimenter", "progen");

	String loop[] = ProGenContext.getMandatoryProperty(label).split(";");
	if (loop.length != 3) {
	    String msg = Error.get(29) + " (" + label + ")";
	    throw new IllegalArgumentException(msg);
	}
	try {
	    this.start = Double.parseDouble(loop[0].trim());
	    this.current = this.start;
	    this.end = Double.parseDouble(loop[1].trim());
	    this.increment = Double.parseDouble(loop[2].trim().replaceAll("%",
		    ""));
	    this.isPercent = loop[2].endsWith("%");
	    if (isPercent) {
		this.increment = (end - start) * increment / 100.0;
	    }
	    condition = LoopCondition.makeInstance(this.start, this.end,
		    this.increment);
	} catch (NumberFormatException e) {
	    String msg = Error.get(29) + " " + label + " " + e.getLocalizedMessage();
	    throw new IllegalArgumentException(msg);
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgumentException(label + ": " + e.getMessage());
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.experimenter.Property#getLabel()
     */
    @Override
    public String getLabel() {
	return label;
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.experimenter.Property#getValue()
     */
    @Override
    public String getValue() {
	String value = current + "";
	if (isPercent) {
	    value = (current * 100.0) + "";
	    value += "%";
	}
	return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.experimenter.Property#hasNext()
     */
    @Override
    public boolean hasNext() {
	return !condition.end(current, end);
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.experimenter.Property#nextValue()
     */
    @Override
    public String nextValue() {
	current += increment;
	return this.getValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.experimenter.Property#reset()
     */
    @Override
    public void reset() {
	current = start;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder stb = new StringBuilder();
	stb.append(start + ";");
	stb.append(end + ";");
	stb.append(increment);
	stb.append("[" + current + "],");
	return stb.toString();
    }
}
