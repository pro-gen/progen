package progen.kernel.functions;

/**
 * Implementación concreta de un ERC que toma valores entre 0 y 1.
 * 
 * @author jirsis
 * @since 2.0
 */
public class ERC01 extends ERC {

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
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value="CN_IDIOM_NO_SUPER_CALL", justification="super.clone() doesn't exists")
  public ERC clone() {
    return new ERC01();
  }
}
