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

  @Override
  public void defineValues() {
    // do nothing
  }

  @Override
  public boolean isDone() {
    return isDone;
  }

  @Override
  public void updateValues() {
    isDone = true;
  }

  @Override
  public void generateResults() {
    OutputStore outputs = OutputStore.makeInstance();
    outputs.print();
  }

  @Override
  public String defineExperimentDir() {
    return "results";
  }

  @Override
  public String finishMessage() {
    final StringBuilder finishMessage = new StringBuilder(20);
    finishMessage.append("----- ");
    finishMessage.append(Info.get(2));
    finishMessage.append(" -----");
    return finishMessage.toString();
  }

}
