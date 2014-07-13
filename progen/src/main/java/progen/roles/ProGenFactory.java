package progen.roles;

import java.util.Locale;

import progen.context.ProGenContext;

/**
 * Fábrica general de que genera una fábrica concreta en función de la
 * configuración definida en el fichero de configuración o una fábrica local, en
 * caso de que no se defina ninguna.
 * 
 * Esta fábrica general implementa a su vez un patrón singleton para que
 * únicamente exista una instancia en toda la ejecución de la aplicación.
 * 
 * @author jirsis
 * @since 2.0
 */
public abstract class ProGenFactory {
  /** Representación única de la fábrica a lo largo de la ejecución de ProGen */
  private static ProGenFactory factory;

  /**
   * Método de la fábrica que devuelve un Client en función del tipo concreto de
   * fábrica.
   * 
   * @return Una intancia de Cliente concreto.
   */
  public abstract Client makeClient();

  /**
   * Método de la fábrica que devuelve un Dispatcher en función del tipo
   * concreto de fábrica.
   * 
   * @return Una intancia de Dispatcher concreto.
   */
  public abstract Dispatcher makeDispatcher();

  /**
   * Método de la fábrica que devuelve un Worker en función del tipo concreto de
   * fábrica.
   * 
   * @return Una intancia de Worker concreto.
   */
  public abstract Worker makeWorker();

  /**
   * Método que devuelve el rol de ejecución que está definido en el fichero de
   * configuración, o un ClienteLocal en caso de no definir nada.
   * 
   * @return El rol de ProGen en función de la configuración de fichero.
   */
  public abstract ExecutionRole makeExecutionRole();

  /**
   * Método para obtener la única instancia que existe a lo largo de la
   * ejecución de ProGen.
   * 
   * @return La instancia ya creada o una nueva, la primera vez que se llame al
   *         método.
   */
  public static synchronized ProGenFactory makeInstance() {
    if (factory == null) {
      String factoryClass = getFactoryClass();

      try {
        factory = (ProGenFactory) Class.forName(factoryClass).newInstance();
      } catch (InstantiationException e) {
        throw new FactoryNotFoundException(factoryClass);
      } catch (IllegalAccessException e) {
        throw new FactoryNotFoundException(factoryClass);
      } catch (ClassNotFoundException e) {
        throw new FactoryNotFoundException(factoryClass);
      }
    }

    return factory;
  }

  private static String getFactoryClass() {
    String factoryPackage = ProGenContext.getOptionalProperty("progen.roles.factory", "standalone");
    StringBuilder factoryClass = new StringBuilder(30);
    factoryClass.append("progen.roles.");
    factoryClass.append(factoryPackage + ".");
    factoryClass.append(factoryPackage.substring(0, 1).toUpperCase(Locale.getDefault()));
    factoryClass.append(factoryPackage.substring(1));
    factoryClass.append("Factory");
    return factoryClass.toString();
  }
}
