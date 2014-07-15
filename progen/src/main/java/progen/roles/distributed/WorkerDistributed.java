package progen.roles.distributed;

import progen.roles.Task;
import progen.roles.Worker;
import progen.userprogram.UserProgram;

/**
 * @author jirsis
 * 
 */
public class WorkerDistributed implements Worker {

  private static final String NOT_IMPLEMENTED_YET_LITERAL = "NOT IMPLEMENTED YET";

  public void start() {
    System.err.println(NOT_IMPLEMENTED_YET_LITERAL);

  }

  public void calculate(Task task, UserProgram userprogram) {
    System.err.println(NOT_IMPLEMENTED_YET_LITERAL);

  }

  public String getId() {
    System.err.println(NOT_IMPLEMENTED_YET_LITERAL);
    return null;
  }

  public int getPort() {
    System.err.println(NOT_IMPLEMENTED_YET_LITERAL);
    return 0;
  }

  public Task getTask() {
    System.err.println(NOT_IMPLEMENTED_YET_LITERAL);
    return null;
  }

}
