/**
 * 
 */
package progen.roles.standalone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import progen.context.ProGenContext;
import progen.experimenter.Experimenter;
import progen.experimenter.ExperimenterFactory;
import progen.kernel.error.Info;
import progen.kernel.evolution.Elitism;
import progen.kernel.evolution.GenneticFactory;
import progen.kernel.evolution.GenneticOperator;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;
import progen.output.HistoricalData;
import progen.roles.Client;
import progen.roles.Dispatcher;
import progen.roles.ProGenFactory;
import progen.roles.Task;
import progen.userprogram.UserProgram;

/**
 * Implementación de un cliente que se conecta con un Dispatcher alojado en la
 * misma máquina virtual.
 * 
 * @author jirsis
 * 
 */
public class ClientLocal implements Client {

  /** Factoría de roles. */
  private ProGenFactory factory;

  /** Población del problema */
  private Population population;

  /** Factoría de operadores genéticos. */
  private GenneticFactory genneticFactory;

  /** Almacén de datos históricos. */
  private HistoricalData historical;

  /**
   * Constructor genérico de la clase.
   */
  public ClientLocal() {
    ProGenContext.loadExtraConfiguration();
    factory = ProGenFactory.makeInstance();
    genneticFactory = new GenneticFactory();
    historical = HistoricalData.makeInstance();
  }

  @Override
  public Dispatcher initDispatcher() {
    return factory.makeDispatcher();
  }

  @Override
  public void start() {
    Experimenter experimenter = ExperimenterFactory.makeInstance();
    Dispatcher dispatcher = this.initDispatcher();
    UserProgram userProgram = UserProgram.getUserProgram();
    int maxGenerations = ProGenContext.getOptionalProperty("progen.max-generation", Integer.MAX_VALUE);

    userProgram.initialize();

    while (!experimenter.isDone()) {
      experimenter.defineValues();

      executeExperimenter(experimenter, dispatcher, userProgram, maxGenerations);

      experimenter.updateValues();
      historical.newExperiment();
      historical.newGeneration();
      System.out.println(experimenter.finishMessage());
    }

    Individual best = population.getBestIndividual();
    String printable = userProgram.postProcess(best);
    best.setPrintableIndividual(printable);
    System.out.println(printable);

  }

  private void executeExperimenter(Experimenter experimenter, Dispatcher dispatcher, UserProgram userProgram, int maxGenerations) {
    List<Individual> newPopulation;
    // TODO: barra de progreso para la creacion de poblaciones?
    Info.show(1);
    population = new Population();
    for (int currentGeneration = 0; currentGeneration < maxGenerations; currentGeneration++) {

      dispatcher.asignTask(convertIndividualsToTasks(), userProgram);
      updateHistoricalData(dispatcher);

      newPopulation = updateNewPopulation();

      experimenter.generateOutputs();
      population.setPopulation(newPopulation);
      historical.newGeneration();
    }
  }

  private List<Individual> updateNewPopulation() {
    List<Individual> newPopulation;
    GenneticOperator operator;
    Calendar before;
    Calendar after;

    newPopulation = applyElitism();

    while (newPopulation.size() <= population.getIndividuals().size()) {
      operator = genneticFactory.getOperator();
      before = GregorianCalendar.getInstance();
      newPopulation.addAll(operator.evolve(population));
      after = GregorianCalendar.getInstance();
      historical.getCurrentDataCollector("PopulationTimeData").addValue("breeding", after.getTimeInMillis() - before.getTimeInMillis());
    }

    eraseExcedentIndividuals(newPopulation);
    return newPopulation;
  }

  private List<Individual> applyElitism() {
    List<Individual> newPopulation;
    Elitism elitism = new Elitism(population);
    newPopulation = elitism.propagate();
    return newPopulation;
  }

  private void eraseExcedentIndividuals(List<Individual> newPopulation) {
    if (newPopulation.size() > population.getIndividuals().size()) {
      while (newPopulation.size() != population.getIndividuals().size()) {
        newPopulation.remove(newPopulation.size() - 1);
      }
    }
  }

  private List<Task> convertIndividualsToTasks() {
    List<Task> individuals = new ArrayList<Task>();
    for (Individual ind : population.getIndividuals()) {
      individuals.add((Task) ind);
    }
    return individuals;
  }

  private void updateHistoricalData(Dispatcher dispatcher) {
    for (Task task : dispatcher.getResults()) {
      Individual individual = (Individual) task;
      historical.getDataCollectorExperiment("ExperimentIndividualData").addValue("statistical", individual);
      historical.getCurrentDataCollector("ExperimentIndividualData").addValue("statistical", individual);
      historical.getCurrentDataCollector("PopulationData").addValue("individualMean", individual);
    }
  }

}
