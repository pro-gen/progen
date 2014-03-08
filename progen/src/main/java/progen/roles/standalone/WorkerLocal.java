/**
 * 
 */
package progen.roles.standalone;

import progen.roles.Task;
import progen.roles.Worker;
import progen.userprogram.UserProgram;

/**
 * @author jirsis
 *
 */
public class WorkerLocal implements Worker {

	private Task task;
	public void start() {
		//do nothing
	}

	public void calculate(Task task, UserProgram userprogram) {
		this.task=task;
		//TODO: revisar workerLocal
		task.calculate(userprogram);
	}

	public String getId() {		
		return this.getClass().getName();
	}

	public int getPort() {
		return 0;
	}

	public Task getTask() {
		return task;
	}

}
