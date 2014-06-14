package progen.roles.standalone;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.roles.Client;
import progen.roles.Dispatcher;
import progen.roles.ExecutionRole;
import progen.roles.ProGenFactory;
import progen.roles.UnknownRoleException;
import progen.roles.Worker;

public class StandAloneFactoryTest {

  private ProGenFactory factory;

  @Before
  public void setUp() throws Exception {
    ProGenContext.makeInstance();
    ProGenContext.setProperty("progen.total.operators", "1");
    ProGenContext.setProperty("progen.gennetic.operator0.class", "PointMutation");
    ProGenContext.setProperty("progen.gennetic.operator0.selector", "RandomSelector");
    ProGenContext.setProperty("progen.max-generation", "0");

    factory = StandaloneFactory.makeInstance();
  }

  @After
  public void tearDown() {
    ProGenContext.clearContext();
  }

  @Test@Ignore
  public void testMakeClient() {

    Client client = factory.makeClient();
    assertTrue(client instanceof ClientLocal);
  }

  @Test
  public void testMakeDispatcher() {
    Dispatcher dispatcher = factory.makeDispatcher();
    assertTrue(dispatcher instanceof DispatcherLocal);
  }

  @Test
  public void testMakeWorker() {
    Worker worker = factory.makeWorker();
    assertTrue(worker instanceof WorkerLocal);

  }

  @Test@Ignore
  public void testMakeExecutionRoleDefault() {
    ExecutionRole defaultRole = factory.makeExecutionRole();
    assertTrue(defaultRole instanceof ClientLocal);
  }

  @Test(expected = UnknownRoleException.class)
  public void testMakeExecutionRoleUnkown() {
    ProGenContext.setProperty("progen.role", "unknown-role");
    factory.makeExecutionRole();
  }

}
