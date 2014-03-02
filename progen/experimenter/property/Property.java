package progen.experimenter.property;

/**
 * Interface que define como acceder a los valores que podrá tomar una propiedad
 * determinada en un experimento.
 * 
 * @author jirsis
 * @since 2.0
 */
public interface Property {

    /**
     * Devuelve el nombre de la propiedad que será utilizada por ProGen.
     * 
     * @return La etiqueta que identifica a dicha propiedad
     */
    public String getLabel();

    /**
     * Devuelve el valor actual de la propiedad con la que se está
     * experimentando en un momento determinado.
     * 
     * @return El valor actual de la propiedad.
     */
    public String getValue();

    /**
     * Devuelve el siguiente valor que tomará la propiedad que se esté evaluando
     * o <code>null</code> en caso de que ya no pueda tomar más valores.
     * 
     * @return El siguiente valor de la propiedad.
     */
    public String nextValue();

    /**
     * Comprueba si puede tomar más valores o ya ha alcanzado el último.
     * 
     * @return <code>true</code> si puede tomar más valores y <code>false</code>
     *         en caso contrario.
     */
    public boolean hasNext();

    /**
     * Vuelve al primer valor de todos los que puede tomar la propiedad.
     */
    public void reset();
}
