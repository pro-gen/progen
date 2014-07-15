package progen.output.outputers;

import progen.kernel.tree.Tree;

/**
 * Clase que formatea distintas cadenas para que la justificación dentro de una
 * cadena sea centrada, a la izquierda o a la derecha.
 * 
 * @author jirsis
 * @since 2.0
 */
public final class Formatter {

  private Formatter() {

  }

  /**
   * Centra el texto pasado como parámetro, en la longitud solicitada.
   * 
   * @param text
   *          El texto a centrar.
   * @param length
   *          El espacio donde centrarlo.
   * @return La cadena centrada en el espacio disponible.
   */
  public static String center(String text, int length) {
    int leftPad = 0;
    int rightPad = 0;
    String center;
    if (length > text.length()) {
      leftPad = (length - text.length()) / 2 + text.length();
      rightPad = Math.abs(length - leftPad);
      if (rightPad > 0) {
        center = String.format("%" + leftPad + "s%" + rightPad + "s", text, " ");
      } else {
        center = String.format("%" + leftPad + "s", text);
      }
    } else {
      center = text;
    }
    if (center.length() < length) {
      center = String.format("%" + (length - center.length()) + "s", " ");
    }
    return center;
  }

  /**
   * Justifica a la izquierda el texto pasado como parámetro, en la longitud
   * solicitada.
   * 
   * @param text
   *          El texto a justificar.
   * @param length
   *          El espacio donde centrarlo.
   * @return La cadena justificada en el espacio disponible.
   */
  public static String left(String text, int length) {
    final StringBuilder alignLeft = new StringBuilder(text);
    if (length > text.length()) {
      alignLeft.append(String.format("%" + (length - text.length()) + "s", " "));
    }
    return alignLeft.toString();
  }

  /**
   * Justifica a la derecha el texto pasado como parámetro, en la longitud
   * solicitada.
   * 
   * @param text
   *          El texto a justificar.
   * @param length
   *          El espacio donde centrarlo.
   * @return La cadena justificada en el espacio disponible.
   */
  public static String right(String text, int length) {
    final StringBuilder alignRight = new StringBuilder();
    if (length > text.length()) {
      alignRight.append(String.format("%" + (length - text.length()) + "s", " "));
    }
    alignRight.append(text);
    return alignRight.toString();
  }

  /**
   * Formatea un árbol proporcionado por parámetro de tal forma que devuelve un
   * <code>String</code> en forma de programa Lisp, identado en varias líneas,
   * según la profundidad del mismo.
   * 
   * @param tree
   *          El árbol a formatear.
   * @return La representación del árbol como programa Lisp
   */
  public static String tree(Tree tree) {
    final StringBuilder line = new StringBuilder();
    final String[] tokens = tree.toString().trim().split(" ");
    boolean function;
    int indent = 1;
    for (String token : tokens) {
      function = false;
      if (token.charAt(0) == '(') {
        // If the token is a function (starts with '(') we increment the indent
        // and flag it as function.
        indent++;
        function = true;
      }
      if (token.endsWith(")")) {
        // If we are finalizing a function, we reduce the indent, we delete the
        // last '\t' and we flat it
        // as no function. It is done in this way to work with functions
        // "(function)"
        indent--;
        function = false;
        line.deleteCharAt(line.length() - 1);
      }
      if (function) {
        line.append(token + " \t");
        function = false;
      } else {
        line.append(token + " " + indentation(indent));
      }
    }

    return line.toString();
  }

  /**
   * Añade tantos tabulados '\t' ( ), como indique el argumento.
   * 
   * @param indent
   *          El total de tabuladores a añadir.
   * @return La cadena con la indentación requerida.
   */
  private static String indentation(int indent) {
    int currentIndent = indent;
    final StringBuilder indentation = new StringBuilder();
    indentation.append(String.format("%n"));
    while ((currentIndent--) > 0) {
      indentation.append("\t");
    }
    return indentation.toString();
  }
}
