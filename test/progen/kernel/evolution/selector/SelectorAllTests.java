package test.progen.kernel.evolution.selector;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Clase que ejecuta todo el conjunto de teste del paquete correspondiente.
 * @author jirsis
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  NullSelectorTest.class,
  RandomSelectorTest.class,
  RouletteTest.class,
  SelectorTest.class,
  TournamentTest.class
})
public class SelectorAllTests {}
