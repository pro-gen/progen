package progen.kernel.functions;

/**
 * @author jirsis
 * 
 */
public abstract class NonTerminal extends Function {

  private static final long serialVersionUID = 5173228444409252772L;

  /**
   * @param signature
   * @param symbol
   */
  public NonTerminal(String signature, String symbol) {
    super(signature, symbol);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
  
  @Override
  public boolean equals(Object other){
    boolean equals = true;
    if(other == null ){
      equals = false;
    } else if (other instanceof NonTerminal){
      equals = this.hashCode() == other.hashCode();
    }else {
      equals = false;
    }
    return equals;
  }

}
