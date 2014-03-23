package progen.output.plugins;

import org.junit.Before;
import org.junit.Test;
import progen.output.plugins.Best;

import java.util.Random;

import static org.junit.Assert.*;

public class BestTest {

  private Best best;

  private Integer elMejor;

  @Before
  public void setUp() {

    best = new Best();
    elMejor = Integer.valueOf(2);

    best.addValue(Integer.valueOf(elMejor));
    for (int i = 0; i < 100; i++) {
      best.addValue(Integer.valueOf((int) (elMejor + Math.random() * 100)));
    }
  }

  @Test
  public void testAddValue() {
    for (int i = 0; i < 30; i++) {
      Integer integer = Integer.valueOf(new Random().nextInt() * 100);
      best.addValue(integer);
    }
  }

  @Test
  public void testGetValue() {
    Integer mejor = (Integer) best.getValue();
    assertTrue(mejor.intValue() == elMejor);
  }

  @Test
  public void testGetValueFail() {
    Integer mejor = (Integer) best.getValue();
    assertFalse(mejor.intValue() == 0);
  }

  @Test
  public void testGetName() {
    assertEquals(best.getName(), "best");
  }

}
