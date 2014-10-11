package progen.roles.distributed;

import java.util.Locale;

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

  @Override
  public Client makeClient() {
    final String roleClass = ProGenContext.getOptionalProperty("progen.role.client.class", "ClientDistributed");
    return (Client) loadRole(roleClass);
  }

  @Override
  public Dispatcher makeDispatcher() {
    final String roleClass = ProGenContext.getOptionalProperty("progen.role.dispatcher.class", "DispatcherDistributed");
    return (Dispatcher) loadRole(roleClass);
  }

  @Override
  public Worker makeWorker() {
    final String roleClass = ProGenContext.getOptionalProperty("progen.role.worker.class", "WorkerDistributed");
    return (Worker) loadRole(roleClass);
  }

  @Override
  public ExecutionRole makeExecutionRole() {
    ExecutionRole exec = null;
    final String name = ProGenContext.getMandatoryProperty("progen.role");
    try {
      final Role executionRole = Role.valueOf(name.toUpperCase(Locale.getDefault()));
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
        default:
          throw new UnknownRoleException(executionRole.name());
      }
    } catch (IllegalArgumentException e) {
      throw new UnknownRoleException(name, e);
    }
    return exec;
  }

  private Object loadRole(String clazz) {
    Object role = null;
    final String roleName = "progen.roles.distributed." + clazz;
    try {
      role = Class.forName(roleName).newInstance();
    } catch (ClassNotFoundException e) {
      throw new UnknownRoleImplementationException(roleName, e);
    } catch (InstantiationException e) {
      throw new UnknownRoleImplementationException(roleName, e);
    } catch (IllegalAccessException e) {
      throw new UnknownRoleImplementationException(roleName, e);
    }
    return role;
  }

}