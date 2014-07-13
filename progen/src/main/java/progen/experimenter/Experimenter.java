package progen.experimenter;

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

/**
 * Interface que define los métodos de acceso a un experimento
 * 
 * @author jirsis
 * @since 2.0
 */
public abstract class Experimenter {

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
    String masterFile = ProGenContext.getMandatoryProperty("progen.masterfile");
    String experimentFile = ProGenContext.getMandatoryProperty("progen.experiment.file").replace('.', File.separatorChar).replace(File.separatorChar + "txt", ".txt");
    String optionalFiles = ProGenContext.getOptionalProperty("progen.optional.files", "");
    String currentDir = System.getProperty("user.dir");

    copyFile(masterFile, ProGenContext.getMandatoryProperty("progen.output.dir"));
    copyFile(experimentFile, ProGenContext.getMandatoryProperty("progen.output.dir"));
    for (String file : optionalFiles.trim().split(",[ ]*")) {
      if (file.length() > 0) {
        copyFile(currentDir + File.separatorChar + file, ProGenContext.getMandatoryProperty("progen.output.dir"));
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
      throw new IllegalArgumentException(Error.get(31));
    } else {
      copyFileSecure(original, copyPath);
    }
  }

  private void copyFileSecure(String original, String copyPath) {
    File originalFile = new File(original);
    File destinationFile = new File(copyPath + File.separator + originalFile.getName());
    if (!originalFile.exists()) {
      throw new ProGenException(Error.get(31) + " [" + original + "]");
    }
    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      inputStream = new FileInputStream(originalFile);
      outputStream = new FileOutputStream(destinationFile);
      copyFile(inputStream, outputStream);
    } catch (FileNotFoundException e) {
      throw new ProGenException(Error.get(31) + " [" + original + "]");
    } catch (IOException e) {
      throw new ProGenException(e.getLocalizedMessage());
    } finally {
      try {
        if(inputStream != null){
          inputStream.close();
        }
        if (outputStream != null){
          outputStream.close();
        }
      } catch (IOException e) {
        throw new ProGenException(e.getLocalizedMessage());
      }

    }
  }

  private void copyFile(InputStream inputStream, OutputStream outputStream) throws IOException {
    byte[] buffer = new byte[1024];
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
    if (!path.exists()) {
      deleted = false;
    } else {
      for (File file : path.listFiles()) {
        if (file.isDirectory()) {
          deleteDirectory(file);
        } else {
          deleted = file.delete();
        }
      }
      deleted = path.delete() && deleted;
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
  protected boolean generateOutputDir() {
    boolean createDir = false;
    StringBuilder defaultPath = new StringBuilder(100);

    defaultPath.append("outputs");
    defaultPath.append(File.separator);
    defaultPath.append(ProGenContext.getMandatoryProperty("progen.experiment.name"));

    String outputDir = ProGenContext.getOptionalProperty("progen.output.dir", defaultPath.toString());

    File dir = new File(outputDir);
    ProGenContext.setProperty("progen.output.dir", dir.getAbsolutePath() + File.separator);

    if (dir.exists()) {
      deleteDirectory(dir);
    }
    // creacion de la carpeta "home" de todos los ficheros de salida
    createDir = dir.mkdirs();

    return createDir;
  }

  /**
   * Prepara la carpeta que contendrá los resultados de la ejecución de un
   * experimento. El flujo de ejecución es: - defineExperimentDir() <br>
   * - crea la carpeta <br>
   * - generateResults()
   */
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value="RV_RETURN_VALUE_IGNORED_BAD_PRACTICE", justification="I don't really care makeDirs result")
  public final void generateOutputs() {
    String experimentDir = defineExperimentDir();

    if (!experimentDir.endsWith(File.separator)) {
      experimentDir += File.separator;
    }

    ProGenContext.setProperty("progen.output.experiment", experimentDir);
    File dir = new File(ProGenContext.getMandatoryProperty("progen.output.dir") + experimentDir);
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
