package progen.kernel.evolution;

import java.util.ArrayList;
import java.util.List;

import progen.context.ProGenContext;
import progen.kernel.error.Warning;

/**
 * Fábrica encargada de cargar todos los operadores genéticos disponibles para
 * cada ejecución. También es la encargada de proporcionarlos, una vez cargados,
 * para que se puedan utilizar en las distintas etapas de la evolución del
 * programa.
 * 
 * @author jirsis
 * 
 */
public class GenneticFactory {
  private static final String PROBABILITY_PROPERTY = ".probability";
  private static final String PROGEN_GENNETIC_OPERATOR_PROPERTY = "progen.gennetic.operator";
  private static final String PROGEN_TOTAL_OPERATORS_PROPERTY = "progen.total.operators";
  /** Lista en la que se almacenan todos los operadores. */
  private List<GenneticOperator> operators;

  /**
   * Constructor de la clase, encargado de instanciar todos los operadores
   * genéticos disponibles para la ejecución de este experimento.
   */
  public GenneticFactory() {
    operators = new ArrayList<GenneticOperator>();
    checkProbability();
    loadOperators();
  }

  /**
   * Comprueba, si la suma de probabilidades de todos los operadores de ese tipo
   * suman 100%.
   * 
   * @return <code>true</code> si la suma es 100%
   */
  private boolean checkProbability() {
    final int totalOperators = Integer.parseInt(ProGenContext.getMandatoryProperty(PROGEN_TOTAL_OPERATORS_PROPERTY));
    double probability = 0.0;
    for (int i = 0; i < totalOperators; i++) {
      probability += ProGenContext.getOptionalPercent(PROGEN_GENNETIC_OPERATOR_PROPERTY + i + PROBABILITY_PROPERTY, String.valueOf(1 - probability));
    }

    if (probability != 1.0) {
      Warning.show(2);
      throw new BadConfigurationGenneticOperatorsException(probability);
    }
    return true;
  }

  /**
   * Especifica e instancia el conjunto total de operadores genéticos
   * disponibles en la ejecución.
   */
  private void loadOperators() {
    final int totalOperators = Integer.parseInt(ProGenContext.getMandatoryProperty(PROGEN_TOTAL_OPERATORS_PROPERTY));
    GenneticOperator operator;
    String selectorPropertyContext;
    double probability = 0.0;

    for (int idOperator = 0; idOperator < totalOperators; idOperator++) {
      selectorPropertyContext = PROGEN_GENNETIC_OPERATOR_PROPERTY + idOperator + ".selector";
      try {
        probability = ProGenContext.getOptionalPercent(PROGEN_GENNETIC_OPERATOR_PROPERTY + idOperator + PROBABILITY_PROPERTY, String.valueOf(1 - probability));
        operator = configureOperator(idOperator, selectorPropertyContext, probability);
        operators.add(operator);
      } catch (ClassNotFoundException e) {
        throw new UndefinedGenneticOperatorException(e.getMessage());
      } catch (InstantiationException | IllegalAccessException e) {
        throw new GenneticOperatorException(e.getMessage());
      }
    }
  }

  private GenneticOperator configureOperator(int idOperator, String selectorPropertyContext, double probability) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    GenneticOperator operator;
    operator = makeOperatorInstance(idOperator);
    operator.setProbability(probability);
    operator.setSelector(ProGenContext.getMandatoryProperty(selectorPropertyContext), ProGenContext.getParameters(selectorPropertyContext));
    return operator;
  }

  private GenneticOperator makeOperatorInstance(int idOperator) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    GenneticOperator operator;
    String operatorClass;
    operatorClass = ProGenContext.getMandatoryProperty(PROGEN_GENNETIC_OPERATOR_PROPERTY + idOperator + ".class");
    operator = (GenneticOperator) Class.forName("progen.kernel.evolution.operators." + operatorClass).newInstance();
    return operator;
  }

  /**
   * Devuelve un operador genético de la lista de los posibles.
   * 
   * @return El operador a ejecutar.
   */
  public GenneticOperator getOperator() {
    final double probability = Math.random();
    double actualProb = 0.0;
    int idOperator = 0;
    while (actualProb <= probability) {
      actualProb += operators.get(idOperator++).getProbability();
    }
    return operators.get(--idOperator);
  }
}
