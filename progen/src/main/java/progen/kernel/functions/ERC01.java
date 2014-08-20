package progen.kernel.functions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementación concreta de un ERC que toma valores entre 0 y 1.
 * 
 * @author jirsis
 * @since 2.0
 */
public class ERC01 extends ERC {

  private static final long serialVersionUID = -6494672512595532696L;

  /**
   * Constructor por defecto.
   */
  public ERC01() {
    super("double");
  }

  @Override
  protected Object defineERC() {
    return Math.random();
  }

  @Override
  protected String printERC() {
    return super.getValue().toString();
  }

  @Override
  @SuppressFBWarnings(value = "CN_IDIOM_NO_SUPER_CALL", justification = "super.clone() doesn't exists")
  public ERC clone() {
    return new ERC01();
  }
}
