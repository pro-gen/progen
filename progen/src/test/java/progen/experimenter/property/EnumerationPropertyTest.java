package progen.experimenter.property;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import progen.context.ProGenContext;
import progen.experimenter.property.EnumerationProperty;
import progen.experimenter.property.IlegalPropertySeparatorException;

import static org.junit.Assert.*;

public class EnumerationPropertyTest {

  private EnumerationProperty property;

  private final String label = "progen.experimenter.enumeration";

  @Before
  public void setUp() throws Exception {

    ProGenContext.makeInstance();
    ProGenContext.setProperty(label, "    uno | dos  |    tres|cuatro|cinco   ");
    ProGenContext.setProperty("fail.progen.bad-property", "1;2;3");
    property = new EnumerationProperty(label);
  }

  @After
  public void tearDown() throws Exception {
    ProGenContext.clearContext();
  }

  @Test(expected = IlegalPropertySeparatorException.class)
  public void testEnumerationProperty() {
    EnumerationProperty propertyTest = new EnumerationProperty("progen.experimenter.enumeration");
    assertEquals("progen.enumeration", propertyTest.getLabel());
    assertEquals("uno", propertyTest.getValue());
    new EnumerationProperty("fail.progen.bad-property");
  }

  @Test
  public void testGetLabel() {
    assertEquals("progen.enumeration", property.getLabel());
  }

  @Test
  public void testGetValue() {
    assertEquals("uno", property.getValue());
    property.nextValue();
    assertEquals("dos", property.getValue());
    property.nextValue();
    assertEquals("tres", property.getValue());
    property.nextValue();
    assertEquals("cuatro", property.getValue());
    property.nextValue();
    assertEquals("cinco", property.getValue());
    property.nextValue();
    assertNull(property.nextValue());
  }

  @Test
  public void testHasNext() {
    for (int i = 0; i < 4; i++) {
      assertTrue(property.hasNext());
      property.nextValue();
    }
    assertFalse(property.hasNext());
    property.nextValue();
    assertFalse(property.hasNext());
  }

  @Test
  public void testNextValue() {
    assertEquals("dos", property.nextValue());
    assertEquals("tres", property.nextValue());
    assertEquals("cuatro", property.nextValue());
    assertEquals("cinco", property.nextValue());
    assertNull(property.nextValue());
    assertNull(property.nextValue());
  }

  @Test
  public void testReset() {
    int rnd = (int) (Math.random() * 5);
    for (int i = 0; i < rnd; i++) {
      property.nextValue();
    }

    property.reset();
    assertEquals("uno", property.getValue());
  }

  @Test(expected = IlegalPropertySeparatorException.class)
  public void testVoidProperty() {
    ProGenContext.setProperty(label, "");
    property = new EnumerationProperty(label);
  }

  @Test
  public void twoValues() {
    ProGenContext.setProperty(label, "a|b");
    property = new EnumerationProperty(label);
    int values = 1;
    while (property.hasNext()) {
      values++;
      property.nextValue();
    }
    assertEquals(2, values);
    assertFalse(property.hasNext());
  }
}
