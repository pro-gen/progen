package progen.output.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import progen.context.ProGenContext;
import progen.kernel.evolution.GenneticOperatorException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProGenContext.class })
public class TimePluginTest {

  private TimePlugin plugin;
  private Plugin testInnerPlugin;
  
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    plugin = new TimePlugin("timePluginTest");
    testInnerPlugin = new TestPlugin();
    plugin.addPlugin(null, testInnerPlugin);
  }

  @Test
  public void testTimePlugin() {
    plugin = new TimePlugin("test");
    assertNotNull(plugin);
    assertEquals("test", plugin.getName());
    assertNotNull(plugin.getPlugins());
    assertEquals(0, plugin.getPlugins().size());
  }

  @Test
  public void testAddValue() {
    plugin.addValue(Integer.MAX_VALUE);
    assertEquals(Integer.MAX_VALUE, testInnerPlugin.getValue());
  }

  @Test
  public void testGetValue() {
    assertEquals(1, plugin.getValue().size());
  }
  
  @Test
  public void testGetValueWithInnerPlugins() {
    Plugin testPlugin = spy(new TestPlugin());
    plugin.addPlugin(null, testPlugin);
    plugin.addValue(Integer.MAX_VALUE);
    HashMap<String, Object> values = plugin.getValue();
    assertEquals(1, values.size());
    assertEquals(Integer.MAX_VALUE, values.get("testPlugin"));
  }
  
  @Test
  public void testInitialValue() {
    plugin.initialValue();
    assertEquals(Integer.MIN_VALUE, testInnerPlugin.getValue());
  }

  @Test
  public void testGetName() {
    assertEquals("timePluginTest", plugin.getName());
  }

  @Test
  public void testGetPriority() {
    assertEquals(0, plugin.getPriority());
  }

  @Test
  public void testInitPlugin() {
    mockStatic(ProGenContext.class);
    when(ProGenContext.getOptionalProperty(any(String.class), any(String.class))).thenReturn("NullPlugin");
    plugin.initPlugin("defaultValues");
    assertEquals(2, plugin.getPlugins().size());
    
    plugin = new TimePlugin("otherTest");
    plugin.initPlugin("defaultValues");
    assertEquals(1, plugin.getPlugins().size());
  }
  
  @Test
  public void testGetPlugin() {
    Plugin foundIt = plugin.getPlugin("timePluginTest");
    assertNotNull(foundIt);
    assertTrue(foundIt instanceof TimePlugin);
  }

  @Test
  public void testGetPluginFamily() {
    mockStatic(ProGenContext.class);
    when(ProGenContext.getOptionalProperty(any(String.class), any(String.class))).thenReturn("NullPlugin");
    Plugin foundIt = plugin.getPlugin("NullPlugin");
    assertNotNull(foundIt);
    assertTrue(foundIt instanceof NullPlugin);
  }

  @Test
  public void testGetPluginDefault() {
    mockStatic(ProGenContext.class);
    when(ProGenContext.getOptionalProperty(any(String.class), any(String.class))).thenReturn("Mean");
    Plugin foundIt = plugin.getPlugin("OtherPlugin");
    assertNotNull(foundIt);
    assertTrue(foundIt instanceof NullPlugin);
  }

  @Test
  public void testCheckDependeces() {
    plugin.checkDependeces(any(List.class));
  }

  @Test
  public void testAddPlugin() {
    plugin = new TimePlugin("otherTest");
    int totalPlugins = plugin.getPlugins().size();
    assertEquals(0, totalPlugins);

    plugin.addPlugin(null, new TestPlugin());
    assertEquals(1, plugin.getPlugins().size());
    
    plugin.addPlugin(null, new TestPlugin());
    assertEquals(1, plugin.getPlugins().size());

  }

  @Test
  public void testGetPlugins() {
    List<Plugin> plugins = plugin.getPlugins();
    assertNotNull(plugins);
    assertEquals(1, plugins.size());
  }

  private static class TestPlugin implements Plugin {

    private Integer value;
    
    @Override
    public void addValue(Comparable value) {
      this.value =(Integer)value;  
    }

    @Override
    public Object getValue() {
      return value;
    }

    @Override
    public void initialValue() {
      value = Integer.MIN_VALUE;
    }

    @Override
    public int getPriority() {
      // do nothing in test case
      return 0;
    }

    @Override
    public String getName() {
      return "testPlugin";
    }

    @Override
    public void initPlugin(String propertyFamily) {
      // do nothing in test case
    }

    @Override
    public Plugin getPlugin(String name) {
      return this;
    }

    @Override
    public void checkDependeces(List<Plugin> pluginCollection) {
      // do nothing in test case
    }

  }

}
