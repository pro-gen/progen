package progen.context;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Clase que unifica la recuperación de las distintas propiedades definidas en
 * los ficheros de configuración.
 * <p/>
 * Provee de métodos para recuperar propiedades obligatorias, optativas,
 * porcentajes, etc.
 * 
 * @author jirsis
 */
public class ProGenContext {
  /**
   * Cadena que se utiliza para subopciones en distintos parámetros.
   */
  private final static String parametersDelim = "\\(";
  /**
   * Almacénd de propiedades que es implementado por un Singleton de tal forma
   * que únicamente exista una instancia en toda la ejecución.
   */
  private static ProGenContext proGenProps;
  /**
   * Estructura de datos que almacenta todas las propiedades disponibles en el
   * dominio del problema
   */
  private Properties properties;

  /**
   * Constructor privado del Singleton
   */
  private ProGenContext() {
    properties = new Properties();
  }

  /**
   * Define la única forma que existe de instanciar e inicializar este
   * componente, a partir del fichero <i>master</i> del dominio de la
   * aplicación.
   * 
   * @param file
   *          Fichero maestro de donde se cargarán los distintos valores de
   *          configuración.
   * @return La instancia desde la que se podrán recuperar las opciones
   *         definidas.
   * @throws MissingContextFileException
   *           En caso de que no se encuentre el fichero maestro de
   *           configuración. múltiples veces.
   * @throws IOException
   */
  public static ProGenContext makeInstance(String file) throws MissingContextFileException {

    if (file == null) {
      throw new MissingContextFileException();
    } else {

      try {
        InputStream fis = getResource(file);

        proGenProps = new ProGenContext();
        proGenProps.loadOtherPropertiesFile("ProGen.conf");

        proGenProps.properties.load(fis);
        ProGenContext.setProperty("progen.masterfile", new File(file).getAbsolutePath());
        fis.close();
      } catch (NullPointerException e) {
        throw new MissingContextFileException(e.getMessage());
      } catch (IOException e) {
        throw new MissingContextFileException(e.getMessage());
      }
    }
    return proGenProps;
  }

  private static InputStream getResource(String file) {
    return ProGenContext.class.getClassLoader().getResourceAsStream(file);
  }

  /**
   * Devuelve la única instancia que existe de las propiedades. En caso de no
   * existir, crea las propiedades vacías y lo devuelve tal cual.
   * 
   * @return La referencia a la única instancia de propiedades de ProGen
   */
  public static ProGenContext makeInstance() {
    if (proGenProps == null) {
      proGenProps = new ProGenContext();
    }
    return proGenProps;
  }

  /**
   * Forma de obtener el valor de la propiedad identificada por parámetro. Al
   * tener caracter opcional, en caso de no estar definida, se devolverá el
   * valor pasado como segundo parámetro.
   * 
   * @param property
   *          identificador de la propiedad.
   * @param defaultValue
   *          valor por defecto
   * @return valor de la propiedad solicitada recuperada desde los ficheros de
   *         configuración o valor por defecto en caso de no estar definida.
   */
  public static int getOptionalProperty(String property, int defaultValue) {
    if (ProGenContext.getProperty(property) == null) {
      return defaultValue;
    } else {
      return Integer.parseInt(ProGenContext.getProperty(property).split(parametersDelim)[0]);
    }
  }

  /**
   * Forma de obtener el valor de la propiedad identificada por parámetro. Al
   * tener caracter opcional, en caso de no estar definida, se devolverá el
   * valor pasado como segundo parámetro.
   * 
   * @param property
   *          identificador de la propiedad.
   * @param defaultValue
   *          valor por defecto
   * @return valor de la propiedad solicitada recuperada desde los ficheros de
   *         configuración o valor por defecto en caso de no estar definida.
   */
  public static String getOptionalProperty(String property, String defaultValue) {
    String value;
    if (ProGenContext.getProperty(property) == null) {
      value = defaultValue;
    } else {
      value = ProGenContext.getProperty(property).split(parametersDelim)[0];
    }
    return value;
  }

  /**
   * Forma de obtener el valor de la propiedad identificada por parámetro. Al
   * tener caracter opcional, en caso de no estar definida, se devolverá el
   * valor pasado como segundo parámetro.
   * 
   * @param property
   *          identificador de la propiedad.
   * @param defaultValue
   *          valor por defecto
   * @return valor de la propiedad solicitada recuperada desde los ficheros de
   *         configuración o valor por defecto en caso de no estar definida.
   */
  public static double getOptionalProperty(String property, double defaultValue) {
    if (ProGenContext.getProperty(property) == null) {
      return defaultValue;
    } else {
      return Double.parseDouble(ProGenContext.getProperty(property).split(parametersDelim)[0]);
    }
  }

  /**
   * Forma de obtener el valor de la propiedad identificada por parámetro. Al
   * tener caracter opcional, en caso de no estar definida, se devolverá el
   * valor pasado como segundo parámetro.
   * 
   * @param property
   *          identificador de la propiedad.
   * @param defaultValue
   *          valor por defecto
   * @return valor de la propiedad solicitada recuperada desde los ficheros de
   *         configuración o valor por defecto en caso de no estar definida.
   */
  public static boolean getOptionalProperty(String property, boolean defaultValue) {
    if (ProGenContext.getProperty(property) == null) {
      return defaultValue;
    } else {
      return Boolean.parseBoolean(ProGenContext.getProperty(property).split(parametersDelim)[0]);
    }
  }

  /**
   * Forma de obtener el valor de una propiedad definida en los ficheros de
   * configuración, identificada por el parámetro definido. Será responsabilidad
   * de la parte que realiza esta llamada, comprobar si el contenido de esta
   * propiedad es correcto y acorde a lo que se espera.
   * 
   * @param key
   *          Identificador de la propiedad a recuperar
   * @return <code>String</code> con el valor de la propiedad tal y como aparece
   *         en el fichero de configuración.
   */
  public static String getMandatoryProperty(String key) {
    String property = getProperty(key);
    if (property == null)
      throw new UndefinedMandatoryPropertyException(key);
    return property.split(parametersDelim)[0];
  }

  /**
   * Metodo de acceso para recuperar cualquier propiedad definida, identificada
   * por el parámetro
   * 
   * @param key
   *          Identificador de la propiedad a recuperar
   * @return valor de la propiedad recuperada
   * @see #makeInstance(String)
   */
  private static String getProperty(String key) {
    if (proGenProps == null) {
      throw new UninitializedContextException();
    }
    return proGenProps.properties.getProperty(key);
  }

  /**
   * Devuelve el valor del porcentaje de uso de una subopción definida en el
   * contexto del dominio. Para ello, es necesario definir la subopción
   * separándola de la opción con dos puntos (:) y el porcentaje definido como
   * tanto por ciento o como un valor entre 0 y 1. A su vez, las distintas
   * subopciones, tienen que ir separadas por comas (,).
   * 
   * @param key
   *          La opción a recuperar.
   * @param subOption
   *          La subopción a recuperar dentro de la opción.
   * @param defaultPercent
   *          El porcentaje por defecto, en caso de que no exista esta
   *          subopción.
   * @return Devuelve un valor entre 0 y 1, que representa dicho porcentaje.
   */
  public static double getSuboptionPercent(String key, String subOption, double defaultPercent) {
    double value = defaultPercent;
    String suboption = getParameter(key, subOption);

    if (suboption != null) {
      value = getPercent(suboption);
    }

    return value;
  }

  /**
   * Recupera un porcentaje definido en una opción, de tal forma que esta opción
   * no es obligatoria que se defina.
   * 
   * @param key
   *          La propiedad a recuperar.
   * @param defaultPercent
   *          El valor por defecto en caso de no haberse definido la propiedad.
   * @return Un valor entre 0 y 1, que representa dicho porcentaje.
   */
  public static double getOptionalPercent(String key, String defaultPercent) {
    defaultPercent = getOptionalProperty(key, defaultPercent);
    return getPercent(defaultPercent);
  }

  /**
   * Recupera un porcentaje definido en una opción de forma obligatoria.
   * 
   * @param key
   *          La propiedad a recuperar.
   * @return Un valor entre 0 y 1, que representa dicho porcentaje.
   */
  public static double getMandatoryPercent(String key) {
    String property = getProperty(key);
    if (property == null)
      throw new UndefinedMandatoryPropertyException(key);
    return getPercent(property);
  }

  /**
   * Recupera una colección con todas las opciones que coinciden comparten una
   * parte del nombre, definido en el parámetro.
   * 
   * @param family
   *          La parte común del nombre de la propiedad.
   * @return Una colección con todas las propiedades que cumplen con el
   *         criterio.
   */
  public static List<String> getFamilyOptions(String family) {
    List<String> options = new ArrayList<String>();
    Iterator<Object> i = proGenProps.properties.keySet().iterator();
    String option;
    while (i.hasNext()) {
      option = (String) i.next();
      if (option.indexOf(family) == 0) {
        options.add(option);
      }
    }
    return options;
  }

  /**
   * Recupera el valor numérico de un tanto por ciento, representado en una
   * cadena como valor entre 0 y 1 o con un valor entre 0 y 100 y acabado con el
   * símbolo por ciento (%).
   * 
   * @param percent
   *          La cadena a convertir en valor numérico.
   * @return Un número comprendido entre 0 y 1.
   */
  private static double getPercent(String percent) {
    double value = 0.0;
    percent = percent.replaceAll(" ", "");
    if (percent.endsWith("%")) {
      value = Double.parseDouble(percent.substring(0, percent.length() - 1)) / 100;
    } else {
      value = Double.parseDouble(percent);
    }
    return value;
  }

  /**
   * Añade una propiedad al conjunto de propiedades definidas en ProGen.
   * 
   * @param key
   *          Identificador de la propiedad.
   * @param value
   *          Valor concreto que tomará la propiedad.
   */
  public static void setProperty(String key, String value) {
    proGenProps.properties.setProperty(key, value);
    proGenProps.calculateProperties();
  }

  /**
   * Elimina todas las propiedades que estuvieran definidas en el contexto.
   */
  public static void clearContext() {
    proGenProps = null;
  }

  /**
   * Devuelve el valor concreto de un parámetro de configuración de una opción.
   * En caso de que no exista la opción o la subopción de la que se quiere
   * obtener el valor, se devolverá el valor null; y en caso de que haya algún
   * parámetro no definido según la forma indicada a continuación, se lanzará
   * una {@link MalformedParameterException} indicando la pareja
   * opción-subopción que se no cumple con el formato. El formato específico
   * para definir los parámetros es: <code>
   * progen.opcion=valor(subopcion1=valor1, subopcion2=valor2, ...)<br/>
   * </code>
   * 
   * @param option
   *          Opción de la que se obtendrá un parámetro.
   * @param parameter
   *          Parámetro de la que se quiere obtener un valor concreto.
   * @return El valor del parámetro o null en caso de no encontrarse.
   */
  public static String getParameter(String option, String parameter) {
    String value = null;
    value = getParameters(option).get(parameter);
    return value;
  }

  /**
   * Devuelve todos los parámetros asociados a una opción determinada. En caso
   * de no estar definida la opción o que no tenga parámetros extra, se
   * devolverá un Map vacío, es decir, de tamaño 0.
   * 
   * @param option
   *          La opción para recuperar los parámetros.
   * @return La colección de parámetros asociados a dicha opción.
   */
  public static Map<String, String> getParameters(String option) {
    Map<String, String> parameters = new HashMap<String, String>();
    String parametersContext;
    parametersContext = getProperty(option);

    // Se controla que exista la opción y tenga al menos un parámetro.
    if (parametersContext != null && parametersContext.split(parametersDelim).length > 1) {
      parametersContext = parametersContext.split(parametersDelim)[1];
      // se recupera el valor y se elimina el el posible ) del final
      parametersContext = parametersContext.replace(")", "");
      String parameterKey = null, parameterValue = null;
      try {
        // se separan todos los parámetros
        String[] parametersValues = parametersContext.split(",");
        for (String parameter : parametersValues) {
          parameterKey = parameter.split("=")[0].trim();
          parameterValue = parameter.split("=")[1].trim();
          parameters.put(parameterKey, parameterValue);
        }
      } catch (Exception e) {
        throw new MalformedParameterException(option + "," + parameterKey);
      }
    }
    return parameters;
  }

  public static void loadExtraConfiguration() {
    try {
      proGenProps.calculateProperties();
      proGenProps.loadOtherProperties();
    } catch (java.io.FileNotFoundException fnfe) {
      throw new MissingContextFileException(fnfe.getMessage());
    } catch (IOException ioe) {
      throw new MissingContextFileException(ioe.getMessage());
    }

  }

  /**
   * Carga las opciones definidas en otros ficheros a parte del fichero maestro.
   * Carga también las definidas en el fichero especificado en la propiedad
   * <code>progen.experiment-file</code> en el fichero maestro y las que están
   * definidas en los ficheros del dominio de especificación de experimentos (
   * <code>-experiment</code>) y hypergp (<code>-hypergp</code> ).
   * 
   * @throws FileNotFoundException
   *           Si el fichero del dominio no existe.
   * @throws IOException
   *           Si ocurre un error de E/S.
   */
  private void loadOtherProperties() throws FileNotFoundException, IOException {
    loadOtherProperties("progen.experiment.file");
    loadOptionalFile("-experimenter");
    loadOptionalFile("-hypergp");

  }

  /**
   * Carga directamente un fichero de propiedades al conjunto de las que ya
   * estén definidas en ProGen. Primero buscará en el directorio raíz del
   * proyecto y en caso de no encontrarlo, buscará en el directorio
   * <code>home</code> del experimento.
   * 
   * @param propertyFile
   *          El nombre del fichero de propiedades.
   * @throws IOException
   */
  private void loadOtherPropertiesFile(String propertyFile) throws IOException {
    Properties otherProperties;
    Enumeration<Object> keys;
    String key;
    String value;
    if (propertyFile != null) {
      InputStream fis;
      otherProperties = new Properties();
      // look for in absolute path
      try {
        fis = getResource(propertyFile);
        otherProperties.load(fis);
        fis.close();
      } catch (FileNotFoundException fnfe) {
        // look for in user project

        propertyFile = proGenProps.properties.getProperty("progen.user.home").replace('.', File.separatorChar) + propertyFile;
        fis = getResource(propertyFile);
        otherProperties.load(fis);
        fis.close();
      }
      keys = otherProperties.keys();
      while (keys.hasMoreElements()) {
        key = (String) keys.nextElement();

        if (proGenProps.properties.containsKey(key)) {
          throw new DuplicatedPropertyException(key);
        } else {
          value = otherProperties.getProperty(key);
          proGenProps.properties.put(key, value);
        }
      }
    }
  }

  /**
   * Carga algún fichero de configuración especéfica del dominio del problema.
   * 
   * @param propertyFile
   *          el fichero a cargar.
   * @throws FileNotFoundException
   *           Si no existe el fichero.
   * @throws IOException
   *           Si ocurre un error de E/S.
   */
  @SuppressWarnings("static-access")
  private void loadOtherProperties(String propertyFile) throws FileNotFoundException, IOException {
    Properties otherProperties;
    Enumeration<Object> keys;
    String key;
    String value;
    String otherFile = proGenProps.properties.getProperty(propertyFile);
    int lastDot;
    String path, ext;

    if (otherFile != null) {
      lastDot = otherFile.lastIndexOf(".");
      path = otherFile.substring(0, lastDot);
      ext = otherFile.substring(lastDot);
      otherFile = path.replace('.', File.separatorChar) + ext;

      otherProperties = new Properties();
      // look for in absolute path
      try {
        InputStream fis = getResource(otherFile);
        otherProperties.load(fis);
        fis.close();
      } catch (FileNotFoundException fnfe) {
        // look for in user project
        otherFile = proGenProps.getProperty("progen.user.home").replace('.', File.separatorChar) + proGenProps.properties.getProperty(propertyFile);
        FileInputStream fis = new FileInputStream(otherFile);
        otherProperties.load(fis);
        fis.close();
      }
      keys = otherProperties.keys();
      while (keys.hasMoreElements()) {
        key = (String) keys.nextElement();

        if (proGenProps.properties.containsKey(key)) {
          throw new DuplicatedPropertyException(key);
        } else {
          value = otherProperties.getProperty(key);
          proGenProps.properties.put(key, value);
        }
      }
    }
  }

  /**
   * Método que define algunas propiedades a partir de otras ya existentes.
   * <p/>
   * Por ahora se definen:
   * <ul>
   * <li>
   * <code>progen.user.home</code>: ruta donde se encuentran los elementos que
   * definen el dominio concreto sobre el que se está trabajando.</li>
   * </ul>
   */
  @SuppressWarnings("static-access")
  private void calculateProperties() {
    String userHome = proGenProps.getProperty("progen.experiment.file");
    String experimentName;
    if (userHome != null) {
      // delete .txt
      userHome = userHome.substring(0, userHome.lastIndexOf("."));
      // get the experiment name
      experimentName = userHome.substring(userHome.lastIndexOf(".") + 1, userHome.length());

      // delete experiment File
      userHome = userHome.substring(0, userHome.lastIndexOf(".") + 1);

      proGenProps.properties.setProperty("progen.user.home", userHome);
      proGenProps.properties.setProperty("progen.experiment.name", experimentName);
    }
  }

  /**
   * Carga las propiedades definidas en el fichero que tiene por nombre el del
   * dominio del problema más un sufijo.
   * 
   * @param sufixFile
   *          El sufijo que se le añade al nombre del dominio para formar el
   *          fichero de propiedades.
   */
  private void loadOptionalFile(String sufixFile) {
    Properties otherProperties;
    Enumeration<Object> keys;
    String key;
    String value;
    StringBuilder optionalFilesLoaded = new StringBuilder();

    String optionalFile = ProGenContext.getMandatoryProperty("progen.experiment.file");
    optionalFile = optionalFile.replaceAll("\\.txt$", sufixFile).replace('.', File.separatorChar) + ".txt";
    optionalFilesLoaded.append(ProGenContext.getOptionalProperty("progen.optional.files", ""));

    try {
      FileInputStream fis = new FileInputStream(optionalFile);
      otherProperties = new Properties();
      otherProperties.load(fis);
      keys = otherProperties.keys();
      while (keys.hasMoreElements()) {
        key = (String) keys.nextElement();
        value = otherProperties.getProperty(key);
        proGenProps.properties.put(key, value);
      }
      fis.close();
      optionalFilesLoaded.append(optionalFile);
      optionalFilesLoaded.append(", ");
      ProGenContext.setProperty("progen.optional.files", optionalFilesLoaded.toString());
    } catch (FileNotFoundException e) {
      // do nothing, ignore
    } catch (IOException e) {
      // do nothing, ignore
    }

  }

}
