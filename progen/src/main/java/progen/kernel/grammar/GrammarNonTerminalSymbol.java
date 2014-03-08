package progen.kernel.grammar;

/**
 * Implementación de un símbolo no terminal de una gramática.
 * Proporciona los métodos necesarios para especificar y definir
 * tanto el símbolo que representa al no terminal como el valor concreto.
 * @author jirsis
 * @since 2.0
 */
public class GrammarNonTerminalSymbol implements GrammarSymbol{
	/** Valor del no terminal. */
	private String value;
	/** Simbolo que representa al no terminal. */
	private String symbol;
	
	/**
	 * Constructor genérico de la clase que recibe el símbolo y el valor que definen al no terminal.
	 * @param symbol Símbolo para su representación.
	 * @param value Valor del no terminal
	 */
	public GrammarNonTerminalSymbol(String symbol, String value){
		this.symbol=symbol;
		this.value=value;
	}
	
	/**
	 * Devuelve la representación en forma de String del no terminal.
	 * @return la representación del símbolo no terminal.
	 */
	public String toString(){
		return getSymbol().toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other){
		boolean equals=false;
		if(other instanceof GrammarNonTerminalSymbol){
			equals=equals((GrammarNonTerminalSymbol)other);
		}
		return equals;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return symbol.hashCode();
	}

	/**
	 * Comprueba que el parámetro proporcionado es de un símbolo igual al actual.
	 * @param other Símbolo con el que comparar.
	 * @return <code>true</code> los dos símbolos se representan de la misma forma.
	 */
	public boolean equals(GrammarNonTerminalSymbol other){
		return this.compareTo(other)==0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see progen.kernel.grammar.GrammarSymbol#compareTo(progen.kernel.grammar.GrammarSymbol)
	 */
	public int compareTo(GrammarSymbol other) {
		return symbol.compareTo(other.getSymbol());
	}
	
	/*
	 * (non-Javadoc)
	 * @see progen.kernel.grammar.GrammarSymbol#getSymbol()
	 */
	public String getSymbol() {
		return symbol;
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.grammar.GrammarSymbol#getValue()
	 */
	public String getValue() {
		return value;
	}
}
