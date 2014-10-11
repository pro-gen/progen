package progen.kernel.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import progen.kernel.functions.NullFunction;
import progen.kernel.grammar.GrammarNonTerminalSymbol;
import progen.kernel.grammar.GrammarTerminalSymbol;
import progen.kernel.grammar.Production;
import progen.userprogram.UserProgram;

/**
 * Clase que proporciona todos los métodos necesarios para gestionar un nodo,
 * así como de la modificación de su contenido y calculo de la ejecución del
 * mismo.
 * 
 * @author jirsis
 * @since 1.0
 * @version 2.0
 */
public class Node implements Cloneable, Serializable {
  private static final long serialVersionUID = 8802106541230059873L;
  
  /** Número total de nodos que cuelgan de un nodo concreto */
  private int totalNodes;
  /** Profundidad a la que está dentro del árbol. */
  private int depth;
  /** Enlace al nodo padre. */
  private Node parent;
  /** Conjunto de nodos hijos */
  private List<Node> branches;
  /** Simbolo que representa a este nodo */
  private GrammarNonTerminalSymbol symbol;
  /** Función que contiene el nodo */
  private GrammarTerminalSymbol function;

  /**
   * Constructor que recibe el símbolo no terminal que contendrá el nodo.
   * 
   * @param symbol
   *          Símbolo que estará almacenado en el nodo.
   */
  public Node(GrammarNonTerminalSymbol symbol) {
    this.parent = null;
    this.branches = new ArrayList<Node>();
    this.symbol = symbol;
    this.function = new GrammarTerminalSymbol(new NullFunction(symbol.toString()));
    this.depth = 0;
    this.totalNodes = 1;
  }

  /**
   * Constructor de copia que recibe un nodo en el que basarse para duplicar.
   * 
   * @param node
   *          El nodo del que obtener la información de copia.
   * @param parent
   *          El nodo padre del nodo a copiar.
   */
  private Node(Node node, Node parent) {
    this.depth = node.depth;
    this.function = node.function;
    this.parent = parent;
    this.symbol = node.symbol;
    this.totalNodes = node.totalNodes;

    this.branches = new ArrayList<Node>();
    for (Node branch : node.branches) {
      this.branches.add(new Node(branch, this));
    }

  }

  /**
   * Constructor de la clase que recibe un símbolo no terminal y el padre para
   * que queden enlazados.
   * 
   * @param symbol
   *          Símbolo no terminal que se almacenará en el nodo.
   * @param parent
   *          Nodo que será el pader del actual.
   */
  public Node(GrammarNonTerminalSymbol symbol, Node parent) {
    // se enlazan el padre y el hijo
    this.parent = parent;
    parent.getBranches().add(this);

    this.branches = new ArrayList<Node>();
    this.symbol = symbol;
    this.function = new GrammarTerminalSymbol(new NullFunction(symbol.toString()));
    this.depth = parent.getDepth() + 1;
    this.totalNodes = 0;
    addTotalNodes(1);
  }

  /**
   * Especifica la función que contiene el nodo.
   * 
   * @param function
   *          La función que contiene el nodo.
   */
  public void setFunction(GrammarTerminalSymbol function) {
    this.function = function;
  }

  /**
   * Devuelve el número total de nodos que cuelgan de uno concreto.
   * 
   * @return el número total de nodos.
   */
  public int getTotalNodes() {
    return totalNodes;
  }

  /**
   * Devuelve el nodo padre del nodo actual
   * 
   * @return el padre del nodo actual o <code>null</code> en caso de ser la
   *         raíz.
   */
  public Node getParent() {
    return parent;
  }

  /**
   * Devuelve la función que contiene en el nodo.
   * 
   * @return la función que contiene el nodo.
   */
  public GrammarTerminalSymbol getFunction() {
    return function;
  }

  /**
   * Devuelve el símbolo no terminal de la gramática que generó este nodo.
   * 
   * @return el símbolo no terminal de la gramática que generó el nodo.
   */
  public GrammarNonTerminalSymbol getSymbol() {
    return symbol;
  }

  /**
   * Devuelve una lista con todas las ramas del nodo.
   * 
   * @return conjunto de ramas que cuelgan del nodo.
   */
  public List<Node> getBranches() {
    return branches;
  }

  /**
   * Devuelve la profundidad a la que se encuentra un nodo.
   * 
   * @return la profundidad a la que está el nodo.
   */
  public int getDepth() {
    return depth;
  }

  /**
   * Define a que profundidad se encunetra el nodo y actualiza la profundidad de
   * todas sus ramas.
   * 
   * @param depth
   *          la nueva profundidad.
   */
  private void setDepth(int depth) {
    this.depth = depth;
    for (Node node : branches) {
      node.setDepth(depth + 1);
    }
  }

  /**
   * Completa la información del nodo a partir de una producción pasada por
   * parámetro.
   * 
   * @param production
   *          La producción de la que obtiene la información.
   */
  public void setProduction(Production production) {
    symbol = production.getLeft();
    function = production.getFunction();
    for (int i = 0; i < production.getArgs().length; i++) {
      new Node(production.getArgs()[i], this);
    }
  }

  /**
   * Define una rama en una posición concreta, actualizando la información
   * relativa a la posición dentro del árbol del que forma parte.
   * 
   * @param branch
   *          Rama a colocar.
   * @param branchPosition
   *          Posición de la rama.
   */
  public void setBranch(Node branch, int branchPosition) {
    this.addTotalNodes(branch.getTotalNodes());
    branch.parent = this;
    branch.setDepth(depth + 1);
    if (branchPosition > branches.size()) {
      branches.add(branchPosition - 1, branch);
    } else {
      branches.add(branchPosition, branch);
    }

  }

  /**
   * Separa al nodo del árbol del que forma parte quedando como raíz del
   * subárbol que definía. Actualiza y define todos los atributos de tal forma
   * que es la raíz de un árbol.
   * 
   * @return El nodo ráiz del subárbol que definía.
   */
  public Node branch() {
    if (!this.isRoot()) {
      parent.addTotalNodes(-this.totalNodes);

      int thisNode = 0;
      for (int i = 0; i < parent.getBranches().size(); i++) {
        if (this == parent.getBranches().get(i)) {
          thisNode = i;
        }
      }
      parent.branches.remove(thisNode);
      parent = null;
      depth = 0;
    }
    return this;
  }

  /**
   * Actualiza el número total de nodos que cuelgan de un nodo y de los nodos
   * que están por encima de él hasta llegar a la raíz.
   * 
   * @param totalNodes
   *          El número de nodos a actualizar.
   */
  public void addTotalNodes(int totalNodes) {
    if (parent != null) {
      parent.addTotalNodes(totalNodes);
    }
    this.totalNodes += totalNodes;
  }

  /**
   * Limpia el contenido del nodo en concreto y de todas las ramas que tenía.
   */
  public void clearNode() {
    function = new GrammarTerminalSymbol(new NullFunction(symbol.toString()));
    for (int i = 0; i < branches.size(); i++) {
      addTotalNodes(-branches.get(i).getTotalNodes());
    }
    branches.clear();

  }

  /**
   * Comprueba si el nodo es un nodo hoja o no, esto es, si tiene alguna rama o
   * no.
   * 
   * @return <code>true</code> si es una hoja y <code>false</code> en caso
   *         contrario.
   */
  public boolean isLeaf() {
    return branches.size() == 0;
  }

  /**
   * Comprueba si el nodo es un nodo raíz o no, esto es, si tiene algún nodo que
   * sea padre suyo.
   * 
   * @return <code>true</code> si es la raíz y <code>false</code> en caso
   *         contrario.
   */
  public boolean isRoot() {
    return parent == null;
  }

  @Override
  public String toString() {
    final StringBuilder node = new StringBuilder();
    if (isLeaf()) {
      node.append(function);
    } else {
      node.append("(").append(function);
      for (int i = 0; i < branches.size(); i++) {
        node.append(" ").append(branches.get(i).toString());
      }
      node.append(" )");
    }
    return node.toString();
  }

  /**
   * Evalúa el nodo llamando a la función que contiene.
   * 
   * @param userProgram
   *          Representación del dominio implementado por el usuario.
   * @param returnAddr
   *          Dirección de retorno de las llamadas a ADFs.
   * @return Valor después de ejecutar la función del nodo.
   */
  public Object evaluate(UserProgram userProgram, Map<String, Object> returnAddr) {
    return function.getFunction().evaluate(branches, userProgram, returnAddr);
  }

  /**
   * Devuelve el nodo que se encuentra en la posición solicitada, recorriendo
   * todos los hijos siguiendo en pre-orden.
   * 
   * @param position
   *          La posición del nodo buscado.
   * @return El nodo que está en la posición solicitada o <code>null</code> si
   *         solicitó un nodo fuera de los rangos permitidos.
   */
  public Node getNode(int position) {
    Node node = null;
    // si se busca un numero < 0 o mayor que el numero de nodos total
    if (position < this.getTotalNodes() && position >= 0) {
      node = findNode(position);
    }
    return node;
  }

  /**
   * Método recursivo que recorre todos los nodos a partir del actual buscando
   * el que esté en la posción
   * 
   * @param position
   *          Posición del nodo que se quiere buscar.
   * @return Nodo que está en la posición requerida.
   */
  private Node findNode(int position) {
    Node node = null;
    int currentPosition = position;
    if (currentPosition == 0) {
      node = this;
    } else {
      for (int i = 0; node == null && i < branches.size(); i++) {
        node = branches.get(i).findNode(currentPosition - 1);
        currentPosition -= branches.get(i).getTotalNodes();
      }
    }
    return node;
  }

  /**
   * Devuelve la profundidad a la que se encuentra el nodo más profundo de todos
   * los hijos que tenga un nodo concreto.
   * 
   * @return La profundidad a la que se encuentra el nodo que está más profundo.
   */
  public int getMaximunDepth() {
    int maxDepth = 0;
    if (this.isLeaf()) {
      maxDepth = depth;
    } else {
      for (Node branch : branches) {
        maxDepth = Math.max(maxDepth, branch.getMaximunDepth());
      }
    }
    return maxDepth;
  }
  
  /**
   * La clonación de un nodo implica crear nuevos nodos copia a partir de uno
   * dado. Se recomienda clonar a partir de un nodo raíz.
   * 
   * @return Node clonado.
   * @see java.lang.Object#clone()
   */
  public Node clone() {
    try {
      super.clone();
    } catch (CloneNotSupportedException e) {
      // ignore this exception
    }
    
    return new Node(this, this.parent);
  }
}
