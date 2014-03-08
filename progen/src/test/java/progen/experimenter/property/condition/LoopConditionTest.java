package test.progen.experimenter.property.condition;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import progen.experimenter.property.condition.GratherEqualThanLoopCondition;
import progen.experimenter.property.condition.LessEqualThanLoopCondition;
import progen.experimenter.property.condition.LoopCondition;

import static org.junit.Assert.assertTrue;

public class LoopConditionTest {

  private LoopCondition grather;
  private LoopCondition less;


  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testMakeInstance() {

    less = LoopCondition.makeInstance(0, 100, 1);
    grather = LoopCondition.makeInstance(100, 0, -1);

    assertTrue(less instanceof LessEqualThanLoopCondition);
    assertTrue(grather instanceof GratherEqualThanLoopCondition);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessFailIncrement() {
    LoopCondition.makeInstance(0, 100, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGratherFailIncrement() {
    LoopCondition.makeInstance(100, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailIncrement() {
    LoopCondition.makeInstance(0, 100, 0);
  }
}
