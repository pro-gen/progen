package progen.kernel.grammar.validations;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.GrammarNotValidException;

public class SuperfluousProductionsTest {
  
  private SuperfluousProductions validator;
  
  private Grammar grammarTested;

  @Before
  public void setUp() throws Exception {
    validator = new SuperfluousProductions();
  }
  
  @After
  public void tearDown(){
    ProGenContext.clearContext();
  }
  
  @Test(expected=NullPointerException.class)
  public void grammarNullTest(){
    validator.validate(null);
  }

  @Test(expected=GrammarNotValidException.class)
  public void grammarNoValid(){
    mockProGenContextNoValidGrammar();
    validator.validate(new Grammar("treeKO"));
  }
  
  @Test
  public void testValid() {
    mockProGenContextValidGrammar();
    validator.validate(new Grammar("treeOK"));
  }
  
  private void mockProGenContextNoValidGrammar() {
    ProGenContext.makeInstance();
    ProGenContext.setProperty("progen.treeKO.functionSet", "1");
    ProGenContext.setProperty("progen.functionSet1.return", "int");
    ProGenContext.setProperty("progen.functionSet1", "IntMinus, IntPlus, IntN, IntPow, DoublePlus, Or");
  }
  private void mockProGenContextValidGrammar() {
    ProGenContext.makeInstance();
    ProGenContext.setProperty("progen.treeOK.functionSet", "0");
    ProGenContext.setProperty("progen.functionSet0.return", "int");
    ProGenContext.setProperty("progen.functionSet0", "IntMinus, IntPlus, IntN, IntPow, DoublePlus");
  }


}
