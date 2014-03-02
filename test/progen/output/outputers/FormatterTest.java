package test.progen.output.outputers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import progen.output.outputers.Formatter;

public class FormatterTest {
	
	/** Texto largo */
	private String longText;
	/** Texto corto */
	private String shortText;
	
	/**
	 * Declaración de un estado inicial conocido para todos los tests
	 */
	@Before
	public void setUp(){
		longText="Lorem ipsum dolor sit";
		shortText="Lorem";
	}

	@Test
	public void testCenter() {
		assertTrue(Formatter.center(longText, shortText.length()).equals(longText));
		assertTrue(Formatter.center(shortText, shortText.length()).equals(shortText));
		assertTrue(Formatter.center(longText, longText.length()).equals(longText));
		assertTrue(Formatter.center(shortText, longText.length()).
				equals("        "+shortText+"        "));
		assertTrue(Formatter.center(longText, longText.length()+shortText.length()).
				equals("  "+longText+"   "));
				
		assertTrue(Formatter.center(shortText, longText.length()+shortText.length()).
				equals("          "+shortText+"           "));
		assertTrue(Formatter.center(longText, 1+longText.length()+shortText.length()).
				equals("   "+longText+"   "));
		assertTrue(Formatter.center(shortText, 1+longText.length()+shortText.length()).
				equals("           "+shortText+"           "));
		assertTrue(Formatter.center(shortText, longText.length()).length()==
			Formatter.center(longText, longText.length()).length());
			
	}

	@Test
	public void testLeft() {
		assertTrue(Formatter.left(longText, shortText.length()).
				equals(longText));
		assertTrue(Formatter.left(shortText, shortText.length()).
				equals(shortText));
		assertTrue(Formatter.left(longText, longText.length()).
				equals(longText));
		assertTrue(Formatter.left(shortText, longText.length()+shortText.length()).
				equals(shortText+"                     "));
		assertTrue(Formatter.left(longText, longText.length()+shortText.length()).
				equals(longText+"     "));
		assertTrue(Formatter.left(shortText, longText.length()).
				equals(shortText+"                "));
	}

	@Test
	public void testRight() {
		assertTrue(Formatter.right(longText, shortText.length()).
				equals(longText));
		assertTrue(Formatter.right(shortText, shortText.length()).
				equals(shortText));
		assertTrue(Formatter.right(longText, longText.length()).
				equals(longText));
		assertTrue(Formatter.right(shortText, longText.length()+shortText.length()).
				equals("                     "+shortText));
		assertTrue(Formatter.right(longText, longText.length()+shortText.length()).
				equals("     "+longText));
		assertTrue(Formatter.right(shortText, longText.length()).
				equals("                "+shortText));
	}

}
