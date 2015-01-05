package progen.experimenter;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import progen.ProGenException;
import progen.context.ProGenContext;
import progen.kernel.error.Error;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Interface que define los métodos de acceso a un experimento
 * 
 * @author jirsis
 * @since 2.0
 */
public abstract class Experimenter {

  private static final String PROGEN_OUTPUT_DIR_PROPERTY = "progen.output.dir";
  private static final String PROGEN_OPTIONAL_FILES_PROPERTY = "progen.optional.files";
  private static final String PROGEN_EXPERIMENT_FILE_PROPERTY = "progen.experiment.file";
  private static final String PROGEN_EXPERIMENT_ABSOLUTE_FILE_PROPERTY = "progen.experiment.file.absolute";
  private static final String SPACE_SYMBOL = " ";
  private static final String SQUARE_RIGHT_BRACKET_SYMBOL = "[";
  private static final String SQUARE_LEFT_BRACKET_SYMBOL = "]";
  private static final int PROGEN_ID_ERROR = 31;

  /**
   * Constructor por defecto de todas las clases hijas.
   */
  public Experimenter() {
    this.generateOutputDir();
    this.dumpContext();
  }

  /**
   * Copia de los ficheros que definen el contexto del experimento a la carpeta
   * de salida definida en la propiedad "progen.output.dir" o en
   * &lt;user.bin&gt;/outputs.
   */
  private void dumpContext() {
    final String masterFile = ProGenContext.getMandatoryProperty("progen.masterfile");
    final String experimentFile = ProGenContext.getMandatoryProperty(PROGEN_EXPERIMENT_ABSOLUTE_FILE_PROPERTY);
    final String optionalFiles = ProGenContext.getOptionalProperty(PROGEN_OPTIONAL_FILES_PROPERTY, "");
    final String currentDir = System.getProperty("user.dir");

    copyFile(masterFile, ProGenContext.getMandatoryProperty(PROGEN_OUTPUT_DIR_PROPERTY));
    copyFile(experimentFile, ProGenContext.getMandatoryProperty(PROGEN_OUTPUT_DIR_PROPERTY));
    for (String file : optionalFiles.trim().split(",[ ]*")) {
      if (file.length() > 0) {
        copyFile(file, ProGenContext.getMandatoryProperty(PROGEN_OUTPUT_DIR_PROPERTY));
      }
    }
  }

  /**
   * Copia el fichero original definido como un path absoluto, en el path de
   * destino, manteniendo el nombre del fichero original.
   * 
   * @param original
   *          Path absoluto del fichero original a copiar.
   * @param copyPath
   *          Path de destino de la copia.
   */
  protected void copyFile(String original, String copyPath) {
    if (original == null || copyPath == null) {
      throw new IllegalArgumentException(Error.get(PROGEN_ID_ERROR));
    } else {
      copyFileSecure(original, copyPath);
    }
  }

  private void copyFileSecure(String original, String copyPath) {
    final File originalFile = new File(original);
    final File destinationFile = new File(String.format("%s%s%s", copyPath, File.separator, originalFile.getName()));
    if (!originalFile.exists()) {
      throw new ProGenException(String.format("%s%s%s%s%s", Error.get(PROGEN_ID_ERROR), SPACE_SYMBOL, SQUARE_RIGHT_BRACKET_SYMBOL, original, SQUARE_LEFT_BRACKET_SYMBOL));
    }
    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      inputStream = new FileInputStream(originalFile);
      outputStream = new FileOutputStream(destinationFile);
      copyFile(inputStream, outputStream);
    } catch (FileNotFoundException e) {
      throw new ProGenException(Error.get(PROGEN_ID_ERROR) + SQUARE_RIGHT_BRACKET_SYMBOL + original + SQUARE_LEFT_BRACKET_SYMBOL, e);
    } catch (IOException e) {
      throw new ProGenException(e.getLocalizedMessage(), e);
    } finally {
      closeSilentlyStream(inputStream);
      closeSilentlyStream(outputStream);
    }
  }

  private void closeSilentlyStream(Closeable stream) {
    try {
      if (stream != null) {
        stream.close();
      }
    } catch (IOException e) {
      throw new ProGenException(e.getLocalizedMessage(), e);
    }
  }

  private void copyFile(InputStream inputStream, OutputStream outputStream) throws IOException {
    final int bufferSize = 1024;
    final byte[] buffer = new byte[bufferSize];
    int length;
    while ((length = inputStream.read(buffer)) > 0) {
      outputStream.write(buffer, 0, length);
    }
  }

  /**
   * Función recursiva que elimina todo el contenido de un directorio para poder
   * eliminarlo.
   * 
   * @param path
   *          Ruta de la carpeta a borrar.
   * @return <code>true</code> si se pudo eliminar el directorio y
   *         <code>false</code> en caso contrario.
   */
  public boolean deleteDirectory(File path) {
    boolean deleted = true;
    if (path.exists()) {
      for (File file : path.listFiles()) {
        if (file.isDirectory()) {
          deleteDirectory(file);
        } else {
          deleted = file.delete();
        }
      }
      deleted = path.delete() && deleted;
    } else {
      deleted = false;
    }
    return deleted;
  }

  /**
   * Crea la carpeta en la que se generarán todos los ficheros de salida que
   * estén configurados. Se almacena esta ruta en la variable
   * <code>progen.output.dir</code>
   * 
   * @return <code>true</code> si se pudieron crear todas las carpetas
   *         necesarias y <code>false</code> en caso contrario.
   */
  private boolean generateOutputDir() {
    final StringBuilder defaultPath = generateDefaultPath();
    final File dir = generateOutputDir(defaultPath);
    ProGenContext.setProperty(PROGEN_OUTPUT_DIR_PROPERTY, dir.getAbsolutePath() + File.separator);
    if (dir.exists()) {
      deleteDirectory(dir);
    }
    return dir.mkdirs();
  }

  private File generateOutputDir(final StringBuilder defaultPath) {
    final String outputDir = ProGenContext.getOptionalProperty(PROGEN_OUTPUT_DIR_PROPERTY, defaultPath.toString());
    return new File(outputDir);
  }

  private StringBuilder generateDefaultPath() {
    final StringBuilder defaultPath = new StringBuilder(100);
    defaultPath.append("outputs");
    defaultPath.append(File.separator);
    defaultPath.append(ProGenContext.getMandatoryProperty("progen.experiment.name"));
    return defaultPath;
  }

  /**
   * Prepara la carpeta que contendrá los resultados de la ejecución de un
   * experimento. El flujo de ejecución es: - defineExperimentDir() <br>
   * - crea la carpeta <br>
   * - generateResults()
   */
  @SuppressFBWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE", justification = "I don't really care makeDirs result")
  public final void generateOutputs() {
    String experimentDir = defineExperimentDir();

    if (!experimentDir.endsWith(File.separator)) {
      experimentDir += File.separator;
    }

    ProGenContext.setProperty("progen.output.experiment", experimentDir);
    final File dir = new File(ProGenContext.getMandatoryProperty(PROGEN_OUTPUT_DIR_PROPERTY) + experimentDir);
    dir.mkdir();
    generateResults();

  }

  /**
   * Comprueba si se dan las condiciones para dar por acabado el experimento.
   * 
   * @return <code>true</code> si se acabó y <code>false</code> en caso
   *         contrario.
   */
  public abstract boolean isDone();

  /**
   * Define, en ProGenContext, el valor de la propiedad que se está procesando
   * en un momento determinado.
   * 
   * @see ProGenContext
   */
  public abstract void defineValues();

  /**
   * Incrementa el valor de las propiedades para generar un nuevo experimento
   * concreto.
   */
  public abstract void updateValues();

  /**
   * Define la propiedad <code>progen.output.experiment</code>, encargada de
   * definir en que subcarpeta se almacenarán los resultados de un experimento
   * concreto.
   * 
   * @return Devuelve el subdirectorio en el que se guardarán los resultados
   *         concretos de un experimento.
   */
  public abstract String defineExperimentDir();

  /**
   * Genera los ficheros de salida correspondiente al exeperimento que se haya
   * ejecutado.
   */
  public abstract void generateResults();

  /**
   * Devuelve una cadena con información de la finalización del experimento.
   * 
   * @return La cadena con informacióin acerca de la finalización del
   *         experimento.
   */
  public abstract String finishMessage();

}
