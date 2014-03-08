package progen.kernel.tree;

import java.util.List;

import progen.context.ProGenContext;
import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.Production;

/**
 * Clase que implementa la forma de inicialización Full. En este tipo de inicialización, 
 * se crearán árboles de tal forma que todas las ramas tendrán la misma profundidad.
 * De tal forma que será necesario especificar un intervalo de profundidades mínima
 * y máxima en la que estará comprendida la profundidad del árbol que se generará, 
 * sin exceder el número máximo de nodos permitido.
 * 
 * @author jirsis
 * @since 2.0
 */
public class Full implements InitializeTreeMethod{
	/** Profundidad mínima que tendrá el árbol resultante. */
	private int minDepth;
	/** Profundidad máxima que tendrá el árbol resultante. */
	private int maxDepth;
	/** Número de nodos máximo que puede tener el árbol construído. */
	private int maxNodes;
	
	/** Número máximo de intentos durante los que se intentará generar un árbol válido. */
	private int maxAttempts;
	
	/** 
	 * Profundidad temporal del árbol, según se va construyendo.
	 * Siempre deberá tener un valor entre los valores mínimo y máximo. 
	 */
	private int currentDepth;
	
	/**
	 * Constructor genérico de la clase, en la que se inicializan los atributos
	 * en función de la definición proporcionada en el fichero de propiedades del 
	 * dominio implementado por el usuario.
	 */
	public Full(){
		maxNodes=ProGenContext.getOptionalProperty("progen.population.max-nodes", Integer.MAX_VALUE);
		minDepth=0;
		maxDepth=0;
		maxAttempts=ProGenContext.getOptionalProperty("progen.max-attempts", 100);
		String intervalDepth [] = ProGenContext.getMandatoryProperty("progen.population.init-depth-interval").split(",");
		minDepth=Integer.parseInt(intervalDepth[0]);
		if(intervalDepth.length!=2){
			maxDepth=minDepth;
		}else{
			maxDepth=Integer.parseInt(intervalDepth[1]);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see progen.kernel.tree.InitializeTreeMethod#generate(progen.kernel.grammar.Grammar, progen.kernel.tree.Node)
	 */
	public void generate(Grammar grammar, Node root) {
		boolean generated = false;
		currentDepth=Math.max((int)(Math.random()*(maxDepth-minDepth+1)+minDepth), root.getDepth());
		while(!generated && conditionGeneration()){
			generated=generate(grammar, root, grammar.getRandomProductions(root.getSymbol()));
			if(!generated){
				updateCondition();
			}
		}
		
		if(!generated){
			throw new IncompatibleOptionsInitTreeMethodException(maxNodes, minDepth, maxDepth);
		}
	}
	
	/**
	 * Actualiza el valor de la condición de salida, según sea la condición de búsqueda activa
	 * o el máximo de intentos posibles.
	 */
	private void updateCondition() {
		boolean activeSearchEnable=ProGenContext.getOptionalProperty("progen.activeSearch", false);
		if(activeSearchEnable){
			activeSearchConditionUpdate();
		}else{
			maxAttemptsConditionUpdate();
		}
	}

	/**
	 * Actualiza la condición de máximo de intentos.
	 */
	private void maxAttemptsConditionUpdate() {
		currentDepth=(int)(Math.random()*(maxDepth-minDepth+1)+minDepth);
		maxAttempts--;
	}

	/**
	 * Actualiza la condición de búsqueda activa.
	 */
	private void activeSearchConditionUpdate() {
		currentDepth--;
	}

	/**
	 * Comprueba si se cumple el valor de la condición o no.
	 * @return Si se cumple la condición.
	 */
	private boolean conditionGeneration() {
		boolean activeSearchEnable=ProGenContext.getOptionalProperty("progen.activeSearch", false);
		boolean condition=true;
		if(activeSearchEnable){
			condition=activeSearchCondition();
		}else{
			condition=maxAttemptsCondition();
		}
		return condition;
	}

	/**
	 * Comprueba si la profundidad actual es superior a la profundidad mínima.
	 * @return <code>true</code> si la profundidad actual es mayor que la mínima definida.
	 */
	private boolean activeSearchCondition() {
		return currentDepth>=minDepth;
	}

	/**
	 * Comprueba si no se ha superado el número máximo de intentos posible.
	 * @return <code>true</code> si no se ha excedido el número máximo de intentos disponibles.
	 */
	private boolean maxAttemptsCondition() {
		return maxAttempts>0;
	}

	
	/**
	 * Método recursivo que va generando realmente el árbol de tal forma que se 
	 * van almacenando en el parámetro <code>stack</code> las producciones que 
	 * pueden generar ciertos elementos y que serán utilizados en caso de que 
	 * después de terminar de utilizar una producción determinada no se haya podido
	 * cumplir con la condición impuesta en el inicializador.
	 * 
	 * @param grammar Gramática de la que se obtendrán las distintas producciones
	 * que generarán el árbol.
	 * @param node Nodo que se está expandiendo en un determinado momento.
	 * @param stack Conjunto de producciones que se podrían utilizar en caso de que 
	 * no se pueda acabar de forma satisfactoria. Serán las producciones utilizadas
	 * en la parte de back-tracking del algoritmo.
	 * @return <code>true</code> si se terminó de generar correctamente el nodo y <code>false</code>
	 * en caso contrario.
	 */
	/*
	 * Como optimización del proceso, se hacen primero las comprobaciones sobre un nodo y una vez 
	 * se cumplan, se procederá a generar los distintos hijos, en función de la producción escogida.
	 * Las comprobaciones que se hacen sobre cada nodo consisten en:
	 * - comprobar la profundidad máxima
	 * - si es una hoja, comprobar la profundidad mínima
	 * - comprobar que no se supere el número máximo de nodos
	 * 
	 * Estas comprobaciones sería más intuitivo hacerlas después de generar todos los hijos, pero
	 * resulta mucho más eficiente si se realizan antes, dado que evita tener que generar y evaluar
	 * subramas del árbol, que se terminarán eliminando en muchos casos.
	 */
	private boolean generate(Grammar grammar, Node node, List<Production> stack){
		boolean generated = false;
		List<Production> branchStack;
		Node branch;
		
		if(node.getDepth()>currentDepth){
			generated=false;
		}else{
			while(!generated && stack.size()>0){
				generated=true;
				// se define el nodo con en funcion de la produccion elegida
				node.setProduction(stack.remove(0));
				//si el nodo es una hoja y esta a una profundidad menor
				// es necesario limpiarlo y pasar a la siguiente producción
				// para probar otra opción.
				if(node.isLeaf() && node.getDepth()<currentDepth){
					generated=false;
				}else if(maxNodeExceded(node)){
					//si se sobrepasa el número máximo de nodos, es necesario probar con
					// otra opción.
					generated=false;					
				}else{
					// se definen los hijos de este nodo
					int initialBranch=(int)(Math.random()*node.getBranches().size());
					for (int i=0; generated && i<node.getBranches().size(); i++) {
						branch=node.getBranches().get((i+initialBranch)%node.getBranches().size());
						// for(Node branch: node.getBranches()){
						branchStack=grammar.getRandomProductions(branch.getSymbol());
						generated&=generate(grammar,branch,branchStack);
					}
				}
				/*
				 * si alguno de los hijos no se pudo generar, se limpia el actual, para continuar con la
				 * siguiente producción.
				 */
				if(!generated){
					node.clearNode();
				}

			}
		}
		
		return generated;
	}
	
	/**
	 * Comprueba que el árbol no haya excedido el número total de nodos permitidos.
	 * @param node Nodo actual, que forma parte de un árbol, del que se calculará el número
	 * total de nodos que cuelgan de la raíz.
	 * @return <code>true</code> si se ha excedido el número máximo de nodos, <code>false</code>
	 * en caso contrario.
	 */
	private boolean maxNodeExceded(Node node){
		while(!node.isRoot()){
			node=node.getParent();
		}
		return node.getTotalNodes()>maxNodes;
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.tree.InitializeTreeMethod#updateMaximunDepth()
	 */
	public void updateMaximunDepth() {
		maxDepth=ProGenContext.getOptionalProperty("progen.population.max-depth.updated", maxDepth);
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.tree.InitializeTreeMethod#updateMaximunNodes()
	 */
	public void updateMaximunNodes() {
		maxNodes=ProGenContext.getOptionalProperty("progen.population.max-nodes.updated", maxNodes);
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.tree.InitializeTreeMethod#updateMinimunDepth()
	 */
	public void updateMinimunDepth() {
		minDepth=ProGenContext.getOptionalProperty("progen.population.min-depth.updated", minDepth);
	}

}
