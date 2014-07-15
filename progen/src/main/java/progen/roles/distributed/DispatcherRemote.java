package progen.roles.distributed;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import progen.roles.Task;
import progen.roles.Worker;
import progen.userprogram.UserProgram;

public interface DispatcherRemote extends Remote {

  void asignTask(List<Task> tasks, UserProgram userProgram) throws RemoteException;

  List<Task> getResults() throws RemoteException;

  int totalTasksDone() throws RemoteException;

  void stopTask() throws RemoteException;

  void addWorker(Worker worker) throws RemoteException;

  void start() throws RemoteException;
}
