package progen.kernel.functions;

import java.util.List;
import java.util.Map;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase que define la funcionalidad básica de un ERC.
 * 
 * @author jirsis
 * @since 2.0
 */
public abstract class ERC extends Terminal implements Cloneable {

  private static final long serialVersionUID = 481135992381751762L;

  /**
   * Constructor que recibe el tipo de valor que almacena el ERC y
   * 
   * @param signature
   */
  public ERC(String signature) {
    super(signature, "");
    super.setValue(defineERC());
    super.setSymbol(this.printERC());
  }

  @Override
  public Object evaluate(List<Node> arguments, UserProgram userProgram, Map<String, Object> returnAddr) {
    return getValue();
  }

  /**
   * En el caso de los ERC, por definición no se puede volver a redefinir el
   * valor del mismo. Esto se consigue marcando el método como
   * <code>final</code> y así todos los ERC que se definan en el futuro, no
   * podrán sobreescribir dicho método, cumpliendo con el requerimiento de
   * impedir la redefinición del valor.
   */
  @Override
  public final void setValue(Object value) {
    // do nothing
  }

  /**
   * Define el valor concreto que tendrá dicho ERC.
   * 
   * @return El valor concreto del ERC
   */
  protected abstract Object defineERC();

  /**
   * Devuelve una forma legible del valor del ERC.
   * 
   * @return Una forma legible dle valor del ERC.
   */
  protected abstract String printERC();

  @Override
  public abstract ERC clone();

}
