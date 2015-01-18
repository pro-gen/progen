package progen.output.outputers;

import java.util.Map;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.output.HistoricalData;
import progen.output.datacollectors.DataCollector;
import progen.output.plugins.Mean;
import progen.output.plugins.Plugin;

public class StandardConsole extends ConsoleOutput {

  private static final String DEPTH_SUFFIX = "-depth";
  private static final String NODES_SUFFIX = "-nodes";
  private static final String FORMAT_3G = "%.3G";
  private static final String FORMAT_N = "%n";
  private static final String FORMAT_S_D_N = "%s%d:%n";
  private static final String FORMAT_5F = "%.5f";
  private static final String FORMAT_3F = "%.3f";
  private static final String FORMAT_S_N_S = "%s%n%s";
  private static final String FORMAT_S_S = "%s%s";
  private static final int EXTRA_PADDING = 3;
  private static final String WORST_RUN_LITERAL = "worstRun";
  private static final String BEST_RUN_LITERAL = "bestRun";
  private static final String GENERATION_MEAN_LITERAL = "generationMean";
  private static final String INDIVIDUAL_MEAN_LITERAL = "individualMean";
  private static final String POPULATION_DATA_LITERAL = "PopulationData";
  private static final String BREEDING_LITERAL = "breeding";
  private static final String TOTAL_LITERAL = "total";
  private static final String MEAN_LITERAL = "mean";
  private static final String EVALUATION_LITERAL = "evaluation";
  private static final String POPULATION_TIME_DATA_LITERAL = "PopulationTimeData";
  private static final String RPB_LITERAL = "RPB";
  private static final String INDIVIDUAL_LITERAL = "individual";
  private static final String ADF_LITERAL = "ADF";
  private static final String RPB_LITEARL = RPB_LITERAL;
  private static final String DEPTH_LITERAL = "depth";
  private static final String NODES_LITERAL = "nodes";
  private static final String FITNESS_LITERAL = "fitness";
  private static final String ADJUSTED_LITERAL = "adjusted";
  private static final String RAW_LITERAL = "raw";
  private static final String BEST_LITERAL = "best";
  private static final String STRING_FORMAT = "%s";
  private static final String STRING_NEW_LINE_FORMAT = "%s%n";
  private static final String SPACE_SYMBOL = " ";
  private static final String EXPERIMENT_INDIVIDUAL_DATA_LITERAL = "ExperimentIndividualData";
  private static final String CENTER_SEP = " | ";
  private static final String LEFT_SEP = CENTER_SEP.substring(1, 3);
  private static final String RIGHT_SEP = CENTER_SEP.substring(1);
  private static final int WIDTH_COLUMN = 10;

  private int firstColumnWidth;
  private int secondColumnWidth;

  private HistoricalData historical;

  private DataCollector experimentData;

  private String hline;

  private int totalRPB;
  private int totalADF;

  public StandardConsole() {
    super();
    historical = HistoricalData.makeInstance();
    experimentData = historical.getDataCollectorExperiment(EXPERIMENT_INDIVIDUAL_DATA_LITERAL);
    
    totalRPB = Integer.parseInt(ProGenContext.getMandatoryProperty("progen.total.RPB"));
    totalADF = ProGenContext.getOptionalProperty("progen.total.ADF", 0);
    calculateHline();
  }

  private void calculateHline() {
    final int maxLineLength = getMaxLine().length();
    final StringBuilder lineOfDashes = new StringBuilder(SPACE_SYMBOL);
    for (int i = 2; i < maxLineLength - 1; i++) {
      lineOfDashes.append("-");
    }

    hline = String.format(STRING_NEW_LINE_FORMAT, lineOfDashes.toString());
    
  }

  @Override
  public void print() {
    final int currentGeneration = historical.getCurrentGeneration();
    final int maxGenerations = ProGenContext.getOptionalProperty("progen.max-generation", Integer.MAX_VALUE);
    if (currentGeneration < maxGenerations) {
      printHeader(maxGenerations);
      printBody();
      printTail();
    }
  }

  private void printHeader(int maxGenerations) {
    final String generation = getLiterals().getString("generation") + Formatter.right(historical.getCurrentGeneration(), maxGenerations);
    System.out.printf("%n%n%s", hline);
    System.out.printf("%s%s%s%n", LEFT_SEP, Formatter.center(generation, getMaxLine().length() - LEFT_SEP.length() - RIGHT_SEP.length()), RIGHT_SEP);

    System.out.printf(STRING_FORMAT, hline);
  }

  private void printBody() {
    final Individual bestTotal = (Individual) (historical.getDataCollectorExperiment(EXPERIMENT_INDIVIDUAL_DATA_LITERAL).getPlugin(BEST_LITERAL).getValue());
    final Individual bestGeneration = (Individual) (historical.getCurrentDataCollector(EXPERIMENT_INDIVIDUAL_DATA_LITERAL).getPlugin(BEST_LITERAL).getValue());

    printIndividual();
    printTime();
    if (bestTotal.equals(bestGeneration)) {
      printBest();
    }
  }

  private void printBest() {
    printBestHeaderTable();
    printBestSubHeaderTable();
    printBestData();
    printBestTail();
  }

  private void printBestHeaderTable() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding;

    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString("newBestIndividual"), firstColumnWidth + secondColumnWidth), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(RAW_LITERAL), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(ADJUSTED_LITERAL), WIDTH_COLUMN).length() + EXTRA_PADDING;
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(FITNESS_LITERAL), padding), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN).length() + EXTRA_PADDING;
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(RPB_LITEARL + i, padding), CENTER_SEP));
    }
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(ADF_LITERAL + i, padding), CENTER_SEP));
    }
    System.out.printf(FORMAT_S_N_S, line.toString(), hline);
  }

  private void printBestSubHeaderTable() {
    printIndividualSubHeaderTable();
  }

  private void printBestData() {
    StringBuilder line;
    String ceilData;
    final Individual best = (Individual) (experimentData.getPlugin(BEST_LITERAL).getValue());

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format(STRING_FORMAT, Formatter.center(SPACE_SYMBOL, padding + CENTER_SEP.length())));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(INDIVIDUAL_LITERAL), secondColumnWidth), CENTER_SEP));

    ceilData = String.format(FORMAT_3F, best.getRawFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format(FORMAT_5F, best.getAdjustedFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printRPBIndividualData(line, best);
    printADFIndividualData(line, best);
    System.out.printf(FORMAT_S_N_S, line.toString(), hline);
  }

  private void printADFIndividualData(StringBuilder line, final Individual individual) {
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(individual.getTrees().get(ADF_LITERAL + i).getRoot().getTotalNodes(), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(individual.getTrees().get(ADF_LITERAL + i).getRoot().getMaximunDepth(), WIDTH_COLUMN), CENTER_SEP));
    }
  }

  private void printRPBIndividualData(StringBuilder line, final Individual individual) {
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(individual.getTrees().get(RPB_LITERAL + i).getRoot().getTotalNodes(), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(individual.getTrees().get(RPB_LITERAL + i).getRoot().getMaximunDepth(), WIDTH_COLUMN), CENTER_SEP));
    }
  }

  private void printBestTail() {
    final StringBuilder bestTail = new StringBuilder();
    final Individual best = (Individual) (experimentData.getPlugin(BEST_LITERAL).getValue());
    for (int i = 0; i < best.getTotalRPB(); i++) {
      bestTail.append(String.format(FORMAT_S_D_N, RPB_LITERAL, i));
      bestTail.append(Formatter.tree(best.getTrees().get(RPB_LITERAL + i)));
      bestTail.append(String.format(FORMAT_N));
    }

    for (int i = 0; i < best.getTotalADF(); i++) {
      bestTail.append(String.format(FORMAT_S_D_N, ADF_LITERAL, i));
      bestTail.append(Formatter.tree(best.getTrees().get(ADF_LITERAL + i)));
      bestTail.append(String.format(FORMAT_N));
    }

    System.out.println(bestTail.toString());
  }

  private void printTime() {
    printTimeHeaderTable();
    printTimeData();

  }

  private void printTimeData() {
    printTimeBreeding();
    printTimeEvaluation();
  }

  private void printTimeEvaluation() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding = hline.length() - (firstColumnWidth + secondColumnWidth) - 2 * CENTER_SEP.length() - EXTRA_PADDING;
    padding = padding / 2;
    final Plugin evaluation = historical.getCurrentDataCollector(POPULATION_TIME_DATA_LITERAL).getPlugin(EVALUATION_LITERAL);
    String ceilData;

    line.append(String.format(STRING_FORMAT, Formatter.center(SPACE_SYMBOL, firstColumnWidth)));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(EVALUATION_LITERAL), secondColumnWidth), CENTER_SEP));
    ceilData = String.format(STRING_FORMAT, evaluation.getPlugin(MEAN_LITERAL).getValue().toString());
    line.append(String.format(FORMAT_S_S, Formatter.center(ceilData, padding), CENTER_SEP));
    ceilData = String.format(STRING_FORMAT, evaluation.getPlugin(TOTAL_LITERAL).getValue().toString());
    line.append(String.format(FORMAT_S_S, Formatter.center(ceilData, padding), CENTER_SEP));
    System.out.printf(FORMAT_S_N_S, line.toString(), hline);
  }

  private void printTimeBreeding() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding = hline.length() - (firstColumnWidth + secondColumnWidth) - 2 * CENTER_SEP.length() - EXTRA_PADDING;
    padding = padding / 2;
    final Plugin breeding = historical.getCurrentDataCollector(POPULATION_TIME_DATA_LITERAL).getPlugin(BREEDING_LITERAL);
    String ceilData;

    line.append(String.format(STRING_FORMAT, Formatter.center(SPACE_SYMBOL, firstColumnWidth)));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(BREEDING_LITERAL), secondColumnWidth), CENTER_SEP));
    ceilData = String.format(STRING_FORMAT, breeding.getPlugin(MEAN_LITERAL).getValue().toString());
    line.append(String.format(FORMAT_S_S, Formatter.center(ceilData, padding), CENTER_SEP));
    ceilData = String.format(STRING_FORMAT, breeding.getPlugin(TOTAL_LITERAL).getValue().toString());
    line.append(String.format(FORMAT_S_S, Formatter.center(ceilData, padding), CENTER_SEP));
    System.out.printf(STRING_NEW_LINE_FORMAT, line.toString());
  }

  private void printTimeHeaderTable() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding;

    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString("time"), firstColumnWidth + secondColumnWidth), CENTER_SEP));

    padding = hline.length() - (firstColumnWidth + secondColumnWidth) - 2 * CENTER_SEP.length() - EXTRA_PADDING;
    padding = padding / 2;

    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString("populationMean"), padding), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString("totalPopulation"), padding), CENTER_SEP));

    System.out.printf(FORMAT_S_N_S, line.toString(), hline);
  }

  private void printIndividual() {
    printIndividualHeaderTable();
    printIndividualSubHeaderTable();
    printIndividualData();
  }

  private void printIndividualData() {
    printIndividualBestRunData();
    printIndividualGenerationMeanData();
    printIndividualWorstData();
  }

  private void printIndividualGenerationMeanData() {
    StringBuilder line;
    String ceilData;
    final DataCollector populationData = historical.getCurrentDataCollector(POPULATION_DATA_LITERAL);
    final Map<String, Mean> mean = (Map<String, Mean>) (populationData.getPlugin(INDIVIDUAL_MEAN_LITERAL).getValue());

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format(STRING_FORMAT, Formatter.center(SPACE_SYMBOL, padding + CENTER_SEP.length())));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(GENERATION_MEAN_LITERAL), secondColumnWidth), CENTER_SEP));

    ceilData = String.format(FORMAT_3G, mean.get(RAW_LITERAL).getValue());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format(FORMAT_5F, mean.get(ADJUSTED_LITERAL).getValue());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printRPBIndividualGenerationMeanData(line, mean);
    printADFIndividualGenerationMeanData(line, mean);
    System.out.println(line.toString());
  }

  private void printADFIndividualGenerationMeanData(StringBuilder line, final Map<String, Mean> mean) {
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(mean.get(ADF_LITERAL + i + NODES_SUFFIX).getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(mean.get(ADF_LITERAL + i + DEPTH_SUFFIX).getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
    }
  }

  private void printRPBIndividualGenerationMeanData(StringBuilder line, final Map<String, Mean> mean) {
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(mean.get(RPB_LITERAL + i + NODES_SUFFIX).getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(mean.get(RPB_LITERAL + i + DEPTH_SUFFIX).getValue().toString(), WIDTH_COLUMN), CENTER_SEP));
    }
  }

  private void printIndividualBestRunData() {
    StringBuilder line;
    String ceilData;
    final Individual best = (Individual) (experimentData.getPlugin(BEST_LITERAL).getValue());

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format(STRING_FORMAT, Formatter.center(SPACE_SYMBOL, padding + CENTER_SEP.length())));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(BEST_RUN_LITERAL), secondColumnWidth), CENTER_SEP));

    ceilData = String.format(FORMAT_3F, best.getRawFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format(FORMAT_5F, best.getAdjustedFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printRPBIndividualData(line, best);
    printADFIndividualData(line, best);
    System.out.println(line.toString());

  }

  private void printIndividualWorstData() {
    StringBuilder line;
    String ceilData;
    final Individual worst = (Individual) (historical.getCurrentDataCollector(EXPERIMENT_INDIVIDUAL_DATA_LITERAL).getPlugin("worst").getValue());

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line = new StringBuilder(LEFT_SEP);
    line.append(String.format(STRING_FORMAT, Formatter.center(SPACE_SYMBOL, padding + CENTER_SEP.length())));
    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(WORST_RUN_LITERAL), secondColumnWidth), CENTER_SEP));

    ceilData = String.format(FORMAT_3G, worst.getRawFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));
    ceilData = String.format(FORMAT_5F, worst.getAdjustedFitness());
    line.append(String.format(FORMAT_S_S, Formatter.right(ceilData, WIDTH_COLUMN), CENTER_SEP));

    printRPBIndividualData(line, worst);
    printADFIndividualData(line, worst);
    System.out.printf(FORMAT_S_N_S, line.toString(), hline);

  }

  private void printIndividualSubHeaderTable() {
    // Print sub-head table
    final StringBuilder line = new StringBuilder(LEFT_SEP);

    final int padding = Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN).length();
    line.append(String.format("%s   %s", Formatter.center(SPACE_SYMBOL, padding + secondColumnWidth), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(RAW_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(ADJUSTED_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    }
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    }
    System.out.printf(STRING_NEW_LINE_FORMAT, line.toString());

  }

  private void printIndividualHeaderTable() {
    // Print head table
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    int padding;
    firstColumnWidth = WIDTH_COLUMN + CENTER_SEP.length();

    calculateSecondColumnWidth();

    line.append(String.format(FORMAT_S_S, Formatter.left(getLiterals().getString(INDIVIDUAL_LITERAL), firstColumnWidth + secondColumnWidth), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(RAW_LITERAL), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(ADJUSTED_LITERAL), WIDTH_COLUMN).length() + EXTRA_PADDING;
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(FITNESS_LITERAL), padding), CENTER_SEP));

    padding = Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN).length();
    padding += Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN).length() + EXTRA_PADDING;
    for (int i = 0; i < totalRPB; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(RPB_LITEARL + i, padding), CENTER_SEP));
    }
    for (int i = 0; i < totalADF; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(ADF_LITERAL + i, padding), CENTER_SEP));
    }
    System.out.printf(FORMAT_S_N_S, line.toString(), hline);
  }

  private void calculateSecondColumnWidth() {
    secondColumnWidth = Math.max(WIDTH_COLUMN, getLiterals().getString(GENERATION_MEAN_LITERAL).length());
    secondColumnWidth = Math.max(secondColumnWidth, getLiterals().getString(BEST_RUN_LITERAL).length());
    secondColumnWidth = Math.max(secondColumnWidth, getLiterals().getString(WORST_RUN_LITERAL).length());
  }

  private void printTail() {
    // do nothing
  }

  private String getMaxLine() {
    final StringBuilder line = new StringBuilder(LEFT_SEP);
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(INDIVIDUAL_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(GENERATION_MEAN_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(RAW_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(ADJUSTED_LITERAL), WIDTH_COLUMN), CENTER_SEP));

    int totalTrees = 0;
    totalTrees += totalRPB;
    totalTrees += totalADF;

    for (int i = 0; i < totalTrees; i++) {
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(NODES_LITERAL), WIDTH_COLUMN), CENTER_SEP));
      line.append(String.format(FORMAT_S_S, Formatter.center(getLiterals().getString(DEPTH_LITERAL), WIDTH_COLUMN), CENTER_SEP));
    }
    return line.toString();

  }
}
