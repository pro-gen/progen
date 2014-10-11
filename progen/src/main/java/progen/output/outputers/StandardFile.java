package progen.output.outputers;

import java.util.Map;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.output.HistoricalData;
import progen.output.dataCollectors.DataCollector;
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
@SuppressWarnings("checkstyle:multiplestringliterals")
public class StandardFile extends FileOutput {

  private static final int EXTRA_PADDING = 3;
  private static final String WORST_RUN_LITERAL = "worstRun";
  private static final String BEST_RUN_LITERAL = "bestRun";
  private static final String DASH_DEPTH_LITERAL = "-depth";
  private static final String DASH_NODES_LITERAL = "-nodes";
  private static final String GENERATION_MEAN_LITERAL = "generationMean";
  private static final String POPULATION_DATA_LITERAL = "PopulationData";
  private static final String BREEDING_LITERAL = "breeding";
  private static final String TOTAL_LITERAL = "total";
  private static final String MEAN_LITERAL = "mean";
  private static final String EVALUATION_LITERAL = "evaluation";
  private static final String POPULATION_TIME_DATA_LITERAL = "PopulationTimeData";
  private static final String INDIVIDUAL_LITERAL = "individual";
  private static final String ADF_LITERAL = "ADF";
  private static final String RBP_LITERAL = "RBP";
  private static final String DEPTH_LITERAL = "depth";
  private static final String NODES_LITERAL = "nodes";
  private static final String FITNESS_LITERAL = "fitness";
  private static final String ADJUSTED_LITERAL = "adjusted";
  private static final String RAW_LITERAL = "raw";
  private static final String BEST_LITERAL = "best";
  private static final String BLANK_SPACE_SYMBOL = " ";
  private static final String EXPERIMENT_INDIVIDUAL_DATA_LITERAL = "ExperimentIndividualData";
  /** Separador central de la tabla que imprime por pantalla. */
  private static final String CENTER_SEP = " | ";
  /** Separador izquierdo de la tabla que imprime por pantalla. */
  private static final String LEFT_SEP = CENTER_SEP.substring(1, 3);
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
    experimentData = historical.getDataCollectorExperiment(EXPERIMENT_INDIVIDUAL_DATA_LITERAL);
    final int maxLineLength = getMaxLine().length();
    final StringBuilder lineOfDashes = new StringBuilder(BLANK_SPACE_SYMBOL);
    for (int i = 2; i < maxLineLength - 1; i++) {
      lineOfDashes.append("-");
    }

    hline = String.format("%s%n", lineOfDashes.toString());
    totalRPB = Integer.parseInt(ProGenContext.getMandatoryProperty("progen.total.RPB"));
    totalADF = ProGenContext.getOptionalProperty("progen.total.ADF", 0);
  }

  @Override
  public void print() {
    this.init();
    final int currentGeneration = historical.getCurrentGeneration();
    final int maxGenerations = ProGenContext.getOptionalProperty("progen.max-generation", Integer.MAX_VALUE);
    if (currentGeneration < maxGenerations) {
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
    final String generation = getLiterals().getString("generation") + Formatter.right(historical.getCurrentGeneration(), maxGenerations);
    getWriter().printf("%n%n%s", hline);
    getWriter().printf("%s%s%s%n", LEFT_SEP, Formatter.center(generation, getMaxLine().length() - LEFT_SEP.length() - RIGHT_SEP.length()), RIGHT_SEP);

    getWriter().printf("%s", hline);
  }

  /**
   * Imprime el grueso de la tabla.
   */
  private void printBody() {
    final Individual bestTotal = (Individual) (historical.getDataCollectorExperiment(EXPERIMENT_INDIVIDUAL_DATA_LITERAL).getPlugin(BEST_LITERAL).getValue());
    final Individual bestGeneration = (Individual) (historical.getCurrentDataCollector(EXPERIMENT_INDIVIDUAL_DATA_LITERAL).getPlugin(BEST_LITERAL).getValue());

    printIndividual();
    printTime();
    if (bestTotal.equals(bestGeneration)) {
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
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding;

    line.append(String.format("%s%s", Formatter.left(getLiterals().getString("newBestIndividual"), firstColumnWidth + secondColumnWidth), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(RAW_LITERAL), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(ADJUSTED_LITERAL), WIDTH_COLUMN).length() + EXTRA_PADDING;
    line.append(String.format("%s%s", Formatter.center(getLiterals().getString(FITNESS_LITERAL), padding), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN).length() + EXTRA_PADDING;
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format("%s%s", Formatter.center(RBP_LITERAL + i, padding), CENTER_SEP));
    }
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format("%s%s", Formatter.center(ADF_LITERAL + i, padding), CENTER_SEP));
    }
    getWriter().printf("%s%n%s", line.toString(), hline);
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
    final Individual best = (Individual) (experimentData.getPlugin(BEST_LITERAL).getValue());

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format("%s", Formatter.center(BLANK_SPACE_SYMBOL, padding + CENTER_SEP.length())));
    line.append(String.format("%s%s", Formatter.left(getLiterals().getString(INDIVIDUAL_LITERAL), secondColumnWidth), CENTER_SEP));

    ceilData = String.format("%.3f", best.getRawFitness());
    line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format("%.5f", best.getAdjustedFitness());
    line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printBestDataRPB(line, best);
    printBestDataADF(line, best);
    getWriter().printf("%s%n%s", line.toString(), hline);
  }

  private void printBestDataADF(StringBuilder line, final Individual best) {
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format("%s%s", Formatter.center(best.getTrees().get(ADF_LITERAL + i).getRoot().getTotalNodes(), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format("%s%s", Formatter.center(best.getTrees().get(ADF_LITERAL + i).getRoot().getMaximunDepth(), WIDTH_COLUMN), CENTER_SEP));
    }
  }

  private void printBestDataRPB(StringBuilder line, final Individual best) {
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format("%s%s", Formatter.center(best.getTrees().get(RBP_LITERAL + i).getRoot().getTotalNodes(), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format("%s%s", Formatter.center(best.getTrees().get(RBP_LITERAL + i).getRoot().getMaximunDepth(), WIDTH_COLUMN), CENTER_SEP));
    }
  }

  /**
   * Imprime los datos resumen del mejor individuo.
   */
  private void printBestTail() {
    final StringBuilder bestTail= new StringBuilder();
    final Individual best = (Individual) (experimentData.getPlugin(BEST_LITERAL).getValue());
    for (int i = 0; i < best.getTotalRPB(); i++) {
      bestTail.append(String.format("RPB%d:%n", i));
      bestTail.append(Formatter.tree(best.getTrees().get(RBP_LITERAL + i)));
      bestTail.append(String.format("%n"));
    }

    for (int i = 0; i < best.getTotalADF(); i++) {
      bestTail.append(String.format("ADF%d:%n", i));
      bestTail.append(Formatter.tree(best.getTrees().get(ADF_LITERAL + i)));
      bestTail.append(String.format("%n"));
    }

    getWriter().println(bestTail.toString());
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
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding = hline.length() - (firstColumnWidth + secondColumnWidth) - 2 * CENTER_SEP.length() - EXTRA_PADDING;
    padding = padding / 2;
    final Plugin evaluation = historical.getCurrentDataCollector(POPULATION_TIME_DATA_LITERAL).getPlugin(EVALUATION_LITERAL);
    String ceilData;

    line.append(String.format("%s", Formatter.center(BLANK_SPACE_SYMBOL, firstColumnWidth)));
    line.append(String.format("%s%s", Formatter.left(getLiterals().getString(EVALUATION_LITERAL), secondColumnWidth), CENTER_SEP));
    ceilData = String.format("%s", evaluation.getPlugin(MEAN_LITERAL).getValue().toString());
    line.append(String.format("%s%s", Formatter.center(ceilData, padding), CENTER_SEP));
    ceilData = String.format("%s", evaluation.getPlugin(TOTAL_LITERAL).getValue().toString());
    line.append(String.format("%s%s", Formatter.center(ceilData, padding), CENTER_SEP));
    getWriter().printf("%s%n%s", line.toString(), hline);
  }

  /**
   * Imprime los tiempos de ejecución de individuos.
   */
  private void printTimeBreeding() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding = hline.length() - (firstColumnWidth + secondColumnWidth) - 2 * CENTER_SEP.length() - EXTRA_PADDING;
    padding = padding / 2;
    final Plugin breeding = historical.getCurrentDataCollector(POPULATION_TIME_DATA_LITERAL).getPlugin(BREEDING_LITERAL);
    String ceilData;

    line.append(String.format("%s", Formatter.center(BLANK_SPACE_SYMBOL, firstColumnWidth)));
    line.append(String.format("%s%s", Formatter.left(getLiterals().getString(BREEDING_LITERAL), secondColumnWidth), CENTER_SEP));
    ceilData = String.format("%s", breeding.getPlugin(MEAN_LITERAL).getValue().toString());
    line.append(String.format("%s%s", Formatter.center(ceilData, padding), CENTER_SEP));
    ceilData = String.format("%s", breeding.getPlugin(TOTAL_LITERAL).getValue().toString());
    line.append(String.format("%s%s", Formatter.center(ceilData, padding), CENTER_SEP));
    getWriter().printf("%s%n", line.toString());
  }

  /**
   * Imprime la cabecera de la tabla de tiempos.
   */
  private void printTimeHeaderTable() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding;

    line.append(String.format("%s%s", Formatter.left(getLiterals().getString("time"), firstColumnWidth + secondColumnWidth), CENTER_SEP));

    padding = hline.length() - (firstColumnWidth + secondColumnWidth) - 2 * CENTER_SEP.length() - EXTRA_PADDING;
    padding = padding / 2;

    line.append(String.format("%s%s", Formatter.center(getLiterals().getString("populationMean"), padding), CENTER_SEP));
    line.append(String.format("%s%s", Formatter.center(getLiterals().getString("totalPopulation"), padding), CENTER_SEP));

    getWriter().printf("%s%n%s", line.toString(), hline);
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
    final DataCollector populationData = historical.getCurrentDataCollector(POPULATION_DATA_LITERAL);
    final Map<String, Mean> mean = (Map<String, Mean>) (populationData.getPlugin("individualMean").getValue());

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format("%s", Formatter.center(BLANK_SPACE_SYMBOL, padding + CENTER_SEP.length())));
    line.append(String.format("%s%s", Formatter.left(getLiterals().getString(GENERATION_MEAN_LITERAL), secondColumnWidth), CENTER_SEP));

    ceilData = String.format("%.3G", mean.get(RAW_LITERAL).getValue());
    line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format("%.5f", mean.get(ADJUSTED_LITERAL).getValue());
    line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format("%s%s", Formatter.center(mean.get(RBP_LITERAL + i + DASH_NODES_LITERAL).getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format("%s%s", Formatter.center(mean.get(RBP_LITERAL + i + DASH_DEPTH_LITERAL).getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
    }
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format("%s%s", Formatter.center(mean.get(ADF_LITERAL + i + DASH_NODES_LITERAL).getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format("%s%s", Formatter.center(mean.get(ADF_LITERAL + i + DASH_DEPTH_LITERAL).getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
    }
    getWriter().println(line.toString());
  }

  /**
   * Imprime los datos de la mejor ejecución de un individuo.
   */
  private void printIndividualBestRunData() {
    StringBuilder line;
    String ceilData;
    final Individual best = (Individual) (experimentData.getPlugin(BEST_LITERAL).getValue());

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format("%s", Formatter.center(BLANK_SPACE_SYMBOL, padding + CENTER_SEP.length())));
    line.append(String.format("%s%s", Formatter.left(getLiterals().getString(BEST_RUN_LITERAL), secondColumnWidth), CENTER_SEP));

    ceilData = String.format("%.3f", best.getRawFitness());
    line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format("%.5f", best.getAdjustedFitness());
    line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printBestDataRPB(line, best);
    printBestDataADF(line, best);
    getWriter().println(line.toString());

  }

  /**
   * Imprime los datos del peor individuo.
   */
  private void printIndividualWorstData() {
    StringBuilder line;
    String ceilData;
    final Individual worst = (Individual) (historical.getCurrentDataCollector(EXPERIMENT_INDIVIDUAL_DATA_LITERAL).getPlugin("worst").getValue());

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format("%s", Formatter.center(BLANK_SPACE_SYMBOL, padding + CENTER_SEP.length())));
    line.append(String.format("%s%s", Formatter.left(getLiterals().getString(WORST_RUN_LITERAL), secondColumnWidth), CENTER_SEP));

    ceilData = String.format("%.3G", worst.getRawFitness());
    line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format("%.5f", worst.getAdjustedFitness());
    line.append(String.format("%s%s", Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printBestDataRPB(line, worst);
    printBestDataADF(line, worst);
    getWriter().printf("%s%n%s", line.toString(), hline);

  }

  /**
   * Imprime la cabecera de la subtabla de individuos.
   */
  private void printIndividualSubHeaderTable() {
    // Print sub-head table
    final StringBuilder line = new StringBuilder(LEFT_SEP);

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line.append(String.format("%s   %s", Formatter.center(BLANK_SPACE_SYMBOL, padding + secondColumnWidth), CENTER_SEP));
    line.append(String.format("%s%s", Formatter.center(getLiterals().getString(RAW_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format("%s%s", Formatter.center(getLiterals().getString(ADJUSTED_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format("%s%s", Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format("%s%s", Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    }
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format("%s%s", Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format("%s%s", Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    }
    getWriter().printf("%s%n", line.toString());

  }

  /**
   * Imprime la cabecera de la tabla de individuos.
   */
  private void printIndividualHeaderTable() {
    // Print head table
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding;
    firstColumnWidth = WIDTH_COLUMN + CENTER_SEP.length();

    secondColumnWidth = Math.max(WIDTH_COLUMN, getLiterals().getString(GENERATION_MEAN_LITERAL).length());
    secondColumnWidth = Math.max(secondColumnWidth, getLiterals().getString(BEST_RUN_LITERAL).length());
    secondColumnWidth = Math.max(secondColumnWidth, getLiterals().getString(WORST_RUN_LITERAL).length());

    line.append(String.format("%s%s", Formatter.left(getLiterals().getString(INDIVIDUAL_LITERAL), firstColumnWidth + secondColumnWidth), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(RAW_LITERAL), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(ADJUSTED_LITERAL), WIDTH_COLUMN).length() + EXTRA_PADDING;
    line.append(String.format("%s%s", Formatter.center(getLiterals().getString(FITNESS_LITERAL), padding), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN).length() + EXTRA_PADDING;
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format("%s%s", Formatter.center(RBP_LITERAL + i, padding), CENTER_SEP));
    }
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format("%s%s", Formatter.center(ADF_LITERAL + i, padding), CENTER_SEP));
    }
    getWriter().printf("%s%n%s", line.toString(), hline);
  }

  /**
   * Imprime los resumenes de las tablas.
   */
  private void printTail() {
    // do nothing
  }

  /**
   * Devuelve la línea más larga que contendrá una tabla.
   * 
   * @return La línea más larga.
   */
  private String getMaxLine() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    line.append(String.format("%s%s", Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format("%s%s", Formatter.center(getLiterals().getString(GENERATION_MEAN_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format("%s%s", Formatter.center(getLiterals().getString(RAW_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format("%s%s", Formatter.center(getLiterals().getString(ADJUSTED_LITERAL), WIDTH_COLUMN), CENTER_SEP));

    int totalTrees = 0;
    totalTrees += totalRPB;
    totalTrees += totalADF;

    for (int i = 0; i < totalTrees; i++) {
      line.append(String.format("%s%s", Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format("%s%s", Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    }
    return line.toString();

  }
}