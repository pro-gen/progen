package test.progen.kernel.tree;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Clase que ejecuta todo el conjunto de teste del paquete correspondiente.
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  FullTest.class,
  GrowTest.class,
  NodeTest.class,
  TreeTest.class
})
public class TreeAllTests {}