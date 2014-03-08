package progen.roles.distributed;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import progen.roles.Task;
import progen.roles.Worker;
import progen.userprogram.UserProgram;

public interface DispatcherRemote extends Remote{
   
    public void asignTask(List<Task> tasks, UserProgram userProgram) throws RemoteException;
    
    public List<Task> getResults() throws RemoteException;
    
    public int totalTasksDone() throws RemoteException;
    
    public void stopTask() throws RemoteException;
    
    public void addWorker(Worker worker) throws RemoteException;
    
    public void start() throws RemoteException;
}
