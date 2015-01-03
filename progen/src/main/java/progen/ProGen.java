package progen;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import progen.context.MissingContextFileException;
import progen.context.ProGenContext;
import progen.kernel.error.Error;
import progen.kernel.grammar.UndefinedFunctionSetException;
import progen.output.HistoricalData;
import progen.output.outputers.OutputStore;
import progen.roles.ExecutionRole;
import progen.roles.ProGenFactory;

/**
 * Clase principal del programa. Desde el método main, se inicializan todos los
 * Singleton existentes y se instancia el rol concreto de ejecución de la
 * aplicación.
 * 
 * @author jirsis
 * @since 2.0
 */
public class ProGen {
  
  private String[] args;

  public ProGen(final String [] args) {
    this.args = Arrays.copyOf(args, args.length);
  }
  
  public final void runProGen() {
    ProGenContext.makeInstance(args[0]);
    Error.makeInstance();
    final ProGenFactory progenFactory = ProGenFactory.makeInstance();
    final ExecutionRole progen = progenFactory.makeExecutionRole();
    HistoricalData.makeInstance();
    OutputStore.makeInstance();
    System.out.println(ProGenContext.getMandatoryProperty("progen.welcome"));
    progen.start();
  }
  
  /**
   * Método principal de la aplicación en la que se inicializan los elementos
   * estáticos de la aplicación y se ejecuta el rol que corresponde según esté
   * definido en el fichero de configuración pasado como parámetro.
   * 
   * @param args
   *          Fichero <code>master-file</code> en el que se define la
   *          configuración de la ejecución de ProGen
   * @see progen.roles
   */
  public static void main(final String[] args) {
    final Calendar begin = GregorianCalendar.getInstance();
    if (args.length != 1) {
      System.err.println(Error.get(0));
      throw new ProGenException(Error.get(0));
    } else {
      try {
        new ProGen(args).runProGen();
      } catch (MissingContextFileException e) {
        System.err.println(Error.get(2) + "(" + e.getMessage() + ")");
      } catch (UndefinedFunctionSetException e) {
        System.err.println(e.getMessage());
      } catch (NumberFormatException e) {
        System.err.println(e.getMessage());
      }
      final Calendar end = GregorianCalendar.getInstance();
      System.out.println("\nEXECUTION TIME: " + (end.getTimeInMillis() - begin.getTimeInMillis()) + " ms.");
    }
  }

}
