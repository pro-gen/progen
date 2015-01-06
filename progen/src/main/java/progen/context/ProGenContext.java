package progen.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
public final class ProGenContext {
  private static final int HUNDRED_PERCENT = 100;
  private static final String PROGEN_OPTIONAL_FILES_PROPERTY = "progen.optional.files";
  private static final String DOT_SYMBOL = ".";
  private static final String PROGEN_USER_HOME_PROPERTY = "progen.user.home";
  private static final String PROGEN_EXPERIMENT_FILE_PROPERTY = "progen.experiment.file";
  private static final String PROGEN_EXPERIMENT_ABSOLUTE_FILE_PROPERTY = "progen.experiment.file.absolute";
  private static final String EQUAL_SYMBOL = "=";
  private static final String COMA_SYMBOL = ",";
  private static final String PARAMETERS_DELIMITER = "\\(";
  
  private static ProGenContext proGenProps;
  
  private Properties properties;

  private ProGenContext() {
    properties = new Properties();
  }

  public static ProGenContext makeInstance(String masterFile) {
    if (masterFile == null) {
      throw new MissingContextFileException();
    } else {
      try {
        execMakeInstance(masterFile);
      } catch (NullPointerException e) {
        throw new MissingContextFileException(masterFile, e);
      } catch (IOException e) {
        throw new MissingContextFileException(e.getMessage(), e);
      }
    }
    return proGenProps;
  }
  
  @Override
  public String toString(){
    return properties.toString();
  }

  private static void execMakeInstance(String file) throws IOException {
    proGenProps = new ProGenContext();
    proGenProps.loadOtherPropertiesFile("ProGen.conf");
    final InputStream fis = getResourceInClassPath(file);
    proGenProps.properties.load(fis);
    proGenProps.properties.setProperty("progen.masterfile", ProGenContext.class.getClassLoader().getResource(file).getFile());
    fis.close();
  }

  private static InputStream getResourceInClassPath(String file) throws FileNotFoundException {
    return new FileInputStream(new File(ProGenContext.class.getClassLoader().getResource(file).getFile()));
  }
  
  private static InputStream getResource(String file) throws FileNotFoundException {
    return ProGenContext.class.getClassLoader().getResourceAsStream(file);
  }

  /**
   * Devuelve la única instancia que existe de las propiedades. En caso de no
   * existir, crea las propiedades vacías y lo devuelve tal cual.
   * 
   * @return La referencia a la única instancia de propiedades de ProGen
   */
  public static synchronized ProGenContext makeInstance() {
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
    int value;
    if (ProGenContext.getProperty(property) == null) {
      value = defaultValue;
    } else {
      value = Integer.parseInt(ProGenContext.getProperty(property).split(PARAMETERS_DELIMITER)[0]);
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
  public static String getOptionalProperty(String property, String defaultValue) {
    String value;
    if (ProGenContext.getProperty(property) == null) {
      value = defaultValue;
    } else {
      value = ProGenContext.getProperty(property).split(PARAMETERS_DELIMITER)[0];
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
    double value;
    if (ProGenContext.getProperty(property) == null) {
      value = defaultValue;
    } else {
      value = Double.parseDouble(ProGenContext.getProperty(property).split(PARAMETERS_DELIMITER)[0]);
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
  public static boolean getOptionalProperty(String property, boolean defaultValue) {
    boolean value;
    if (ProGenContext.getProperty(property) == null) {
      value = defaultValue;
    } else {
      value = Boolean.parseBoolean(ProGenContext.getProperty(property).split(PARAMETERS_DELIMITER)[0]);
    }
    return value;
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
    final String property = getProperty(key);
    if (property == null)
      throw new UndefinedMandatoryPropertyException(key);
    return property.split(PARAMETERS_DELIMITER)[0];
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
    final String suboption = getParameter(key, subOption);
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
    final String percent = getOptionalProperty(key, defaultPercent);
    return getPercent(percent);
  }

  /**
   * Recupera un porcentaje definido en una opción de forma obligatoria.
   * 
   * @param key
   *          La propiedad a recuperar.
   * @return Un valor entre 0 y 1, que representa dicho porcentaje.
   */
  public static double getMandatoryPercent(String key) {
    final String property = getProperty(key);
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
    final List<String> options = new ArrayList<String>();
    final Iterator<Object> propertyKey = proGenProps.properties.keySet().iterator();
    String option;
    while (propertyKey.hasNext()) {
      option = (String) propertyKey.next();
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
    final String percentNormalized = percent.replaceAll(" ", "");
    if (percentNormalized.endsWith("%")) {
      value = Double.parseDouble(percentNormalized.substring(0, percentNormalized.length() - 1)) / HUNDRED_PERCENT;
    } else {
      value = Double.parseDouble(percentNormalized);
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
    String parametersContext = getProperty(option);

    if (checkAtLeastOneParameter(parametersContext)) {
      parametersContext = normalizeParameters(parametersContext);
      parameters = splitParameters(option, parametersContext);
    }
    return parameters;
  }

  private static String normalizeParameters(String parametersContext) {
    final String parameterContextNormalized = parametersContext.split(PARAMETERS_DELIMITER)[1];
    return parameterContextNormalized.replace(")", "");
  }

  private static boolean checkAtLeastOneParameter(String parametersContext) {
    return parametersContext != null && parametersContext.split(PARAMETERS_DELIMITER).length > 1;
  }

  private static Map<String, String> splitParameters(String option, String parametersContext) {
    String parameterValue;
    String parameterKey = null;
    final Map<String, String> parameters = new HashMap<String, String>();
    try {
      for (String parameter : parametersContext.split(COMA_SYMBOL)) {
        parameterKey = parameter.split(EQUAL_SYMBOL)[0].trim();
        parameterValue = parameter.split(EQUAL_SYMBOL)[1].trim();
        parameters.put(parameterKey, parameterValue);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new MalformedParameterException(option + COMA_SYMBOL + parameterKey, e);
    }
    return parameters;
  }

  public static void loadExtraConfiguration() {
    try {
      proGenProps.calculateProperties();
      proGenProps.loadOtherProperties();
    } catch (FileNotFoundException fnfe) {
      throw new MissingContextFileException(fnfe.getMessage(), fnfe);
    } catch (IOException ioe) {
      throw new MissingContextFileException(ioe.getMessage(), ioe);
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
  private void loadOtherProperties() throws IOException {
    loadOtherProperties(PROGEN_EXPERIMENT_FILE_PROPERTY);
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
    if (propertyFile != null) {
      otherProperties = new Properties();
      try {
        findPropertiesAbsolutePath(propertyFile, otherProperties);
      } catch (FileNotFoundException fnfe) {
        findPropertiesUserProjectPath(propertyFile, otherProperties);
      }
      chekProperties(otherProperties);
    }
  }

  private void chekProperties(Properties otherProperties) {
    Enumeration<Object> keys;
    String key;
    String value;
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

  private void findPropertiesUserProjectPath(String propertyFile, Properties otherProperties) throws IOException {
    final String propertyFileNormalized = proGenProps.properties.getProperty(PROGEN_USER_HOME_PROPERTY).replace('.', File.separatorChar) + propertyFile;
    findPropertiesAbsolutePath(propertyFileNormalized, otherProperties);
  }

  private void findPropertiesAbsolutePath(String propertyFile, Properties otherProperties) throws IOException {
    final InputStream fis = getResource(propertyFile);
    otherProperties.load(fis);
    fis.close();
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
  private void loadOtherProperties(String propertyFile) throws IOException {
    Properties otherProperties;
    String otherFile = proGenProps.properties.getProperty(propertyFile);

    if (otherFile != null) {
      otherFile = convertClasspath2Path(otherFile);
      otherProperties = new Properties();
      try {
        lookForInAbsolutePath(otherProperties, otherFile);
      } catch (FileNotFoundException fnfe) {
        lookForInUserProject(propertyFile, otherProperties);
      }
      chekProperties(otherProperties);
    }
  }

  private void lookForInAbsolutePath(Properties otherProperties, String otherFile) throws IOException {
    final InputStream inputStream = getResource(otherFile);
    try {
      otherProperties.load(inputStream);
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }
  }

  private FileInputStream lookForInUserProject(String propertyFile, Properties otherProperties) throws IOException {
    FileInputStream fileInputStream = null;
    final String otherFile = ProGenContext.getProperty(PROGEN_USER_HOME_PROPERTY).replace('.', File.separatorChar) + proGenProps.properties.getProperty(propertyFile);
    try {
      fileInputStream = new FileInputStream(otherFile);
      otherProperties.load(fileInputStream);
    } finally {
      if (fileInputStream != null) {
        fileInputStream.close();
      }
    }

    return fileInputStream;
  }

  private String convertClasspath2Path(String otherFile) {
    final int lastDot = otherFile.lastIndexOf(DOT_SYMBOL);
    final String path = otherFile.substring(0, lastDot);
    final String ext = otherFile.substring(lastDot);
    return path.replace('.', File.separatorChar) + ext;
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
  private void calculateProperties() {
    final String experimentFile = ProGenContext.getProperty(PROGEN_EXPERIMENT_FILE_PROPERTY);
    if (experimentFile != null) {
      setUserHome(experimentFile);
    }
    updateExperimentFileProperty();
  }

  private void updateExperimentFileProperty() {
    String experimentFile = ProGenContext.getOptionalProperty(PROGEN_EXPERIMENT_FILE_PROPERTY, "");
    final int lastDotPosition = experimentFile.lastIndexOf('.');
    if (lastDotPosition >= 0) {
      experimentFile = experimentFile.replaceAll("\\.", File.separator);
      final StringBuilder builder = new StringBuilder(experimentFile);
      builder.setCharAt(lastDotPosition, '.');
      experimentFile = builder.toString();
      experimentFile = ProGenContext.class.getClassLoader().getResource(experimentFile).getFile();
      proGenProps.properties.setProperty(PROGEN_EXPERIMENT_ABSOLUTE_FILE_PROPERTY, experimentFile);
    }
  }

  private void setUserHome(String experimentFile) {
    final String experimentName = experimentFile.substring(0, experimentFile.lastIndexOf(DOT_SYMBOL));
    final String userHomeNormalized = experimentName.substring(0, experimentName.lastIndexOf(DOT_SYMBOL) + 1);
    proGenProps.properties.setProperty(PROGEN_USER_HOME_PROPERTY, userHomeNormalized);
    setExperimentName(experimentName);
  }

  private void setExperimentName(String experimentName) {
    final String experimentNameNormalized = experimentName.substring(experimentName.lastIndexOf(DOT_SYMBOL) + 1, experimentName.length());
    proGenProps.properties.setProperty("progen.experiment.name", experimentNameNormalized);
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
    final StringBuilder optionalFilesLoaded = new StringBuilder();
    final String optionalFile = normalizeOptionalFileName(sufixFile, optionalFilesLoaded);
    loadOptionalsProperties(optionalFile);
    optionalFilesLoaded.append(optionalFile).append(", ");
    ProGenContext.setProperty(PROGEN_OPTIONAL_FILES_PROPERTY, optionalFilesLoaded.toString());

  }

  private void closeSilently(InputStream fis) {
    try {
      if (fis != null) {
        fis.close();
      }
    } catch (IOException e) {
      // do nothing, ignore
    }
  }

  private String normalizeOptionalFileName(String sufixFile, final StringBuilder optionalFilesLoaded) {
    String optionalFile = ProGenContext.getMandatoryProperty(PROGEN_EXPERIMENT_FILE_PROPERTY);
    optionalFile = optionalFile.replaceAll("\\.txt$", sufixFile).replace('.', File.separatorChar) + ".txt";
    final URL optionalFileResource = ProGenContext.class.getClassLoader().getResource(optionalFile); 
    if( optionalFileResource == null){
      optionalFile = "";
    }else{
      optionalFile = optionalFileResource.getFile();
    }
    optionalFilesLoaded.append(ProGenContext.getOptionalProperty(PROGEN_OPTIONAL_FILES_PROPERTY, ""));
    return optionalFile;
  }

  private void loadOptionalsProperties(String optionalFile) {
    InputStream fis = null;
    try {
      fis = new FileInputStream(new File(optionalFile));
      mixProperties(fis);
    } catch (IOException e) {
      // do nothing, ignore
    } finally {
      closeSilently(fis);
    }
  }

  private void mixProperties(InputStream fis) throws IOException {
    Properties otherProperties;
    Enumeration<Object> keys;
    String key;
    String value;
    otherProperties = new Properties();
    otherProperties.load(fis);
    keys = otherProperties.keys();
    while (keys.hasMoreElements()) {
      key = (String) keys.nextElement();
      value = otherProperties.getProperty(key);
      proGenProps.properties.put(key, value);
    }
  }
}
