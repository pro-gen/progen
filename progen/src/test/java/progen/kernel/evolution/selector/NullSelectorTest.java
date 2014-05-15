package progen.kernel.evolution.selector;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import progen.context.ProGenContext;

public class NullSelectorTest {

  private NullSelector selector;

  @Before
  public void setUp() throws Exception {
    selector = new NullSelector();
  }

  @Test
  public void testSelect() {
    assertTrue(selector.select(null, 0).size() == 0);
  }

  @Test@Ignore
  public void testMakeSelector() {
    Map<String, String> params = ProGenContext.getParameters("default.params");
    Selector nullSelector = Selector.makeSelector("NullSelector", params);

    assertTrue(nullSelector instanceof NullSelector);
    assertTrue(nullSelector.select(null, 0).size() == 0);
  }

}
