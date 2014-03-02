package progen.kernel.tree;

import progen.context.MalformedPercentSuboptionException;
import progen.context.ProGenContext;
import progen.kernel.grammar.Grammar;

/**
 * Clase que implementa la forma más común de inicialización de los árboles en programación
 * genética, que es una mezcla de inicialización Full y Grow, de forma que se elegirá un 
 * método u otro en función de la probabilidad especificada en la propiedad 
 * 'progen.population.init-mode'
 * 
 * @author jirsis
 * 
 * @see progen.kernel.tree.Full
 * @see progen.kernel.tree.Grow
 * 
 * @since 2.0
 */
public class HalfAndHalf implements InitializeTreeMethod{
	/** Inicializador tipo Full */
	private InitializeTreeMethod full;
	/** Inicializador tipo Grow */
	private InitializeTreeMethod grow;
	/** Probabilidad de usar el inicializador Full */
	private double percentFull;
	/** Probabilidad de usar el inicializador Grow */
	private double percentGrow;
	
	/**
	 * Constructor genérico de la clase, en la que se inicializan las probabilidades de uso
	 * de los inicializadores y se comprueba que sumen 100%
	 * En caso de no haber definido las probabilidades particulares de alguno de los 
	 * inicializadores, se asumirá que la probabilidad de los dos es del 50%.
	 */
	public HalfAndHalf(){
		full=new Full();
		grow=new Grow();
		percentFull=ProGenContext.getSuboptionPercent("progen.population.init-mode", "full", 0.5);
		percentGrow=ProGenContext.getSuboptionPercent("progen.population.init-mode", "grow", 0.5);
		if(percentFull+percentGrow!=1){
			throw new MalformedPercentSuboptionException("progen.population.init-mode");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see progen.kernel.tree.InitializeTreeMethod#generate(progen.kernel.grammar.Grammar, progen.kernel.tree.Node)
	 */
	public void generate(Grammar grammar, Node root){
		if(Math.random()>=percentFull){
			full.generate(grammar, root);
		}else{
			grow.generate(grammar, root);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.tree.InitializeTreeMethod#updateMaximunDepth()
	 */
	public void updateMaximunDepth() {
		full.updateMaximunDepth();
		grow.updateMaximunDepth();
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.tree.InitializeTreeMethod#updateMaximunNodes()
	 */
	public void updateMaximunNodes() {
		full.updateMaximunNodes();
		grow.updateMaximunNodes();
	}

	/*
	 * (non-Javadoc)
	 * @see progen.kernel.tree.InitializeTreeMethod#updateMinimunDepth()
	 */
	public void updateMinimunDepth() {
		full.updateMinimunDepth();
		full.updateMinimunDepth();
	}
}
