/**
 * 
 */
package progen.output.outputers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import progen.context.ProGenContext;

/**
 * @author jirsis
 * @since 2.0
 * 
 */
public abstract class FileOutput implements Outputer {

  /** El escritor en fichero. */
  protected PrintWriter writer;

  /** Nombre del fichero que contiene la salida. */
  private String fileName;

  /** Indica si se va crear vacío o si se va a añadir más información. */
  private boolean append;

  /** Almacén de todos los literales de texto que aparecerán en la salida. */
  protected ResourceBundle literals;

  /**
   * Constructor que recibe como parámetro el nombre del fichero y en que modo
   * se crea el fichero, si será vaciado al crearlo o se añadirán nuevas líneas.
   * 
   * @param fileName
   *          El nombre del fichero.
   * @param append
   *          <code>true</code> si se añadirán más líneas y <code>false</code>
   *          en caso contrario.
   */
  public FileOutput(String fileName, boolean append) {
    this.fileName = fileName;
    this.append = append;
    this.literals = ResourceBundle.getBundle("progen.output.outputers.literals", Locale.getDefault());
  }

  /*
   * (non-Javadoc)
   * 
   * @see progen.output.outputers.Outputer#close()
   */
  public void close() {
    writer.flush();
    writer.close();

  }

  /*
   * (non-Javadoc)
   * 
   * @see progen.output.outputers.Outputer#init()
   */
  public void init() {
    try {
      StringBuilder outputDir = new StringBuilder(50);
      outputDir.append(ProGenContext.getMandatoryProperty("progen.output.dir"));
      outputDir.append(ProGenContext.getMandatoryProperty("progen.output.experiment"));
      outputDir.append(fileName);
      File file = new File(outputDir.toString());
      writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, append), "UTF-8"));

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
