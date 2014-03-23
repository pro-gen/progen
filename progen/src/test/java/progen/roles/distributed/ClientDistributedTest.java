package progen.roles.distributed;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import progen.context.ProGenContext;
import progen.roles.Client;
import progen.roles.Dispatcher;
import progen.roles.ProGenFactory;
import progen.roles.UnknownRoleImplementationException;
import progen.roles.distributed.DispatcherDistributed;
import progen.roles.distributed.DispatcherServer;
import progen.roles.distributed.ProGenDistributedException;

import java.lang.reflect.Field;
import java.rmi.registry.Registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ClientDistributedTest {

  private Client client;

  @Before
  public void setUp() throws Exception {
    ProGenContext.makeInstance();
    ProGenContext.setProperty("progen.experiment.file", "app.regression.Regression.txt");
    ProGenContext.setProperty("progen.roles.factory", "distributed");
    ProGenContext.setProperty("progen.role", "client");
    ProGenContext.setProperty("progen.role.client.class", "ClientDistributed");
    ProGenContext.setProperty("progen.role.client.dispatcher.bindAddress", "thonolan.local");
    ProGenContext.setProperty("progen.role.client.dispatcher.port", Registry.REGISTRY_PORT + "");
    ProGenContext.setProperty("progen.role.client.dispatcher.name", DispatcherDistributed.DISPATCHER_NAME);
    client = ProGenFactory.makeInstance().makeClient();
  }

  @After
  public void tearDown() throws Exception {
    ProGenContext.clearContext();
    clearSingleton();
  }


  private void clearSingleton() {
    Field factoryField;
    try {
      factoryField = ProGenFactory.class.getDeclaredField("factory");
      factoryField.setAccessible(true);
      factoryField.set(ProGenFactory.makeInstance(), null);
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
  public void testInitDispatcher() {
    Dispatcher dispatcher = client.initDispatcher();
    assertEquals(dispatcher.totalTasksDone(), DispatcherServer.UNASIGNED_TASKS);
  }

  @Test
  public void testInitDispatcherDefault() {
    ProGenContext.clearContext();
    ProGenContext.setProperty("progen.experiment.file", "app.regression.Regression.txt");
    ProGenContext.setProperty("progen.roles.factory", "distributed");
    ProGenContext.setProperty("progen.role", "client");

    client = ProGenFactory.makeInstance().makeClient();
    Dispatcher dispatcher = client.initDispatcher();
    assertEquals(dispatcher.totalTasksDone(), DispatcherServer.UNASIGNED_TASKS);
  }

  @Test(expected = UnknownRoleImplementationException.class)
  public void testCreateClientUnknownClass() {
    ProGenContext.setProperty("progen.role.client.class", "ClientUnknown");
    client = ProGenFactory.makeInstance().makeClient();
  }

  @Test(expected = ProGenDistributedException.class)
  public void testInitDispatcherFailBindAddress() {
    ProGenContext.setProperty("progen.role.client.dispatcher.bindAddress", "unknown-bind-address");
    client.initDispatcher();
  }

  @Test(expected = ProGenDistributedException.class)
  public void testInitDispatcherFailPort() {
    ProGenContext.setProperty("progen.role.client.dispatcher.port", "32000");
    client.initDispatcher();
  }

  @Test(expected = ProGenDistributedException.class)
  public void testInitDispatcherFailName() {
    ProGenContext.setProperty("progen.role.client.dispatcher.name", "unknown-dispatcher-name");
    client.initDispatcher();
  }

}
