package progen.kernel.evolution.operators;

import java.util.ArrayList;
import java.util.List;

import progen.kernel.tree.Node;
import progen.kernel.tree.Tree;

/**
 * Implementación del operador de cruce de un punto. Este operador trabaja de tal forma 
 * que selecciona un nodo compatible de la parte similar de dos árboles. Estos árboles
 * pertenecen a dos individuos distintos, pero tienen el mismo identificador, es decir, 
 * son RBP0 ambos, o ADF0 ambos, etc.
 * 
 * La parte común de dos árboles se entenderá como el conjunto de nodos de ambos 
 * árboles que contienen la misma función, de tal forma que si se superpusieran 
 * ambos árboles, esta parte común sería exactamente igual.
 * 
 * @author jirsis
 * @since 2.0
 */
public class OnePointCrossover extends StandardCrossover {

	/**
	 * En este caso, la selección de nodos se realiza de acuerdo 
	 * al comportamiento que se espera para este operador, es decir, 
	 * devuelve dos nodos, uno de cada árbol, de todos aquellos 
	 * que pertenecen a la parte común de esos árboles.
	 * 
	 * @see StandardCrossover#selectNodes(Tree treeA, Tree treeB)
	 */
	@Override
	protected List<Node> selectNodes(Tree treeA, Tree treeB){
		List<Node> nodes=new ArrayList<Node>();
		List<Node> stackA=new ArrayList<Node>();
		List<Node> stackB=new ArrayList<Node>();
		List<Node> comunA=new ArrayList<Node>();
		List<Node> comunB=new ArrayList<Node>();
		
		selectComun(treeA.getRoot(), treeB.getRoot(), stackA, stackB, comunA, comunB);
		
		//se eliminan las raices dado que es el único nodo que aún siendo
		//común, no se puede utilizar para cruzar.
		comunA.remove(treeA.getRoot());
		comunB.remove(treeB.getRoot());
		
		if(comunA.size()>0 && comunB.size()>0){
			int randomNode=(int)(Math.random()*comunA.size());
			nodes.add(comunA.get(randomNode));
			nodes.add(comunB.get(randomNode));
		}
		return nodes;
	}

	/**
	 * Selecciona a partir de dos nodos, todos los nodos comunes que comparten dichos nodos.
	 * Un nodo es compartido por varios nodos, si ambos nodos tienen una función con el mismo
	 * símbolo y con el mismo valor de retorno.
	 * 
	 * @param nodeA Un de los nodos.
	 * @param nodeB El otro nodo.
	 * @param stackA Una pila en la que se almacenan los nodos hijo que faltan por procesar del nodo A.
	 * @param stackB Una pila en la que se almacenan los nodos hijo que faltan por procesar del nodo B.
	 * @param comunA Conjunto de nodos que forman la parte común al árbol A.
	 * @param comunB Conjunto de nodos que forman la parte común al árbol B.
	 */
	private void selectComun(Node nodeA, Node nodeB, List<Node> stackA,
			List<Node> stackB, List<Node> comunA, List<Node> comunB) {
		if(nodeA.getFunction().getFunction().getReturnType().equals(nodeB.getFunction().getFunction().getReturnType()) && 
			nodeA.getFunction().getFunction().getSymbol().equals(nodeB.getFunction().getFunction().getSymbol())){
			stackA.addAll(nodeA.getBranches());
			stackB.addAll(nodeB.getBranches());
			comunA.add(nodeA);
			comunB.add(nodeB);
		}else{
			stackA.removeAll(nodeA.getBranches());
			stackB.removeAll(nodeB.getBranches());
		}
		
		if(stackA.size()>0 && stackB.size()>0){
			selectComun(stackA.remove(0), stackB.remove(0), stackA, stackB, comunA, comunB);
		}
	}
}
