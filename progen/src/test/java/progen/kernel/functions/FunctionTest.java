package progen.kernel.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.context.UndefinedMandatoryPropertyException;
import progen.kernel.grammar.FunctionNotFoundException;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

public class FunctionTest {
  
  private Function function; 
  
  @Before
  public void setUp(){
    function = new FunctionSample();
  }
  
  @After
  public void tearDown(){
    ProGenContext.clearContext();
  }

  @Test
  public void testHashCode() {
    assertEquals(-1856345686, function.hashCode());
  }

  @Test
  public void testGetSignature() {
    assertEquals("S", function.getSignature());
    function = new FunctionComplexSample();
    assertEquals("A$$B$$C", function.getSignature());
  }

  @Test
  public void testGetArity() {
    assertEquals(0, function.getArity());
    function = new FunctionComplexSample();
    assertEquals(2, function.getArity());
  }

  @Test
  public void testGetSymbol() {
    assertEquals("SAMPLE", function.getSymbol());
  }

  @Test
  public void testSetSymbol() {
    assertEquals("SAMPLE", function.getSymbol());
    function.setSymbol("o");
    assertEquals("o", function.getSymbol());
  }

  @Test
  public void testGetReturnType() {
    assertEquals("S", function.getReturnType().toString());
    function = new FunctionComplexSample();
    assertEquals("A", function.getReturnType().toString());
  }

  @Test
  public void testToString() {
    assertEquals("SAMPLE", function.toString());
    assertEquals(function.getSymbol(), function.toString());
  }

  @Test
  public void testGetArgsType() {
    Object args [] = function.getArgsType();
    assertEquals(0, args.length);
    args = new FunctionComplexSample().getArgsType();
    assertEquals(2, args.length);
    assertEquals("B", args[0]);
    assertEquals("C", args[1]);
  }

  @Test
  public void testEqualsObject() {
    assertTrue(function.equals(function));
    assertFalse(function.equals(new FunctionComplexSample()));
    assertTrue(function.equals(new FunctionSample()));
    assertFalse(function.equals(null));
    assertFalse(function.equals(Integer.MIN_VALUE));
  }

  @Test
  public void testCompareTo() {
    assertEquals(0, function.compareTo(function));
    assertTrue(function.compareTo(new FunctionComplexSample())>0);
    assertTrue(new FunctionComplexSample().compareTo(function)<0);
  }

  @Test
  public void testIsCompatibleWith() {
    Function function1 = new FunctionSample();
    Function function2 = new FunctionComplexSample();
    assertTrue(function.isCompatibleWith(function));
    assertTrue(function.isCompatibleWith(function1));
    assertFalse(function.isCompatibleWith(function2));
    function = new FunctionComplexSample();
    assertTrue(function.isCompatibleWith(function));
    assertTrue(function.isCompatibleWith(function2));
    assertFalse(function.isCompatibleWith(function1));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testEvaluate() {
    assertEquals("sample", function.evaluate(any(List.class), any(UserProgram.class), any(HashMap.class)));
    function = new FunctionComplexSample();
    assertEquals("complexFunction", function.evaluate(any(List.class), any(UserProgram.class), any(HashMap.class)));
  }
  
  @Test
  public void testLoadADF(){
    ProGenContext.makeInstance();
    ProGenContext.setProperty("progen.ADF0.interface", "object$$object$$$object");
    function = Function.load("ADF0");
    assertEquals("ADF0", function.getSymbol());
    assertEquals(2, function.getArity());
  }
  
  @Test(expected=UndefinedMandatoryPropertyException.class)
  public void testLoadADFUndefinedFunctionSetException(){
    ProGenContext.makeInstance();
    function = Function.load("ADF0");
    fail("exception must be expected");
  }
  
  @Test
  public void testLoadKernelFunction(){
    ProGenContext.makeInstance();
    function = Function.load("And");
    assertEquals("&&", function.getSymbol());
    assertEquals(2, function.getArity());
    assertEquals("boolean", function.getReturnType().toString());
  }
  
  @Test
  public void testLoadUserFunction(){
    ProGenContext.makeInstance();
    ProGenContext.setProperty("progen.user.home", "userprogram.");
    function = Function.load("FunctionExample");
    assertEquals("TEST", function.getSymbol());
    assertEquals(2, function.getArity());
    assertEquals("boolean", function.getReturnType().toString());
  }
  
  @Test(expected=FunctionNotFoundException.class)
  public void testLoadNotFoundFunction(){
    ProGenContext.makeInstance();
    function = Function.load("NotFoundFunction");
    fail("exception must be thrown");
  }
  
  
  private class FunctionSample extends Function {

    private static final long serialVersionUID = 1673204109657604166L;

    public FunctionSample() {
      super("S", "SAMPLE");
    }

    @Override
    public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
      return "sample";
    }

  }
  
  private class FunctionComplexSample extends Function {

    private static final long serialVersionUID = 8494956432052087770L;

    public FunctionComplexSample() {
      super("A$$B$$C", "COMPLEX");
    }

    @Override
    public Object evaluate(List<Node> arguments, UserProgram userProgram, HashMap<String, Object> returnAddr) {
      return "complexFunction";
    }

  }

}
