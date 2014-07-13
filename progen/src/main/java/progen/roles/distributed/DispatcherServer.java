package progen.roles.distributed;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import progen.context.ProGenContext;
import progen.kernel.error.Info;
import progen.roles.Task;
import progen.roles.Worker;
import progen.userprogram.UserProgram;

public class DispatcherServer extends UnicastRemoteObject implements DispatcherRemote {

  private static final long serialVersionUID = 1486658118278870720L;
  
  public static final int UNASIGNED_TASKS = -10;

  private int port;

  private String bindAddress;

  private Registry registry;

  protected DispatcherServer() throws RemoteException {
    super();
    port = ProGenContext.getOptionalProperty("progen.role.dispatcher.port", Registry.REGISTRY_PORT);
    bindAddress = getDefaultBindAddress();
    bindAddress = ProGenContext.getOptionalProperty("progen.role.dispatcher.bindAddress", bindAddress);
    ProGenContext.setProperty("progen.role.dispatcher.bindAddress", bindAddress);
  }

  private String getDefaultBindAddress() {
    String address = "127.0.0.1";
    try {
      InetAddress addr = InetAddress.getLocalHost();
      address = addr.getHostName();
    } catch (UnknownHostException e) {
      // do nothing
    }
    return address;
  }

  @Override
  public void asignTask(List<Task> tasks, UserProgram userProgram) throws RemoteException {
    // TODO Auto-generated method stub

  }

  @Override
  public List<Task> getResults() throws RemoteException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int totalTasksDone() throws RemoteException {
    System.err.println("DispatcherServer: no total tasks done");
    return UNASIGNED_TASKS;
  }

  @Override
  public void stopTask() throws RemoteException {
    try {
      registry.unbind(DispatcherDistributed.DISPATCHER_NAME);
      UnicastRemoteObject.unexportObject(registry, true);
    } catch (AccessException e) {
      throw new ProGenDistributedException(e.getMessage());
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (NotBoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addWorker(Worker worker) throws RemoteException {
    // TODO Auto-generated method stub

  }

  @Override
  public void start() throws RemoteException {
    defineDispatcherName();
    try {
      System.getProperties().setProperty("java.rmi.server.hostname", bindAddress);
      registry = LocateRegistry.createRegistry(port);
      registry.bind(ProGenContext.getMandatoryProperty("progen.dispatcher.name"), this);

      for (String remoteService : registry.list()) {
        Info.show(3, bindAddress + ":" + port + "/" + remoteService);
      }

    } catch (AccessException e) {
      throw new ProGenDistributedException(e.getLocalizedMessage());
    } catch (RemoteException e) {
      throw new ProGenDistributedException(e.getLocalizedMessage());
    } catch (AlreadyBoundException e) {
      throw new ProGenDistributedException(e.getLocalizedMessage());
    }

  }

  private void defineDispatcherName() {
    String defaultName = DispatcherDistributed.DISPATCHER_NAME;
    defaultName = ProGenContext.getOptionalProperty("progen.role.dispatcher.name", defaultName);
    ProGenContext.setProperty("progen.dispatcher.name", defaultName);

  }

  @Override
  public String toString() {
    StringBuilder stb = new StringBuilder(32);
    stb.append("rmi://");
    stb.append(ProGenContext.getMandatoryProperty("progen.role.dispatcher.bindAddress"));
    stb.append(":");
    stb.append(ProGenContext.getMandatoryProperty("progen.role.dispatcher.port"));
    stb.append("/");
    stb.append(DispatcherDistributed.DISPATCHER_NAME);
    return stb.toString();
  }

}
