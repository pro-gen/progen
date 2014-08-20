package progen.kernel.functions;

import java.util.Random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * ERC que define un valor aleatorio de 32 bits.
 * 
 * @author David Fernandez García & César Estébanez Tascón
 * @since 1.0
 * @version 2.0
 * 
 */
public class Bit32ERC extends ERC {

  private static final long serialVersionUID = -7046389973379467366L;

  /**
   * Constructor que especifica que este ERC será de tipo int
   */
  public Bit32ERC() {
    super("int");
  }

  @Override
  @SuppressFBWarnings(value = "DMI_RANDOM_USED_ONLY_ONCE", justification = "Original design ProGen v1.0")
  protected Object defineERC() {
    return Integer.valueOf(new Random().nextInt());
  }

  @Override
  protected String printERC() {
    return "0x" + Integer.toHexString((Integer) getValue());
  }

  @Override
  @SuppressFBWarnings(value = "CN_IDIOM_NO_SUPER_CALL", justification = "super.clone() doesn't exists")
  public ERC clone() {
    return new Bit32ERC();
  }

}
