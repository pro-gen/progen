package test.progen.roles.distributed;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.roles.Client;
import progen.roles.Dispatcher;
import progen.roles.ExecutionRole;
import progen.roles.ProGenFactory;
import progen.roles.UnknownRoleException;
import progen.roles.UnknownRoleImplementationException;
import progen.roles.Worker;
import progen.roles.distributed.ClientDistributed;
import progen.roles.distributed.DispatcherDistributed;
import progen.roles.distributed.WorkerDistributed;

public class DistributedFactoryTest {

    private ProGenFactory factory;
    
    @Before
    public void setUp() throws Exception {
	ProGenContext.makeInstance();
	ProGenContext.setProperty("progen.total.operators", "1");
	ProGenContext.setProperty("progen.gennetic.operator0.class", "PointMutation");
	ProGenContext.setProperty("progen.gennetic.operator0.selector", "RandomSelector");
	ProGenContext.setProperty("progen.max-generation", "0");
	
	ProGenContext.setProperty("progen.roles.factory", "distributed");
	
	factory= ProGenFactory.makeInstance();
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
    public void testMakeClient() {
	Client client = factory.makeClient();
	assertTrue(client instanceof ClientDistributed);
    }
    
    @Test(expected=UnknownRoleImplementationException.class)
    public void testMakeClientUnknown() {
	ProGenContext.setProperty("progen.role.client.class", "unknown-class");
	Client client = factory.makeClient();
    }

    @Test
    public void testMakeDispatcher() {
	Dispatcher dispatcher = factory.makeDispatcher();
	assertTrue(dispatcher instanceof DispatcherDistributed);
    }

    @Test(expected=UnknownRoleImplementationException.class)
    public void testMakeDispatcherUnknown() {
	ProGenContext.setProperty("progen.role.dispatcher.class", "unknown-class");
	Dispatcher dispatcher = factory.makeDispatcher();
    }
    
    @Test
    public void testMakeDefaultWorker() {
	Worker worker = factory.makeWorker();
	assertTrue(worker instanceof WorkerDistributed);
    }

    @Test(expected=UnknownRoleImplementationException.class)
    public void testMakeWorkerUnknown() {
	ProGenContext.setProperty("progen.role.worker.class", "unknown-class");
	Worker worker = factory.makeWorker();
    }
    
    @Test
    public void testMakeExecutionRoleClient() {
	ProGenContext.setProperty("progen.role", "client");
	ExecutionRole defaultRole = factory.makeExecutionRole();
	assertTrue(defaultRole instanceof ClientDistributed);
    }
    
    @Test
    public void testMakeExecutionRoleDispatcher() {
	ProGenContext.setProperty("progen.role", "dispatcher");
	ExecutionRole defaultRole = factory.makeExecutionRole();
	assertTrue(defaultRole instanceof DispatcherDistributed);
    }

    @Test
    public void testMakeExecutionRoleWorker() {
	ProGenContext.setProperty("progen.role", "worker");
	ExecutionRole defaultRole = factory.makeExecutionRole();
	assertTrue(defaultRole instanceof WorkerDistributed);
    }
    
    @Test(expected=UnknownRoleException.class)
    public void testMakeExecutionUnkownRole() {
	ProGenContext.setProperty("progen.role", "unkown-role");
	ExecutionRole defaultRole = factory.makeExecutionRole();
    }
    
}
