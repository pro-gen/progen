/**
 * 
 */
package progen.roles.distributed;

import progen.context.ProGenContext;
import progen.roles.Client;
import progen.roles.Dispatcher;
import progen.roles.ExecutionRole;
import progen.roles.ProGenFactory;
import progen.roles.Role;
import progen.roles.UnknownRoleException;
import progen.roles.UnknownRoleImplementationException;
import progen.roles.Worker;

/**
 * @author jirsis
 *
 */
public class DistributedFactory extends ProGenFactory {

    /* (non-Javadoc)
     * @see progen.roles.ProGenFactory#makeClient()
     */
    @Override
    public Client makeClient() {
	String roleClass = ProGenContext.getOptionalProperty("progen.role.client.class", "ClientDistributed");

	return (Client)loadRole(roleClass);
    }

    /* (non-Javadoc)
     * @see progen.roles.ProGenFactory#makeDispatcher()
     */
    @Override
    public Dispatcher makeDispatcher() {
	String roleClass = ProGenContext.getOptionalProperty("progen.role.dispatcher.class", "DispatcherDistributed");
	Dispatcher dispatcher = (Dispatcher)loadRole(roleClass);
	
	return dispatcher;
    }

    /* (non-Javadoc)
     * @see progen.roles.ProGenFactory#makeWorker()
     */
    @Override
    public Worker makeWorker() {
	String roleClass = ProGenContext.getOptionalProperty("progen.role.worker.class", "WorkerDistributed");

	return (Worker)loadRole(roleClass);
    }

    @Override
    public ExecutionRole makeExecutionRole() {
	ExecutionRole exec = null;
	String name = ProGenContext.getMandatoryProperty("progen.role");
	try{
	    Role executionRole = Role.valueOf(name.toUpperCase());
	    switch (executionRole) {
	    case CLIENT:
		exec = this.makeClient();
		break;
	    case DISPATCHER:
		exec = this.makeDispatcher();
		break;
	    case WORKER:
		exec = this.makeWorker();
		break;
	    }
	}catch(IllegalArgumentException e){
	    throw new UnknownRoleException(name);
	}
	return exec;
    }

    private Object loadRole(String clazz){
	Object role=null;
	String roleName = "progen.roles.distributed." + clazz;
	try {
	    role = Class.forName(roleName).newInstance();
	} catch (ClassNotFoundException e) {
	    throw new UnknownRoleImplementationException(roleName);
	} catch (InstantiationException e) {
	    throw new UnknownRoleImplementationException(roleName);
	} catch (IllegalAccessException e) {
	    throw new UnknownRoleImplementationException(roleName);
	}
	return role;
    }

}
