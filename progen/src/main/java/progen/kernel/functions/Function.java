package progen.kernel.functions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import progen.context.ProGenContext;
import progen.kernel.grammar.FunctionNotFoundException;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * Clase abstracta que define los métodos necesarios para acceder a las
 * distintas funciones que se definan en ProGen.
 * 
 * @author jirsis
 * @since 2.0
 */
public abstract class Function implements Comparable<Function>, Serializable {
  private static final long serialVersionUID = -7920842332819035400L;

  /** Indica la aridad de la función. */
  private int arity;
  /** Símbolo que identificará a la función. */
  private String symbol;
  /** Tipo de retorno que devolverá la función. */
  private Object returnType;
  /** Tipos que admite como parámetros la función. */
  private Object [] argsType;

  /**
   * Constructor por defecto de una función. Recibe la signatura de la misma y
   * el símbolo que la identifica.
   * 
   * El formato esperado en la signatura es
   * <code>valorDeRetorno$$arg1$$arg2$$...$$argN</code>
   * 
   * @param signature
   *          Signatura de la función.
   * @param symbol
   *          Símbolo que identificará la función.
   */
  public Function(String signature, String symbol) {
    String [] args;
    this.symbol = symbol;

    args = signature.split("\\$\\$");
    returnType = args[0];

    arity = args.length - 1;
    argsType = new Object[arity];
    for (int i = 0; i < arity; i++) {
      argsType[i] = args[i + 1];
    }
  }

  /**
   * Devuelve la signatura de la función
   * 
   * @return la signatura completa de la función.
   */
  public final String getSignature() {
    final StringBuffer signature = new StringBuffer();
    signature.append(returnType);
    for (int i = 0; i < argsType.length; i++) {
      signature.append("$$" + argsType[i]);
    }
    return signature.toString();
  }

  /**
   * Devuelve la aridad de la función.
   * 
   * @return la aridad de la función.
   */
  public final int getArity() {
    return arity;
  }

  /**
   * Devuelve el símbolo de la función.
   * 
   * @return el símbolo de la función.
   */
  public final String getSymbol() {
    return symbol;
  }

  /**
   * Establece el símbolo que identificará la función.
   * 
   * @param symbol
   *          El nuevo identificador de la función.
   */
  public final void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  /**
   * Devuelve el tipo de retorno de la función.
   * 
   * @return el tipo de retorno de la función.
   */
  public final Object getReturnType() {
    return returnType;
  }

  public String toString() {
    return symbol;
  }

  /**
   * Devuelve el tipo de los argumentos de la función.
   * 
   * @return el tipo de los argumentos de la función.
   */
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "EI_EXPOSE_REP", justification = "Its is a getter method")
  public Object[] getArgsType() {
    return argsType;
  }

  public boolean equals(Object o) {
    boolean equals = false;
    if (o instanceof Function) {
      equals = this.compareTo((Function) o) == 0;
    }
    return equals;
  }

  public int hashCode() {
    return symbol.hashCode();
  }

  public int compareTo(Function f) {
    return symbol.compareTo(f.getSymbol());
  }

  /**
   * Devuelve una instancia concreta a partir del nombre de la función que se
   * desea instanciar.
   * 
   * @param functionName
   *          el nombre de la función.
   * @return una instancia de la función solicitada.
   * @throws FunctionNotFoundException
   *           En caso de que no se encuentre la función en el paquete del
   *           usuario o en el paquete de funciones de la aplicación, en ese
   *           orden.
   * @throws UndefinedFunctionSetException
   *           En el caso de instanciar un ADF que no esté definido en el
   *           function-set correspondiente.
   */
  public static final Function load(String functionName){
    Function function;

    final String classPathProGen = "progen.kernel.functions.";
    final String classPathUser = ProGenContext.getOptionalProperty("progen.user.home", classPathProGen);

    if (!functionName.startsWith("ADF")) {
      function = loadReagularFunction(functionName, classPathProGen, classPathUser);
    } else {
      function = loadFunctionADF(functionName);
    }
    return function;
  }

  private static Function loadFunctionADF(String functionName) {
    return new ADF(functionName);
  }

  private static Function loadReagularFunction(String functionName, String classPathProGen, String classPathUser) {
    Function function;
    try {
      function = (Function) Class.forName(classPathUser + functionName).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      try {
        function = (Function) Class.forName(classPathProGen + functionName).newInstance();
      } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
        throw new FunctionNotFoundException(functionName);
      }
    }
    return function;
  }

  /**
   * Comprueba si una función es compatible con otra, es decir, si recibe los
   * mismos argumentos y tiene el mismo valor de retorno.
   * 
   * @param function
   *          La función para comprobar la compatibilidad.
   * @return <code>true</code> si es compatible y <code>false</code> si no lo
   *         es.
   */
  public boolean isCompatibleWith(Function function) {
    boolean isCompatible = true;
    // si las aridades son distintas, no pueden ser compatibles
    if (this.arity != function.arity) {
      isCompatible = false;
    } else {
      for (int i = 0; i < arity; i++) {
        isCompatible &= argsType[i].equals(function.argsType[i]);
      }
    }

    // comprobacion del valor de retorno
    isCompatible &= returnType.equals(function.returnType);

    return isCompatible;
  }

  /**
   * Forma de calcular el valor concreto de la ejecución de una función.
   * 
   * @param arguments
   *          conjunto de argumentos que se definieron a la hora de crear la
   *          función.
   * @param userProgram
   *          referencia al dominio creado por el usuario por si necesitara
   *          utilizar algún método declarado él.
   * @param returnAddr
   *          Valor de retorno de las llamadas a ADFs
   * @return El valor de evaluar una función concreta
   */
  public abstract Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr);

}
