package progen.roles.distributed;

import progen.roles.Task;
import progen.roles.Worker;
import progen.userprogram.UserProgram;

/**
 * @author jirsis
 * 
 */
public class WorkerDistributed implements Worker {

  public void start() {
    System.err.println("NOT IMPLEMENTED YET");

  }

  public void calculate(Task task, UserProgram userprogram) {
    System.err.println("NOT IMPLEMENTED YET");

  }

  public String getId() {
    System.err.println("NOT IMPLEMENTED YET");
    return null;
  }

  public int getPort() {
    System.err.println("NOT IMPLEMENTED YET");
    return 0;
  }

  public Task getTask() {
    System.err.println("NOT IMPLEMENTED YET");
    return null;
  }

}
