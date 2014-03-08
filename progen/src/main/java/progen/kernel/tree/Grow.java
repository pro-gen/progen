package progen.kernel.tree;

import java.util.List;

import progen.context.ProGenContext;
import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.Production;

/**
 * Clase que implementa el método de inicialización de árboles grow. Esta forma
 * de inicializar un árbol, consiste en generar un árbol tal que todos los nodos
 * hoja estén a una profundidad mínima del parámetro definido y al menos uno de
 * esos nodos hoja, a la profundidad máxima especificada; siempre y cuando no se
 * exceda el número total de nodos que se permita por configuración.
 * 
 * @author jirsis
 * @since 2.0
 */
public class Grow implements InitializeTreeMethod {

    /** Profundidad mínima de los nodos. */
    private int minDepth;
    /** Profundidad máxima de los nodos. */
    private int maxDepth;
    /** Número máximo de nodos que se permiten en el árbol. */
    private int maxNodes;
    /**
     * Número máximo de intentos durante los que se intentará generar un árbol
     * válido.
     */
    private int maxAttempts;

    /**
     * Constructor genérico de la clase, en la que se inicializan los atributos
     * en función de la definición proporcionada en el fichero de propiedades
     * del dominio implementado por el usuario.
     */
    public Grow() {
	maxNodes = ProGenContext.getOptionalProperty(
		"progen.population.max-nodes", Integer.MAX_VALUE);
	minDepth = 0;
	maxDepth = 0;
	maxAttempts = ProGenContext.getOptionalProperty("progen.max-attempts",
		100);
	String intervalDepth[] = ProGenContext.getMandatoryProperty(
		"progen.population.init-depth-interval").split(",");
	minDepth = Integer.parseInt(intervalDepth[0]);
	if (intervalDepth.length != 2) {
	    maxDepth = minDepth;
	} else {
	    maxDepth = Integer.parseInt(intervalDepth[1]);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * progen.kernel.tree.InitializeTreeMethod#generate(progen.kernel.grammar
     * .Grammar, progen.kernel.tree.Node)
     */
    public void generate(Grammar grammar, Node root) {
	boolean generated = false;

	while (!generated && maxAttempts > 0) {
	    generated = generate(grammar, root,
		    grammar.getRandomProductions(root.getSymbol()));
	    if (!generated) {
		maxAttempts--;
	    }
	}

	if (!generated) {
	    throw new IncompatibleOptionsInitTreeMethodException(maxNodes,
		    minDepth, maxDepth);
	}
    }

    /**
     * Método recursivo que va generando realmente el árbol de tal forma que se
     * van almacenando en el parámetro <code>stack</code> las producciones que
     * pueden generar ciertos elementos y que serán utilizados en caso de que
     * después de terminar de utilizar una producción determinada no se haya
     * podido cumplir con la condición impuesta en el inicializador.
     * 
     * @param grammar
     *            Gramática de la que se obtendrán las distintas producciones
     *            que generarán el árbol.
     * @param node
     *            Nodo que se está expandiendo en un determinado momento.
     * @param stack
     *            Conjunto de producciones que se podrían utilizar en caso de
     *            que no se pueda acabar de forma satisfactoria. Serán las
     *            producciones utilizadas en la parte de back-tracking del
     *            algoritmo.
     * @return <code>true</code> si se terminó de generar correctamente el nodo
     *         y <code>false</code> en caso contrario.
     */
    /*
     * Como optimización del proceso, se hacen primero las comprobaciones sobre
     * un nodo y una vez se cumplan, se procederá a generar los distintos hijos,
     * en función de la producción escogida. Las comprobaciones que se hacen
     * sobre cada nodo consisten en: - comprobar la profundidad máxima - si es
     * una hoja, comprobar la profundidad mínima - comprobar que no se supere el
     * número máximo de nodos
     * 
     * Estas comprobaciones sería más intuitivo hacerlas después de generar
     * todos los hijos, pero resulta mucho más eficiente si se realizan antes,
     * dado que evita tener que generar y evaluar subramas del árbol, que se
     * terminarán eliminando en muchos casos.
     */
    private boolean generate(Grammar grammar, Node node, List<Production> stack) {
	boolean generated = false;
	List<Production> branchStack;
	Node branch;

	if (node.getDepth() > maxDepth) {
	    generated = false;
	} else {
	    while (!generated && stack.size() > 0) {
		generated = true;
		// se define el nodo con en funcion de la produccion elegida
		node.setProduction(stack.remove(0));
		// si el nodo es una hoja y esta a una profundidad menor
		// es necesario limpiarlo y pasar a la siguiente producción
		// para probar otra opción.
		if (node.isLeaf() && node.getDepth() < minDepth) {
		    generated = false;
		} else if (maxNodeExceded(node)) {
		    // si se sobrepasa el número máximo de nodos, es necesario
		    // probar con
		    // otra opción.
		    generated = false;
		} else {
		    // se definen los hijos de este nodo
		    int initialBranch = (int) (Math.random() * node
			    .getBranches().size());
		    for (int i = 0; generated && i < node.getBranches().size(); i++) {
			branch = node.getBranches()
				.get((i + initialBranch)
					% node.getBranches().size());
			// for(Node branch: node.getBranches()){
			branchStack = grammar.getRandomProductions(branch.getSymbol());
			generated &= generate(grammar, branch, branchStack);
		    }
		}
		/*
		 * si alguno de los hijos no se pudo generar, se limpia el
		 * actual, para continuar con la siguiente producción.
		 */
		if (!generated) {
		    node.clearNode();
		}

	    }
	}

	return generated;
    }

    /**
     * Comprueba que el árbol no haya excedido el número total de nodos
     * permitidos.
     * 
     * @param node
     *            Nodo actual, que forma parte de un árbol, del que se calculará
     *            el número total de nodos que cuelgan de la raíz.
     * @return <code>true</code> si se ha excedido el número máximo de nodos,
     *         <code>false</code> en caso contrario.
     */
    private boolean maxNodeExceded(Node node) {
	while (!node.isRoot()) {
	    node = node.getParent();
	}
	return node.getTotalNodes() > maxNodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.kernel.tree.InitializeTreeMethod#updateMaximunDepth()
     */
    public void updateMaximunDepth() {
	maxDepth = ProGenContext.getOptionalProperty(
		"progen.population.max-depth.updated", maxDepth);
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.kernel.tree.InitializeTreeMethod#updateMaximunNodes()
     */
    public void updateMaximunNodes() {
	maxNodes = ProGenContext.getOptionalProperty(
		"progen.population.max-nodes.updated", maxNodes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see progen.kernel.tree.InitializeTreeMethod#updateMinimunDepth()
     */
    public void updateMinimunDepth() {
	minDepth = ProGenContext.getOptionalProperty(
		"progen.population.min-depth.updated", minDepth);
    }
}