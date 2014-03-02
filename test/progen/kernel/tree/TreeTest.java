package test.progen.kernel.tree;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.kernel.grammar.Grammar;
import progen.kernel.tree.Full;
import progen.kernel.tree.Tree;
import progen.userprogram.UserProgram;

public class TreeTest {
	
	private Tree tree;

	@Before
	public void setUp() throws Exception {
		ProGenContext.makeInstance();
		ProGenContext.setProperty("progen.population.init-mode", "full");
		ProGenContext.setProperty("progen.population.init-depth-interval", "5,10");
		
		ProGenContext.setProperty("progen.RBP0.functionSet", "0");
		ProGenContext.setProperty("progen.functionSet0.return", "double");
		ProGenContext.setProperty("progen.functionSet0", "DoubleX, DoublePlus");
		
		
		tree=new Tree();
		testGenerate();
	}

	@Test
	public void testTreeTree() {
		Tree other = new Tree(tree);
		assertFalse(other == tree);
		assertTrue(other.getInitializeTreeMethod() == tree.getInitializeTreeMethod());
		assertFalse(other.getRoot() == tree.getRoot());
	}

	@Test
	public void testTree() {
		Tree other = new Tree();
		assertTrue(other.getRoot() == null);
		assertTrue(other.getInitializeTreeMethod() instanceof Full);
	}

	@Test
	public void testGenerate() {
		Grammar grammar = Grammar.makeInstance("RBP0");
		tree.generate(grammar);
		assertFalse(tree.getRoot() == null);
	}

	@Test
	public void testGetRoot() {
		assertTrue(tree.getRoot().isRoot());
		assertTrue(tree.getNode(0).isRoot());
	}

	@Test
	public void testEvaluate() {
		tree.evaluate(UserProgram.getUserProgram(), null);
	}

	@Test
	public void testGetNode() {
		assertTrue(tree.getNode(0)==tree.getRoot());
		assertTrue(tree.getNode(1) == tree.getRoot().getBranches().get(0));
	}

	@Test
	public void testClone() {
		Tree clone=tree.clone();
		assertFalse(clone == tree);
		assertTrue(clone.getInitializeTreeMethod() == tree.getInitializeTreeMethod());
		assertTrue(clone.toString().equals(tree.toString()));
	}

	@Test
	public void testGetInitializeTreeMethod() {
		assertTrue(tree.getInitializeTreeMethod() instanceof Full);
	}

}
