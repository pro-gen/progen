package progen.kernel.tree;

import progen.context.MalformedPercentSuboptionException;
import progen.context.ProGenContext;
import progen.kernel.grammar.Grammar;

/**
 * Clase que implementa la forma más común de inicialización de los árboles en
 * programación genética, que es una mezcla de inicialización Full y Grow, de
 * forma que se elegirá un método u otro en función de la probabilidad
 * especificada en la propiedad 'progen.population.init-mode'
 * 
 * @author jirsis
 * 
 * @see progen.kernel.tree.Full
 * @see progen.kernel.tree.Grow
 * 
 * @since 2.0
 */
public class HalfAndHalf implements InitializeTreeMethod {
  private static final double DEFAULT_PROBABILITY = 0.5;
  private static final String PROGEN_POPULATION_INIT_MODE_PROPERTY = "progen.population.init-mode";
  private static final long serialVersionUID = 1140024727404110598L;
  /** Inicializador tipo Full */
  private InitializeTreeMethod full;
  /** Inicializador tipo Grow */
  private InitializeTreeMethod grow;
  /** Probabilidad de usar el inicializador Full */
  private double percentFull;

  /**
   * Constructor genérico de la clase, en la que se inicializan las
   * probabilidades de uso de los inicializadores y se comprueba que sumen 100%
   * En caso de no haber definido las probabilidades particulares de alguno de
   * los inicializadores, se asumirá que la probabilidad de los dos es del 50%.
   */
  public HalfAndHalf() {
    full = new Full();
    grow = new Grow();
    percentFull = ProGenContext.getSuboptionPercent(PROGEN_POPULATION_INIT_MODE_PROPERTY, "full", DEFAULT_PROBABILITY);
    final double percentGrow = ProGenContext.getSuboptionPercent(PROGEN_POPULATION_INIT_MODE_PROPERTY, "grow", DEFAULT_PROBABILITY);
    if (percentFull + percentGrow != 1) {
      throw new MalformedPercentSuboptionException(PROGEN_POPULATION_INIT_MODE_PROPERTY);
    }
  }

  @Override
  public void generate(Grammar grammar, Node root) {
    if (Math.random() >= percentFull) {
      full.generate(grammar, root);
    } else {
      grow.generate(grammar, root);
    }
  }

  @Override
  public void updateMaximunDepth() {
    full.updateMaximunDepth();
    grow.updateMaximunDepth();
  }

  @Override
  public void updateMaximunNodes() {
    full.updateMaximunNodes();
    grow.updateMaximunNodes();
  }

  @Override
  public void updateMinimunDepth() {
    full.updateMinimunDepth();
    full.updateMinimunDepth();
  }
}
