package progen.roles;

/**
 * Enumeracion de los roles de ejecución disponibles.
 * 
 * @author jirsis
 * @since 2.0
 */
public enum Role {
  /** Constante que identifica el rol de CLIENTE */
  CLIENT("client"),
  /** Constante que identifica el rol de DISPATCHER */
  DISPATCHER("dispatcher"),
  /** Constante que identifica el rol de WORKER */
  WORKER("worker");

  private String role;

  Role(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return role;
  }
}