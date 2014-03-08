package test.progen.kernel.evolution.selector;

import org.junit.Before;
import org.junit.Test;
import progen.context.ProGenContext;
import progen.kernel.error.Error;
import progen.kernel.evolution.selector.RandomSelector;
import progen.kernel.evolution.selector.Selector;
import progen.kernel.evolution.selector.UnknownSelectorException;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class SelectorTest {

  @Before
  public void setup() {
    ProGenContext.makeInstance();
    Error.makeInstance();
  }

  @Test(expected = UnknownSelectorException.class)
  public void testMakeSelectorFail() {
    Map<String, String> params = ProGenContext.getParameters("default.params");
    Selector.makeSelector("UnknownSelector", params);
  }

  @Test
  public void testMakeSelectorOk() {
    Map<String, String> params = ProGenContext.getParameters("default.params");
    Selector selector = Selector.makeSelector("RandomSelector", params);
    assertTrue(selector instanceof RandomSelector);
  }

}
