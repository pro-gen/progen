package progen.output.outputers;

/**
 * Interfaz que define los métodos de acceso para todo tipo de salidas concretas
 * que la implementen.
 * 
 * @author jirsis
 * @since 2.0
 */
public interface Outputer {
  /**
   * Inicialización de la salida.
   */
  void init();

  /**
   * Finalización de la salida.
   */
  void close();

  /**
   * Imprime la información necesaria en la salida correspondiente.
   */
  void print();

}
