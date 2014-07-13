package progen.kernel.functions;

/**
 * Identifica una variable de tipo entero, identificada con el literal
 * <code>iN</code>
 * 
 * @author jirsis
 * @since 2.0
 */
public class IntN extends Terminal {

  private static final long serialVersionUID = 3928014279351054984L;

  /**
   * Constructor por defecto.
   */
  public IntN() {
    super("int", "iN");
  }
}
