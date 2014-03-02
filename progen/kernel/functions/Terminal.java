package progen.kernel.functions;

import java.util.HashMap;
import java.util.List;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase abstracta que representa a los elementos terminales tal y como se
 * entienden en Programación Genética.
 * 
 * Define los métodos necesarios para definir los distintos tipos de elementos
 * terminales.
 * 
 * @author jirsis
 * @since 2.0
 */
public abstract class Terminal extends Function {

    /** Valor concreto del terminal del árbol. */
    private Object value;

    /**
     * Constructor por defecto que recibe dos parámetros y llama al constructor
     * de la clase padre
     * 
     * @see Function
     * @param signature
     *            valor de retorno del terminal.
     * @param symbol
     *            con el que se representará el terminal.
     */
    public Terminal(String signature, String symbol) {
	super(signature, symbol);
	value = null;
    }

    /**
     * Define el valor que tendrá dicho terminal.
     * 
     * @param value
     *            el valor del terminal.
     */
    public void setValue(Object value) {
	this.value = value;
    }

    /**
     * Devuelve el valor concreto del terminal.
     * 
     * @return el valor concreto del terminal.
     */
    public Object getValue() {
	return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.kernel.functions.Terminal#evaluate(java.util.List,
     * progen.userprogram.UserProgram, java.util.HashMap)
     */
    @Override
    public Object evaluate(List<Node> arguments, UserProgram userProgram,
	    HashMap<String, Object> returnAddr) {
	return getValue();
    }

}
