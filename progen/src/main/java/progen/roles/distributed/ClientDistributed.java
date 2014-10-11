package progen.roles.distributed;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import progen.context.ProGenContext;
import progen.roles.Dispatcher;
import progen.roles.standalone.ClientLocal;

/**
 * @author jirsis
 * 
 */
public class ClientDistributed extends ClientLocal {

  private static final int DISPATCHER_ADDRESS_DEFAULT_SIZE = 32;

  public ClientDistributed() {
    super();
  }

  @Override
  public void start() {
    super.start();
  }

  @Override
  public Dispatcher initDispatcher() {
    DispatcherDistributed dispatcher = null;
    try {
      final DispatcherRemote remote = (DispatcherRemote) Naming.lookup(getDispatcherAddress());
      dispatcher = new DispatcherDistributed(remote);
    } catch (MalformedURLException e) {
      throw new ProGenDistributedException(getDispatcherAddress(), e);
    } catch (RemoteException e) {
      throw new ProGenDistributedException(getDispatcherAddress(), e);
    } catch (NotBoundException e) {
      throw new ProGenDistributedException(getDispatcherAddress(), e);
    }
    return dispatcher;
  }

  private String getDispatcherAddress() {
    final StringBuilder dispatcherAddress = new StringBuilder(DISPATCHER_ADDRESS_DEFAULT_SIZE);
    dispatcherAddress.append("rmi://");
    dispatcherAddress.append(ProGenContext.getOptionalProperty("progen.role.client.dispatcher.bindAddress", "127.0.0.1"));
    dispatcherAddress.append(":");
    dispatcherAddress.append(ProGenContext.getOptionalProperty("progen.role.client.dispatcher.port", Registry.REGISTRY_PORT));
    dispatcherAddress.append("/");
    dispatcherAddress.append(ProGenContext.getOptionalProperty("progen.role.client.dispatcher.name", DispatcherDistributed.DISPATCHER_NAME));
    return dispatcherAddress.toString();
  }
}
