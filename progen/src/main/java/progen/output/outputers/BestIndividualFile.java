/**
 * 
 */
package progen.output.outputers;

import progen.kernel.population.Individual;
import progen.output.HistoricalData;
import progen.output.dataCollectors.DataCollector;
import progen.output.plugins.Plugin;

/**
 * @author jirsis
 * @since 2.0
 */
public class BestIndividualFile extends FileOutput {

    public BestIndividualFile() {
	super("bestIndividual.txt", false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.output.outputers.Outputer#print()
     */
    public void print() {
	this.init();
	HistoricalData historical = HistoricalData.makeInstance();
	DataCollector data;

	data = historical.getCurrentDataCollector("ExperimentIndividualData");
	Plugin plugin = data.getPlugin("best");

	Individual individual = (Individual) plugin.getValue();

	for (int i = 0; i < individual.getTotalADF(); i++) {
	    super.writer.println("(defun ADF" + i + " ");
	    super.writer.println(Formatter.tree(individual.getTrees().get(
		    "ADF" + i)));
	    super.writer.println(")\n");
	}

	for (int i = 0; i < individual.getTotalRPB(); i++) {
	    super.writer.println("(defun RPB" + i + " ");
	    super.writer.println(Formatter.tree(individual.getTrees().get(
		    "RPB" + i)));
	    super.writer.println(")\n");
	}
	this.close();
    }
}
