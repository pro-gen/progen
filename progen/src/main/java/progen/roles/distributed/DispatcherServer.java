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
  public static final int UNASIGNED_TASKS = -10;

  private static final int DEFAULT_DISPATCHER_SERVER_URL_LENGTH = 32;

  private static final int ID_INFO = 3;

  private static final String SLASH_SYMBOL = "/";

  private static final String COLON_SYMBOL = ":";

  private static final String PROGEN_DISPATCHER_NAME_PROPERTY = "progen.dispatcher.name";

  private static final String PROGEN_ROLE_DISPATCHER_BIND_ADDRESS_PROPERTY = "progen.role.dispatcher.bindAddress";

  private static final String PROGEN_ROLE_DISPATCHER_PORT_PROPERTY = "progen.role.dispatcher.port";

  private static final long serialVersionUID = 1486658118278870720L;

  private int port;

  private String bindAddress;

  private Registry registry;

  protected DispatcherServer() throws RemoteException {
    super();
    port = ProGenContext.getOptionalProperty(PROGEN_ROLE_DISPATCHER_PORT_PROPERTY, Registry.REGISTRY_PORT);
    bindAddress = getDefaultBindAddress();
    bindAddress = ProGenContext.getOptionalProperty(PROGEN_ROLE_DISPATCHER_BIND_ADDRESS_PROPERTY, bindAddress);
    ProGenContext.setProperty(PROGEN_ROLE_DISPATCHER_BIND_ADDRESS_PROPERTY, bindAddress);
  }

  private String getDefaultBindAddress() {
    String address = "127.0.0.1";
    try {
      final InetAddress addr = InetAddress.getLocalHost();
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
      registry.bind(ProGenContext.getMandatoryProperty(PROGEN_DISPATCHER_NAME_PROPERTY), this);

      for (String remoteService : registry.list()) {
        Info.show(ID_INFO, bindAddress + COLON_SYMBOL + port + SLASH_SYMBOL + remoteService);
      }

    } catch (RemoteException | AlreadyBoundException e) {
      throw new ProGenDistributedException(e.getLocalizedMessage());
    }

  }

  private void defineDispatcherName() {
    String defaultName = DispatcherDistributed.DISPATCHER_NAME;
    defaultName = ProGenContext.getOptionalProperty("progen.role.dispatcher.name", defaultName);
    ProGenContext.setProperty(PROGEN_DISPATCHER_NAME_PROPERTY, defaultName);

  }

  @Override
  public String toString() {
    final StringBuilder dispatcherServer = new StringBuilder(DEFAULT_DISPATCHER_SERVER_URL_LENGTH);
    dispatcherServer.append("rmi://");
    dispatcherServer.append(ProGenContext.getMandatoryProperty(PROGEN_ROLE_DISPATCHER_BIND_ADDRESS_PROPERTY));
    dispatcherServer.append(COLON_SYMBOL);
    dispatcherServer.append(ProGenContext.getMandatoryProperty(PROGEN_ROLE_DISPATCHER_PORT_PROPERTY));
    dispatcherServer.append(SLASH_SYMBOL);
    dispatcherServer.append(DispatcherDistributed.DISPATCHER_NAME);
    return dispatcherServer.toString();
  }

}
