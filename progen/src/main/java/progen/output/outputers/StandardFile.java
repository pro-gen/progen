/**
 *
 */
package progen.output.outputers;

import java.util.Map;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.output.HistoricalData;
import progen.output.datacollectors.DataCollector;
import progen.output.plugins.Mean;
import progen.output.plugins.Plugin;

/**
 * Se genera por la salida estándar una tabla con la información estadística de
 * los datos de cada generación.
 *
 * @author jirsis
 * @since 2.0
 *
 */
public class StandardFile extends FileOutput {

	/** Separador central de la tabla que imprime por pantalla. */
	private static final String CENTER_SEP = " | ";
	/** Separador izquierdo de la tabla que imprime por pantalla. */
	private static final String LEFT_SEP = CENTER_SEP.substring(1,3);
	/** Separador derecho de la tabla que imprime por pantalla. */
	private static final String RIGHT_SEP = CENTER_SEP.substring(1);
	/** Ancho de cada columna de la tabla. */
	private static final int WIDTH_COLUMN = 10;

	/** Ancho de la primera columna. */
	private int firstColumnWidth;
	/** Ancho de la segunda columna. */
	private int secondColumnWidth;

	/** Almacén de datos para recuperar. */
	private HistoricalData historical;

	/** Datos de un experimento concreto. */
	private DataCollector experimentData;

	/** Separador horizontal de filas. */
	private String hline;

    private int totalRPB;
    private int totalADF;

	/**
	 * Constructor por defecto.
	 */
	public StandardFile() {
		super("standardOutput.txt", true);
		historical = HistoricalData.makeInstance();
		experimentData = historical.getDataCollectorExperiment("ExperimentIndividualData");
		int maxLineLength=getMaxLine().length();
		StringBuilder stb = new StringBuilder(" ");
		for (int i = 2; i < maxLineLength-1; i++){
			stb.append("-");
		}

		hline = String.format("%s%n",stb.toString());
        totalRPB=Integer.parseInt(ProGenContext.getMandatoryProperty("progen.total.RPB"));
        totalADF=ProGenContext.getOptionalProperty("progen.total.ADF", 0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see progen.output.outputers.Outputer#print()
	 */
	public void print() {
		this.init();
		int currentGeneration = historical.getCurrentGeneration();
		int maxGenerations=ProGenContext.getOptionalProperty(
				"progen.max-generation", Integer.MAX_VALUE);
		if(currentGeneration<maxGenerations){
			printHeader(maxGenerations);
			printBody();
			printTail();
		}
		this.close();
	}

	/**
	 * Imprime la cabecera de la tabla de resultados.
	 */
	private void printHeader(int maxGenerations) {
            //		int maxGenerations = ProGenContext.getMandatoryProperty("progen.max-generation").length();
		String generation = getLiterals().getString("generation")
				+ Formatter.right(historical.getCurrentGeneration()+"", maxGenerations);
		getWriter().printf("%n%n%s", hline);
		getWriter().printf("%s%s%s%n", LEFT_SEP, Formatter.center(generation, getMaxLine().length()-LEFT_SEP.length()-RIGHT_SEP.length()), RIGHT_SEP);

		getWriter().printf("%s", hline);
	}

	/**
	 * Imprime el grueso de la tabla.
	 */
	private void printBody() {
		Individual bestTotal=(Individual)(historical.getDataCollectorExperiment("ExperimentIndividualData").getPlugin("best").getValue());
		Individual bestGeneration=(Individual)(historical.getCurrentDataCollector("ExperimentIndividualData").getPlugin("best").getValue());

		printIndividual();
		printTime();
		if(bestTotal.equals(bestGeneration)){
			printBest();
		}
	}

	/**
	 * Imprime la tabla del mejor individuo.
	 */
	private void printBest() {
		printBestHeaderTable();
		printBestSubHeaderTable();
		printBestData();
		printBestTail();
	}

	/**
	 * Imprime la cabecera de la tabla del mejor individuo.
	 */
	private void printBestHeaderTable() {
		StringBuilder line = new StringBuilder(LEFT_SEP);
		int padding;

		line.append(String.format("%s%s", Formatter.left(getLiterals().getString("newBestIndividual"), firstColumnWidth+secondColumnWidth), CENTER_SEP));

		padding=Formatter.center(getLiterals().getString("raw"), WIDTH_COLUMN).length();
		padding+=Formatter.center(getLiterals().getString("adjusted"), WIDTH_COLUMN).length()+3;
		line.append(String.format("%s%s", Formatter.center(getLiterals().getString("fitness"), padding), CENTER_SEP));

		padding=Formatter.center(getLiterals().getString("nodes"), WIDTH_COLUMN).length();
		padding+=Formatter.center(getLiterals().getString("depth"), WIDTH_COLUMN).length()+3;
		for(int i=0;i<totalRPB;i++){
			line.append(String.format( "%s%s", Formatter.center("RBP"+i, padding),CENTER_SEP));
		}
		for(int i=0;i<totalADF;i++){
			line.append(String.format( "%s%s", Formatter.center("ADF"+i, padding), CENTER_SEP));
		}
		getWriter().printf("%s%n%s",line.toString(), hline);
	}

	/**
	 * Imprime la cabecera de la tabla de mejor individuo.
	 */
	private void printBestSubHeaderTable() {
		printIndividualSubHeaderTable();
	}

	/**
	 * Imprime los datos del mejor individuo.
	 */
	private void printBestData() {
		StringBuilder line;
		String ceilData;
		Individual best=(Individual)(experimentData.getPlugin("best").getValue());

		int padding=Formatter.center(getLiterals().getString("individual"), WIDTH_COLUMN).length();
		line=new StringBuilder(LEFT_SEP);
		line.append(String.format("%s", Formatter.center(" ", padding+CENTER_SEP.length())));
		line.append(String.format("%s%s", Formatter.left(getLiterals().getString("individual"), secondColumnWidth), CENTER_SEP));

		ceilData=String.format("%.3f", best.getRawFitness());
		line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN),CENTER_SEP));
		ceilData=String.format("%.5f", best.getAdjustedFitness());
		line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN),CENTER_SEP));

		for(int i=0;i<totalRPB;i++){
			line.append(String.format( "%s%s", Formatter.center(best.getTrees().get("RPB"+i).getRoot().getTotalNodes()+"", WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(best.getTrees().get("RPB"+i).getRoot().getMaximunDepth()+"", WIDTH_COLUMN),CENTER_SEP));
		}
		for(int i=0;i<totalADF;i++){
			line.append(String.format( "%s%s", Formatter.center(best.getTrees().get("ADF"+i).getRoot().getTotalNodes()+"", WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(best.getTrees().get("ADF"+i).getRoot().getMaximunDepth()+"", WIDTH_COLUMN),CENTER_SEP));
		}
		getWriter().printf("%s%n%s",line.toString(), hline);
	}

	/**
	 * Imprime los datos resumen del mejor individuo.
	 */
	private void printBestTail() {
		StringBuilder stb=new StringBuilder();
		Individual best=(Individual)(experimentData.getPlugin("best").getValue());
		for(int i=0;i<best.getTotalRPB();i++){
			stb.append(String.format("RPB%d:%n", i));
			stb.append(Formatter.tree(best.getTrees().get("RPB"+i)));
			stb.append(String.format("%n"));
		}

		for(int i=0;i<best.getTotalADF();i++){
			stb.append(String.format("ADF%d:%n", i));
			stb.append(Formatter.tree(best.getTrees().get("ADF"+i)));
			stb.append(String.format("%n"));
		}

		getWriter().println(stb.toString());
	}

	/**
	 * Imprime la tabla de tiempos.
	 */
	private void printTime() {
		printTimeHeaderTable();
		printTimeData();

	}

	/**
	 * Imprime los datos de la tabla de tiempos.
	 */
	private void printTimeData() {
		printTimeBreeding();
		printTimeEvaluation();
	}

	/**
	 * Imprime los tiempos de evaluación de individuos.
	 */
	private void printTimeEvaluation() {
		StringBuilder line = new StringBuilder(LEFT_SEP);
		int padding=hline.length()-(firstColumnWidth+secondColumnWidth)-2*CENTER_SEP.length()-3;
		padding=padding/2;
		Plugin evaluation = historical.getCurrentDataCollector("PopulationTimeData").getPlugin("evaluation");
		String ceilData;

		line.append(String.format("%s", Formatter.center(" ", firstColumnWidth)));
		line.append(String.format("%s%s", Formatter.left(getLiterals().getString("evaluation"), secondColumnWidth), CENTER_SEP));
		ceilData=String.format("%s", evaluation.getPlugin("mean").getValue().toString());
		line.append(String.format("%s%s", Formatter.center(ceilData, padding), CENTER_SEP));
		ceilData=String.format("%s", evaluation.getPlugin("total").getValue().toString());
		line.append(String.format("%s%s", Formatter.center(ceilData, padding), CENTER_SEP));
		getWriter().printf("%s%n%s",line.toString(), hline);
	}

	/**
	 * Imprime los tiempos de ejecución de individuos.
	 */
	private void printTimeBreeding() {
		StringBuilder line = new StringBuilder(LEFT_SEP);
		int padding=hline.length()-(firstColumnWidth+secondColumnWidth)-2*CENTER_SEP.length()-3;
		padding=padding/2;
		Plugin breeding = historical.getCurrentDataCollector("PopulationTimeData").getPlugin("breeding");
		String ceilData;

		line.append(String.format("%s", Formatter.center(" ", firstColumnWidth)));
		line.append(String.format("%s%s", Formatter.left(getLiterals().getString("breeding"), secondColumnWidth), CENTER_SEP));
		ceilData=String.format("%s", breeding.getPlugin("mean").getValue().toString());
		line.append(String.format("%s%s", Formatter.center(ceilData, padding), CENTER_SEP));
		ceilData=String.format("%s", breeding.getPlugin("total").getValue().toString());
		line.append(String.format("%s%s", Formatter.center(ceilData, padding), CENTER_SEP));
		getWriter().printf("%s%n",line.toString());
	}

	/**
	 * Imprime la cabecera de la tabla de tiempos.
	 */
	private void printTimeHeaderTable() {
		StringBuilder line = new StringBuilder(LEFT_SEP);
		int padding;

		line.append(String.format("%s%s", Formatter.left(getLiterals().getString("time"), firstColumnWidth+secondColumnWidth), CENTER_SEP));

		padding=hline.length()-(firstColumnWidth+secondColumnWidth)-2*CENTER_SEP.length()-3;
		padding=padding/2;

		line.append(String.format("%s%s",Formatter.center(getLiterals().getString("populationMean"), padding), CENTER_SEP));
		line.append(String.format("%s%s",Formatter.center(getLiterals().getString("totalPopulation"), padding), CENTER_SEP));

		getWriter().printf("%s%n%s",line.toString(), hline);
	}

	/**
	 * Imprime un individuo.
	 */
	private void printIndividual() {
		printIndividualHeaderTable();
		printIndividualSubHeaderTable();
		printIndividualData();
	}

	/**
	 * Imprime los datos de un individuo.
	 */
	private void printIndividualData() {
		printIndividualBestRunData();
		printIndividualGenerationMeanData();
		printIndividualWorstData();
	}

	/**
	 * Imprime los datos de la generación media.
	 */
	@SuppressWarnings("unchecked")
	private void printIndividualGenerationMeanData() {
		StringBuilder line;
		String ceilData;
		DataCollector populationData = historical.getCurrentDataCollector("PopulationData");
		Map<String, Mean> mean= (Map<String, Mean>) (populationData.getPlugin("individualMean").getValue());

		int padding=Formatter.center(getLiterals().getString("individual"), WIDTH_COLUMN).length();
		line=new StringBuilder(LEFT_SEP);
		line.append(String.format("%s", Formatter.center(" ", padding+CENTER_SEP.length())));
		line.append(String.format("%s%s", Formatter.left(getLiterals().getString("generationMean"), secondColumnWidth), CENTER_SEP));

		ceilData=String.format("%.3G", mean.get("raw").getValue());
		line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN),CENTER_SEP));
		ceilData=String.format("%.5f", mean.get("adjusted").getValue());
		line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN),CENTER_SEP));

		for(int i=0;i<totalRPB;i++){
			line.append(String.format( "%s%s", Formatter.center(mean.get("RPB"+i+"-nodes").getValue().toString(), WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(mean.get("RPB"+i+"-depth").getValue().toString(), WIDTH_COLUMN),CENTER_SEP));
		}
		for(int i=0;i<totalADF;i++){
			line.append(String.format( "%s%s", Formatter.center(mean.get("ADF"+i+"-nodes").getValue().toString(), WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(mean.get("ADF"+i+"-depth").getValue().toString(), WIDTH_COLUMN),CENTER_SEP));
		}
		getWriter().println(line.toString());	}

	/**
	 * Imprime los datos de la mejor ejecución de un individuo.
	 */
	private void printIndividualBestRunData() {
		StringBuilder line;
		String ceilData;
		Individual best=(Individual)(experimentData.getPlugin("best").getValue());

		int padding=Formatter.center(getLiterals().getString("individual"), WIDTH_COLUMN).length();
		line=new StringBuilder(LEFT_SEP);
		line.append(String.format("%s", Formatter.center(" ", padding+CENTER_SEP.length())));
		line.append(String.format("%s%s", Formatter.left(getLiterals().getString("bestRun"), secondColumnWidth), CENTER_SEP));

		ceilData=String.format("%.3f", best.getRawFitness());
		line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN),CENTER_SEP));
		ceilData=String.format("%.5f", best.getAdjustedFitness());
		line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN),CENTER_SEP));

		for(int i=0;i<totalRPB;i++){
			line.append(String.format( "%s%s", Formatter.center(best.getTrees().get("RPB"+i).getRoot().getTotalNodes()+"", WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(best.getTrees().get("RPB"+i).getRoot().getMaximunDepth()+"", WIDTH_COLUMN),CENTER_SEP));
		}
		for(int i=0;i<totalADF;i++){
			line.append(String.format( "%s%s", Formatter.center(best.getTrees().get("ADF"+i).getRoot().getTotalNodes()+"", WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(best.getTrees().get("ADF"+i).getRoot().getMaximunDepth()+"", WIDTH_COLUMN),CENTER_SEP));
		}
		getWriter().println(line.toString());

	}

	/**
	 * Imprime los datos del peor individuo.
	 */
	private void printIndividualWorstData() {
		StringBuilder line;
		String ceilData;
		Individual worst=(Individual)(historical.getCurrentDataCollector("ExperimentIndividualData").getPlugin("worst").getValue());

		int padding=Formatter.center(getLiterals().getString("individual"), WIDTH_COLUMN).length();
		line=new StringBuilder(LEFT_SEP);
		line.append(String.format("%s", Formatter.center(" ", padding+CENTER_SEP.length())));
		line.append(String.format("%s%s", Formatter.left(getLiterals().getString("worstRun"), secondColumnWidth), CENTER_SEP));

		ceilData=String.format("%.3G", worst.getRawFitness());
		line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN),CENTER_SEP));
		ceilData=String.format("%.5f", worst.getAdjustedFitness());
		line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN),CENTER_SEP));

		for(int i=0;i<totalRPB;i++){
			line.append(String.format( "%s%s", Formatter.center(worst.getTrees().get("RPB"+i).getRoot().getTotalNodes()+"", WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(worst.getTrees().get("RPB"+i).getRoot().getMaximunDepth()+"", WIDTH_COLUMN),CENTER_SEP));
		}
		for(int i=0;i<totalADF;i++){
			line.append(String.format( "%s%s", Formatter.center(worst.getTrees().get("ADF"+i).getRoot().getTotalNodes()+"", WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(worst.getTrees().get("ADF"+i).getRoot().getMaximunDepth()+"", WIDTH_COLUMN),CENTER_SEP));
		}
		getWriter().printf("%s%n%s",line.toString(), hline);

	}

	/**
	 * Imprime la cabecera de la subtabla de individuos.
	 */
	private void printIndividualSubHeaderTable() {
		// Print sub-head table
		StringBuilder line = new StringBuilder(LEFT_SEP);

		int padding=Formatter.center(getLiterals().getString("individual"), WIDTH_COLUMN).length();
		line.append(String.format("%s   %s", Formatter.center(" ", padding+secondColumnWidth), CENTER_SEP));
		line.append(String.format("%s%s", Formatter.center(getLiterals().getString("raw"), WIDTH_COLUMN), CENTER_SEP));
		line.append(String.format("%s%s", Formatter.center(getLiterals().getString("adjusted"), WIDTH_COLUMN), CENTER_SEP));
		for(int i=0;i<totalRPB;i++){
			line.append(String.format( "%s%s", Formatter.center(getLiterals().getString("nodes"), WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(getLiterals().getString("depth"), WIDTH_COLUMN),CENTER_SEP));
		}
		for(int i=0;i<totalADF;i++){
			line.append(String.format( "%s%s", Formatter.center(getLiterals().getString("nodes"), WIDTH_COLUMN),CENTER_SEP));
			line.append(String.format( "%s%s", Formatter.center(getLiterals().getString("depth"), WIDTH_COLUMN),CENTER_SEP));
		}
		getWriter().printf("%s%n",line.toString());

	}

	/**
	 * Imprime la cabecera de la tabla de individuos.
	 */
	private void printIndividualHeaderTable() {
		// Print head table
		StringBuilder line = new StringBuilder(LEFT_SEP);
		int padding;
		firstColumnWidth=WIDTH_COLUMN+CENTER_SEP.length();

		secondColumnWidth=Math.max(WIDTH_COLUMN, getLiterals().getString("generationMean").length());
		secondColumnWidth=Math.max(secondColumnWidth, getLiterals().getString("bestRun").length());
		secondColumnWidth=Math.max(secondColumnWidth, getLiterals().getString("worstRun").length());

		line.append(String.format("%s%s", Formatter.left(getLiterals().getString("individual"), firstColumnWidth+secondColumnWidth), CENTER_SEP));

		padding=Formatter.center(getLiterals().getString("raw"), WIDTH_COLUMN).length();
		padding+=Formatter.center(getLiterals().getString("adjusted"), WIDTH_COLUMN).length()+3;
		line.append(String.format("%s%s", Formatter.center(getLiterals().getString("fitness"), padding), CENTER_SEP));

		padding=Formatter.center(getLiterals().getString("nodes"), WIDTH_COLUMN).length();
		padding+=Formatter.center(getLiterals().getString("depth"), WIDTH_COLUMN).length()+3;
		for(int i=0;i<totalRPB;i++){
			line.append(String.format( "%s%s", Formatter.center("RBP"+i, padding),CENTER_SEP));
		}
		for(int i=0;i<totalADF;i++){
			line.append(String.format( "%s%s", Formatter.center("ADF"+i, padding), CENTER_SEP));
		}
		getWriter().printf("%s%n%s",line.toString(), hline);
	}

	/**
	 * Imprime los resumenes de las tablas.
	 */
	private void printTail() {
		//do nothing
	}

	/**
	 * Devuelve la línea más larga que contendrá una tabla.
	 * @return La línea más larga.
	 */
	private String getMaxLine(){
		StringBuilder line = new StringBuilder(LEFT_SEP);
		line.append(String.format("%s%s", Formatter.center(getLiterals().getString("individual"), WIDTH_COLUMN), CENTER_SEP));
		line.append(String.format("%s%s", Formatter.center(getLiterals().getString("generationMean"), WIDTH_COLUMN), CENTER_SEP));
		line.append(String.format("%s%s", Formatter.center(getLiterals().getString("raw"), WIDTH_COLUMN), CENTER_SEP));
		line.append(String.format("%s%s", Formatter.center(getLiterals().getString("adjusted"), WIDTH_COLUMN), CENTER_SEP));

		int totalTrees =0;
		totalTrees+=totalRPB;
		totalTrees+=totalADF;

		for (int i=0;i<totalTrees;i++) {
			line.append(String.format("%s%s", Formatter.center(getLiterals().getString("nodes"), WIDTH_COLUMN), CENTER_SEP));
			line.append(String.format("%s%s", Formatter.center(getLiterals().getString("depth"), WIDTH_COLUMN), CENTER_SEP));
		}
		return line.toString();

	}
}