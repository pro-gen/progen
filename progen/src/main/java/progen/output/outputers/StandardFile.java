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

  private static final String FORMAT_3G = "%.3G";
  private static final String FORMAT_S_D_N = "%s%d:%n";
  private static final String FORMAT_N = "%n";
  private static final String FORMAT_S_N_S = "%s%n%s";
  private static final String FORMAT_5F = "%.5f";
  private static final String FORMAT_3F = "%.3f";
  private static final String FORMAT_S = "%s";
  private static final String FORMAT_S_S = "%s%s";
  private static final String FORMAT_S_N = "%s%n";
  private static final String LITERAL_WORST_RUN = "worstRun";
  private static final String LITERAL_BEST_RUN = "bestRun";
  private static final String LITERAL_GENERATION_MEAN = "generationMean";
  private static final String CENTER_SEP = " | ";
  private static final String DATACOLLECTOR_POPULATION_TIME_DATA = "PopulationTimeData";
  private static final String EXPERIMENT_INDIVIDUAL_DATA = "ExperimentIndividualData";
  private static final int EXTRA_PADDING = 3;
  private static final String LEFT_SEP = CENTER_SEP.substring(1, 3);
  private static final String LITERAL_ADF = "ADF";
  private static final String LITERAL_ADJUSTED = "adjusted";
  private static final String LITERAL_BREEDING = "breeding";
  private static final String LITERAL_DEPTH = "depth";
  private static final String LITERAL_EVALUATION = "evaluation";
  private static final String LITERAL_FITNESS = "fitness";
  private static final String LITERAL_INDIVIDUAL = "individual";
  private static final String LITERAL_NODES = "nodes";
  private static final String LITERAL_RAW = "raw";
  private static final String LITERAL_RBP = "RBP";
  private static final String PLUGIN_NAME_BEST = "best";
  private static final String PLUGIN_NAME_BREEDING = LITERAL_BREEDING;
  private static final String PLUGIN_NAME_EVALUATION = LITERAL_EVALUATION;
  private static final String PLUGIN_NAME_MEAN = "mean";
  private static final String PLUGIN_NAME_TOTAL = "total";
  private static final String RIGHT_SEP = CENTER_SEP.substring(1);
  private static final String SINGLE_BLANK_SPACE = " ";
  private static final int WIDTH_COLUMN = 10;

  /** Datos de un experimento concreto. */
  private DataCollector experimentData;
  /** Ancho de la primera columna. */
  private int firstColumnWidth;

  /** Almacén de datos para recuperar. */
  private HistoricalData historical;

  /** Separador horizontal de filas. */
  private String hline;

  /** Ancho de la segunda columna. */
  private int secondColumnWidth;

  private int totalADF;
  private int totalRPB;

  /**
   * Constructor por defecto.
   */
  public StandardFile() {
    super("standardOutput.txt", true);
    historical = HistoricalData.makeInstance();
    experimentData = historical.getDataCollectorExperiment(EXPERIMENT_INDIVIDUAL_DATA);
    totalRPB = Integer.parseInt(ProGenContext.getMandatoryProperty("progen.total.RPB"));
    totalADF = ProGenContext.getOptionalProperty("progen.total.ADF", 0);
    hline = String.format(FORMAT_S_N, calculateHline());
  }

  private void appendHeaders(StringBuilder line, int padding, int totalTrees, String type) {
    for (int i = 0; i < totalTrees; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(String.format("%s%d", type, i), padding), CENTER_SEP));
    }
  }

  private String calculateHline() {
    final int maxLineLength = getMaxLine().length();
    final StringBuilder stb = new StringBuilder(SINGLE_BLANK_SPACE);
    for (int i = 2; i < maxLineLength - 1; i++) {
      stb.append("-");
    }
    return stb.toString();
  }

  private void defineSecondColumnWidth() {
    secondColumnWidth = Math.max(WIDTH_COLUMN, getLiterals().getString(LITERAL_GENERATION_MEAN).length());
    secondColumnWidth = Math.max(secondColumnWidth, getLiterals().getString(LITERAL_BEST_RUN).length());
    secondColumnWidth = Math.max(secondColumnWidth, getLiterals().getString(LITERAL_WORST_RUN).length());
  }

  /**
   * Devuelve la línea más larga que contendrá una tabla.
   * 
   * @return La línea más larga.
   */
  private String getMaxLine() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_INDIVIDUAL), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_GENERATION_MEAN), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_RAW), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_ADJUSTED), WIDTH_COLUMN), CENTER_SEP));

    int totalTrees = 0;
    totalTrees += totalRPB;
    totalTrees += totalADF;

    for (int i = 0; i < totalTrees; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_NODES), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_DEPTH), WIDTH_COLUMN), CENTER_SEP));
    }
    return line.toString();

  }

  @Override
  public void print() {
    this.init();
    final int currentGeneration = historical.getCurrentGeneration();
    final int maxGenerations = ProGenContext.getOptionalProperty("progen.max-generation", Integer.MAX_VALUE);
    if (currentGeneration < maxGenerations) {
      printHeader(maxGenerations);
      printBody();
    }
    this.close();
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
   * Imprime los datos del mejor individuo.
   */
  private void printBestData() {
    StringBuilder line;
    String ceilData;
    final Individual best = (Individual) (experimentData.getPlugin(PLUGIN_NAME_BEST).getValue());

    final int padding = Formatter.center(getLiterals().getString(LITERAL_INDIVIDUAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format(FORMAT_S, Formatter.center(SINGLE_BLANK_SPACE, padding + CENTER_SEP.length())));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(LITERAL_INDIVIDUAL), secondColumnWidth), CENTER_SEP));

    ceilData = String.format(FORMAT_3F, best.getRawFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format(FORMAT_5F, best.getAdjustedFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printDataRPB(line, best);
    printDataADF(line, best);
    getWriter().printf(FORMAT_S_N_S, line.toString(), hline);
  }

  /**
   * Imprime la cabecera de la tabla del mejor individuo.
   */
  private void printBestHeaderTable() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding;

    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString("newBestIndividual"), firstColumnWidth + secondColumnWidth), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(LITERAL_RAW), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(LITERAL_ADJUSTED), WIDTH_COLUMN).length() + EXTRA_PADDING;
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_FITNESS), padding), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(LITERAL_NODES), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(LITERAL_DEPTH), WIDTH_COLUMN).length() + EXTRA_PADDING;
    appendHeaders(line, padding, totalRPB, LITERAL_RBP);
    appendHeaders(line, padding, totalADF, LITERAL_ADF);
    getWriter().printf(FORMAT_S_N_S, line.toString(), hline);
  }

  /**
   * Imprime la cabecera de la tabla de mejor individuo.
   */
  private void printBestSubHeaderTable() {
    printIndividualSubHeaderTable();
  }

  /**
   * Imprime los datos resumen del mejor individuo.
   */
  private void printBestTail() {
    final StringBuilder stb = new StringBuilder();
    final Individual best = (Individual) (experimentData.getPlugin(PLUGIN_NAME_BEST).getValue());
    for (int i = 0; i < best.getTotalRPB(); i++) {
      stb.append(String.format(FORMAT_S_D_N, LITERAL_RBP, i));
      stb.append(Formatter.tree(best.getTrees().get(LITERAL_RBP + i)));
      stb.append(String.format(FORMAT_N));
    }

    for (int i = 0; i < best.getTotalADF(); i++) {
      stb.append(String.format(FORMAT_S_D_N, LITERAL_ADF, i));
      stb.append(Formatter.tree(best.getTrees().get(LITERAL_ADF + i)));
      stb.append(String.format(FORMAT_N));
    }

    getWriter().println(stb.toString());
  }

  private void printBody() {
    final Individual bestTotal = (Individual) (historical.getDataCollectorExperiment(EXPERIMENT_INDIVIDUAL_DATA).getPlugin(PLUGIN_NAME_BEST).getValue());
    final Individual bestGeneration = (Individual) (historical.getCurrentDataCollector(EXPERIMENT_INDIVIDUAL_DATA).getPlugin(PLUGIN_NAME_BEST).getValue());

    printIndividual();
    printTime();
    if (bestTotal.equals(bestGeneration)) {
      printBest();
    }
  }

  private void printDataADF(StringBuilder line, Individual individual) {
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(Integer.valueOf(individual.getTrees().get(LITERAL_ADF + i).getRoot().getTotalNodes()), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(Integer.valueOf(individual.getTrees().get(LITERAL_ADF + i).getRoot().getMaximunDepth()), WIDTH_COLUMN), CENTER_SEP));
    }
  }

  private void printDataRPB(StringBuilder line, Individual individual) {
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(Integer.valueOf(individual.getTrees().get(LITERAL_RBP + i).getRoot().getTotalNodes()), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(Integer.valueOf(individual.getTrees().get(LITERAL_RBP + i).getRoot().getMaximunDepth()), WIDTH_COLUMN), CENTER_SEP));
    }
  }

  /**
   * Imprime la cabecera de la tabla de resultados.
   */
  private void printHeader(int maxGenerations) {
    final String generation = String.format(FORMAT_S_S,getLiterals().getString("generation"), Formatter.right(historical.getCurrentGeneration(), maxGenerations)); 
    getWriter().printf("%n%n%s", hline);
    getWriter().printf("%s%s%s%n", LEFT_SEP, Formatter.center(generation, getMaxLine().length() - LEFT_SEP.length() - RIGHT_SEP.length()), RIGHT_SEP);

    getWriter().printf(FORMAT_S, hline);
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
   * Imprime los datos de la mejor ejecución de un individuo.
   */
  private void printIndividualBestRunData() {
    StringBuilder line;
    String ceilData;
    final Individual best = (Individual) (experimentData.getPlugin(PLUGIN_NAME_BEST).getValue());

    final int padding = Formatter.center(getLiterals().getString(LITERAL_INDIVIDUAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format(FORMAT_S, Formatter.center(SINGLE_BLANK_SPACE, padding + CENTER_SEP.length())));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(LITERAL_BEST_RUN), secondColumnWidth), CENTER_SEP));

    ceilData = String.format(FORMAT_3F, best.getRawFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format(FORMAT_5F, best.getAdjustedFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printDataRPB(line, best);
    printDataADF(line, best);
    getWriter().println(line.toString());

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
    final DataCollector populationData = historical.getCurrentDataCollector("PopulationData");
    final Map<String, Mean> mean = (Map<String, Mean>) (populationData.getPlugin("individualMean").getValue());

    final int padding = Formatter.center(getLiterals().getString(LITERAL_INDIVIDUAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format(FORMAT_S, Formatter.center(SINGLE_BLANK_SPACE, padding + CENTER_SEP.length())));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(LITERAL_GENERATION_MEAN), secondColumnWidth), CENTER_SEP));

    ceilData = String.format(FORMAT_3G, mean.get(LITERAL_RAW).getValue());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format(FORMAT_5F, mean.get(LITERAL_ADJUSTED).getValue());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printIndividualGenerationMeanDataTree(line, mean, totalRPB, "RPB");
    printIndividualGenerationMeanDataTree(line, mean, totalADF, LITERAL_ADF);
    getWriter().println(line.toString());
  }
  
  

  private void printIndividualGenerationMeanDataTree(StringBuilder line, final Map<String, Mean> mean, int totalTrees, String treeType) {
    for (int i = 0; i < totalTrees; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(mean.get(treeType + i + "-nodes").getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(mean.get(treeType + i + "-depth").getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
    }
  }

  /**
   * Imprime la cabecera de la tabla de individuos.
   */
  private void printIndividualHeaderTable() {
    // Print head table
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding;
    firstColumnWidth = WIDTH_COLUMN + CENTER_SEP.length();

    defineSecondColumnWidth();

    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(LITERAL_INDIVIDUAL), firstColumnWidth + secondColumnWidth), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(LITERAL_RAW), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(LITERAL_ADJUSTED), WIDTH_COLUMN).length() + EXTRA_PADDING;
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_FITNESS), padding), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(LITERAL_NODES), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(LITERAL_DEPTH), WIDTH_COLUMN).length() + EXTRA_PADDING;
    appendHeaders(line, padding, totalRPB, LITERAL_RBP);
    appendHeaders(line, padding, totalADF, LITERAL_ADF);
    getWriter().printf(FORMAT_S_N_S, line.toString(), hline);
  }

  /**
   * Imprime la cabecera de la subtabla de individuos.
   */
  private void printIndividualSubHeaderTable() {
    // Print sub-head table
    final StringBuilder line = new StringBuilder(LEFT_SEP);

    final int padding = Formatter.center(getLiterals().getString(LITERAL_INDIVIDUAL), WIDTH_COLUMN).length();
    line.append(String.format("%s   %s", Formatter.center(SINGLE_BLANK_SPACE, padding + secondColumnWidth), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_RAW), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_ADJUSTED), WIDTH_COLUMN), CENTER_SEP));
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_NODES), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_DEPTH), WIDTH_COLUMN), CENTER_SEP));
    }
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_NODES), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(LITERAL_DEPTH), WIDTH_COLUMN), CENTER_SEP));
    }
    getWriter().printf(FORMAT_S_N, line.toString());

  }

  /**
   * Imprime los datos del peor individuo.
   */
  private void printIndividualWorstData() {
    StringBuilder line;
    String ceilData;
    final Individual worst = (Individual) (historical.getCurrentDataCollector(EXPERIMENT_INDIVIDUAL_DATA).getPlugin("worst").getValue());

    final int padding = Formatter.center(getLiterals().getString(LITERAL_INDIVIDUAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format(FORMAT_S, Formatter.center(SINGLE_BLANK_SPACE, padding + CENTER_SEP.length())));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(LITERAL_WORST_RUN), secondColumnWidth), CENTER_SEP));

    ceilData = String.format(FORMAT_3G, worst.getRawFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format(FORMAT_5F, worst.getAdjustedFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printDataRPB(line, worst);
    printDataADF(line, worst);
    getWriter().printf(FORMAT_S_N_S, line.toString(), hline);

  }

  /**
   * Imprime la tabla de tiempos.
   */
  private void printTime() {
    printTimeHeaderTable();
    printTimeData();

  }

  /**
   * Imprime los tiempos de ejecución de individuos.
   */
  private void printTimeBreeding() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding = hline.length() - (firstColumnWidth + secondColumnWidth) - 2 * CENTER_SEP.length() - EXTRA_PADDING;
    padding = padding / 2;
    final Plugin breeding = historical.getCurrentDataCollector(DATACOLLECTOR_POPULATION_TIME_DATA).getPlugin(PLUGIN_NAME_BREEDING);
    String ceilData;

    line.append(String.format(FORMAT_S, Formatter.center(SINGLE_BLANK_SPACE, firstColumnWidth)));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(LITERAL_BREEDING), secondColumnWidth), CENTER_SEP));
    ceilData = String.format(FORMAT_S, breeding.getPlugin(PLUGIN_NAME_MEAN).getValue().toString());
    line.append(String.format(FORMAT_S_S, Formatter.center(ceilData, padding), CENTER_SEP));
    ceilData = String.format(FORMAT_S, breeding.getPlugin(PLUGIN_NAME_TOTAL).getValue().toString());
    line.append(String.format(FORMAT_S_S, Formatter.center(ceilData, padding), CENTER_SEP));
    getWriter().printf(FORMAT_S_N, line.toString());
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
    final Plugin evaluation = historical.getCurrentDataCollector(DATACOLLECTOR_POPULATION_TIME_DATA).getPlugin(PLUGIN_NAME_EVALUATION);
    String ceilData;

    line.append(String.format(FORMAT_S, Formatter.center(SINGLE_BLANK_SPACE, firstColumnWidth)));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(LITERAL_EVALUATION), secondColumnWidth), CENTER_SEP));
    ceilData = String.format(FORMAT_S, evaluation.getPlugin(PLUGIN_NAME_MEAN).getValue().toString());
    line.append(String.format(FORMAT_S_S, Formatter.center(ceilData, padding), CENTER_SEP));
    ceilData = String.format(FORMAT_S, evaluation.getPlugin(PLUGIN_NAME_TOTAL).getValue().toString());
    line.append(String.format(FORMAT_S_S, Formatter.center(ceilData, padding), CENTER_SEP));
    getWriter().printf(FORMAT_S_N_S, line.toString(), hline);
  }


  /**
   * Imprime la cabecera de la tabla de tiempos.
   */
  private void printTimeHeaderTable() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding;

    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString("time"), firstColumnWidth + secondColumnWidth), CENTER_SEP));

    padding = hline.length() - (firstColumnWidth + secondColumnWidth) - 2 * CENTER_SEP.length() - EXTRA_PADDING;
    padding = padding / 2;

    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString("populationMean"), padding), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString("totalPopulation"), padding), CENTER_SEP));

    getWriter().printf(FORMAT_S_N_S, line.toString(), hline);
  }
}