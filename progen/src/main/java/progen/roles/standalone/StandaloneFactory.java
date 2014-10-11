package progen.roles.standalone;

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
 * Fábrica concreta en la que todos los roles que se pueden generar, son de tipo
 * Local, es decir, todos los componentes interactuan a través de los mecanismos
 * habituales de las llamadas a métodos del lenguaje.
 * 
 * @author jirsis
 * @since 2.0
 */
public class StandaloneFactory extends ProGenFactory {

  @Override
  public Client makeClient() {
    final String roleClass = ProGenContext.getOptionalProperty("progen.role.client.class", "ClientLocal");

    return (Client) loadRole(roleClass);
  }

  @Override
  public Dispatcher makeDispatcher() {
    final String roleClass = ProGenContext.getOptionalProperty("progen.role.dispatcher.class", "DispatcherLocal");
    return (Dispatcher) loadRole(roleClass);
  }

  @Override
  public Worker makeWorker() {
    final String roleClass = ProGenContext.getOptionalProperty("progen.role.worker.class", "WorkerLocal");
    return (Worker) loadRole(roleClass);
  }

  private Object loadRole(String clazz) {
    Object role = null;
    final String roleName = "progen.roles.standalone." + clazz;
    try {
      role = Class.forName(roleName).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      throw new UnknownRoleImplementationException(roleName, e);
    }
    return role;
  }

  @Override
  public ExecutionRole makeExecutionRole() {
    ExecutionRole exec = null;
    final String element = ProGenContext.getOptionalProperty("progen.role", Role.CLIENT.name());
    try {
      final Role executionRole = Role.valueOf(element.toUpperCase(Locale.getDefault()));
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
      throw new UnknownRoleException(element, e);
    }
    return exec;
  }

}
