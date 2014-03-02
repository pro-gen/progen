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
    private String values[];

    /**
     * Constructor genérico de la clase, que recibe el nombre de la propiedad de
     * la que se obtendrán distintos valores.
     * 
     * @param label
     *            El nombre de la propiedad.
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
	return values[index];
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.experimenter.Property#hasNext()
     */
    @Override
    public boolean hasNext() {
	return index < values.length - 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.experimenter.Property#nextValue()
     */
    @Override
    public String nextValue() {
	String value = null;
	if (++index < values.length) {
	    value = values[index];
	}
	return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.experimenter.Property#reset()
     */
    @Override
    public void reset() {
	index = 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder stb = new StringBuilder();
	for (String value : values) {
	    stb.append(value + ";");
	}
	stb.append("[" + values[index] + "],");
	return stb.toString();
    }

}
