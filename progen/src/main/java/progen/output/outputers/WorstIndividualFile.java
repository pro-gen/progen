package progen.output.outputers;

import java.io.File;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.output.HistoricalData;
import progen.output.datacollectors.DataCollector;
import progen.output.plugins.Plugin;

/**
 * @author jirsis
 * @since 2.0
 */
public class WorstIndividualFile extends FileOutput {

  private DataCollector data;

  private boolean finish;

  public WorstIndividualFile() {
    super(ProGenContext.getMandatoryProperty("progen.output.experiment") + File.pathSeparator + "bestIndividual.txt", false);
    final HistoricalData historical = HistoricalData.makeInstance();
    data = historical.getCurrentDataCollector("ExperimentIndividualData");
    finish = false;
  }

  @Override
  public void print() {
    if (finish) {
      final Plugin plugin = data.getPlugin("worst");
      final Individual individual = (Individual) plugin.getValue();
      for (int i = 0; i < individual.getTotalADF(); i++) {
        super.getWriter().println(Formatter.tree(individual.getTrees().get("ADF" + i)));
      }
      for (int i = 0; i < individual.getTotalRPB(); i++) {
        super.getWriter().println(Formatter.tree(individual.getTrees().get("RPB" + i)));
      }
    }
  }

  public void activateFinishOutput() {
    finish = true;
  }

}
