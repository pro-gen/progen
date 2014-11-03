/**
 * 
 */
package progen.output.outputers;

import progen.kernel.population.Individual;
import progen.output.HistoricalData;
import progen.output.datacollectors.DataCollector;
import progen.output.plugins.Plugin;

/**
 * @author jirsis
 * @since 2.0
 */
public class BestIndividualFile extends FileOutput {

  private static final String END_OF_LINE_SYMBOL = ")\n";
  private static final String WHITE_SPACE_SYMBOL = " ";

  public BestIndividualFile() {
    super("bestIndividual.txt", false);
  }

  @Override
  public void print() {
    this.init();
    final HistoricalData historical = HistoricalData.makeInstance();
    DataCollector data;

    data = historical.getCurrentDataCollector("ExperimentIndividualData");
    final Plugin plugin = data.getPlugin("best");

    final Individual individual = (Individual) plugin.getValue();

    for (int i = 0; i < individual.getTotalADF(); i++) {
      getWriter().println("(defun ADF" + i + WHITE_SPACE_SYMBOL);
      getWriter().println(Formatter.tree(individual.getTrees().get("ADF" + i)));
      getWriter().println(END_OF_LINE_SYMBOL);
    }

    for (int i = 0; i < individual.getTotalRPB(); i++) {
      getWriter().println("(defun RPB" + i + WHITE_SPACE_SYMBOL);
      getWriter().println(Formatter.tree(individual.getTrees().get("RPB" + i)));
      getWriter().println(END_OF_LINE_SYMBOL);
    }
    this.close();
  }
}
