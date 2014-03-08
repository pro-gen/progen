package test.progen.roles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import progen.context.ProGenContext;
import progen.roles.FactoryNotFoundException;
import progen.roles.ProGenFactory;
import progen.roles.distributed.DistributedFactory;
import progen.roles.standalone.StandaloneFactory;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class ProGenFactoryTest {

  @Before
  public void setUp() {
    ProGenContext.makeInstance();
  }

  @After
  public void tearDown() {
    ProGenContext.clearContext();
    ProGenFactory factory = ProGenFactory.makeInstance();
    Field factoryField;
    try {
      factoryField = ProGenFactory.class.getDeclaredField("factory");
      factoryField.setAccessible(true);
      factoryField.set(factory, null);
    } catch (SecurityException e) {
      fail(e.getMessage());
    } catch (IllegalArgumentException e) {
      fail(e.getMessage());
    } catch (IllegalAccessException e) {
      fail(e.getMessage());
    } catch (NoSuchFieldException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testMakeInstanceDefault() {
    ProGenFactory factory = ProGenFactory.makeInstance();
    assertNotNull(factory);
    assertTrue(factory instanceof StandaloneFactory);
  }

  @Test
  public void testMakeInstanceStandAlone() {
    ProGenContext.setProperty("progen.role.factory", "standalone");
    ProGenFactory factory = ProGenFactory.makeInstance();
    assertNotNull(factory);
    assertTrue(factory instanceof StandaloneFactory);
  }

  @Test
  public void testMakeInstanceDistributed() {
    ProGenContext.setProperty("progen.roles.factory", "distributed");
    ProGenFactory factory = ProGenFactory.makeInstance();
    assertNotNull(factory);
    assertTrue(factory instanceof DistributedFactory);
  }

  @Test(expected = FactoryNotFoundException.class)
  public void testMakeInstanceUnkown() {
    ProGenContext.setProperty("progen.roles.factory", "other");
    ProGenFactory factory = ProGenFactory.makeInstance();
  }

}
