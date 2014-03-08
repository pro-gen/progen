package test.progen.experimenter.property.condition;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import progen.experimenter.property.condition.LessEqualThanLoopCondition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LessEqualThanLoopConditionTest {

  private LessEqualThanLoopCondition condition;

  @Before
  public void setUp() throws Exception {
    condition = new LessEqualThanLoopCondition(1);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testEnd() {
    int i;
    for (i = 0; i < 100; i = i + 2) {
      assertFalse(i + "", condition.end(i, 100));
    }
    assertTrue(condition.end(i, 100));
  }

  @Test
  public void testPercent() {
    int i = 0;
    condition = new LessEqualThanLoopCondition(10);
    for (i = 0; i < 100; i = i + 10) {
      assertFalse(i + "", condition.end(i, 100));
    }

    assertTrue(condition.end(i, 100));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailZero() {
    new LessEqualThanLoopCondition(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailPositive() {
    new LessEqualThanLoopCondition(-1);
  }
}
