package progen.experimenter;

import progen.kernel.error.Info;
import progen.output.outputers.OutputStore;

/**
 * Clase que implementa la funcionalidad de un experimento, en el que no se ha
 * definido la opción de múltiples experimentos.
 * 
 * @author jirsis
 * @since 2.0
 */
public class SimpleExperimenter extends Experimenter {
  private boolean isDone;

  /** Constructor por defecto. */
  public SimpleExperimenter() {
    super();
    isDone = false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see progen.experimenter.Experimenter#defineValue()
   */
  @Override
  public void defineValues() {
    // do nothing
  }

  /*
   * (non-Javadoc)
   * 
   * @see progen.experimenter.Experimenter#isDone()
   */
  @Override
  public boolean isDone() {
    return isDone;
  }

  /*
   * (non-Javadoc)
   * 
   * @see progen.experimenter.Experimenter#updateValues()
   */
  @Override
  public void updateValues() {
    isDone = true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see progen.experimenter.Experimenter#generateResults()
   */
  @Override
  public void generateResults() {
    OutputStore outputs = OutputStore.makeInstance();
    outputs.print();
  }

  /*
   * (non-Javadoc)
   * 
   * @see progen.experimenter.Experimenter#defineExperimentDir()
   */
  @Override
  public String defineExperimentDir() {
    return "results";
  }

  /*
   * (non-Javadoc)
   * 
   * @see progen.experimenter.Experimenter#finishMessage()
   */
  @Override
  public String finishMessage() {
    StringBuilder stb = new StringBuilder(20);
    stb.append("----- ");
    stb.append(Info.get(2));
    stb.append(" -----");
    return stb.toString();
  }

}
