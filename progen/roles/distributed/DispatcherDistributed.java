/**
 * 
 */
package progen.roles.distributed;

import java.rmi.RemoteException;
import java.util.List;

import progen.roles.Dispatcher;
import progen.roles.Task;
import progen.roles.Worker;
import progen.userprogram.UserProgram;

/**
 * @author jirsis
 *
 */
public class DispatcherDistributed implements Dispatcher {

    public static final String DISPATCHER_NAME="ProGen/dispatcher";
    
    private DispatcherRemote remote;
    
    public DispatcherDistributed() throws RemoteException{
	remote = new DispatcherServer();
    }
    
    public DispatcherDistributed(DispatcherRemote remote){
	this.remote=remote;
    }
 
    @Override
    public void start(){
	try {
	    remote.start();
	} catch (RemoteException e) {
	    throw new ProGenDistributedException();
	}
    }

    public void addWorker(Worker worker) {
	System.err.println("addWorker NOT IMPLEMENTED YET");
    }

    public void asignTask(List<Task> tasks, UserProgram userProgram) {
	System.err.println("assignTask NOT IMPLEMENTED YET");

    }

    public List<Task> getResults() {
	System.err.println("getResults NOT IMPLEMENTED YET");
	return null;
    }

    public void stopTask() {
	System.err.println("stopTask NOT IMPLEMENTED YET");
    }

    public int totalTasksDone() {
	try {
	    return remote.totalTasksDone();
	} catch (RemoteException e) {
	    throw new ProGenDistributedException(remote.toString());
	}
    }    
}
