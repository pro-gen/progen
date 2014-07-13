package progen.kernel.functions;

/**
 * Identifica el valor booleano de FALSO
 * 
 * @author jirsis
 * @since 2.0
 */
public class False extends Terminal {

  private static final long serialVersionUID = -5583977807814709823L;

  /**
   * Constructor por defecto.
   */
  public False() {
    super("boolean", "FALSE");
    super.setValue(Boolean.FALSE);
  }

  public void setValue(Object value) {
    // do nothing
  }

}
