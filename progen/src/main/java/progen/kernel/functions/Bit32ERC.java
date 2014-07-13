package progen.kernel.functions;

import java.util.Random;

/**
 * ERC que define un valor aleatorio de 32 bits.
 * 
 * @author David Fernandez García & César Estébanez Tascón
 * @since 1.0
 * @version 2.0
 * 
 */
public class Bit32ERC extends ERC {

  /**
   * Constructor que especifica que este ERC será de tipo int
   */
  public Bit32ERC() {
    super("int");
  }

  @Override
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "DMI_RANDOM_USED_ONLY_ONCE", justification = "Original design ProGen v1.0")
  protected Object defineERC() {
    return Integer.valueOf(new Random().nextInt());
  }

  @Override
  protected String printERC() {
    return "0x" + Integer.toHexString((Integer) getValue());
  }

  @Override
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value="CN_IDIOM_NO_SUPER_CALL", justification="super.clone() doesn't exists")
  public ERC clone() {
    return new Bit32ERC();
  }

}
