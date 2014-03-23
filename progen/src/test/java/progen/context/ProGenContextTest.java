package progen.context;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProGenContextTest {

  private ProGenContext proGenContext;

  @Before
  public void setUp() throws Exception {
    proGenContext = ProGenContext.makeInstance();
  }

  @Test
  public void testMakeInstance() {
    ProGenContext props = ProGenContext.makeInstance();
    assertEquals(proGenContext, props);
  }

  @Test
  public void testMakeInstanceFile(){
    ProGenContext props = ProGenContext.makeInstance("test.properties");
    assertNotNull(props);
    assertNotEquals(proGenContext, props);
    assertEquals("success", ProGenContext.getMandatoryProperty("property"));
  }

  @Test
  public void testGetOptionalPropertyStringInt() {
    int defaultValue = 9;
    int value = ProGenContext.getOptionalProperty("optional.int", defaultValue);
    assertEquals(defaultValue, value);
  }

  @Test
  public void testGetOptionalPropertyString() {
    String defaultValue = "default";
    String value = ProGenContext.getOptionalProperty("optional.string", defaultValue);
    assertEquals(defaultValue, value);
  }

  @Test
  public void testGetOptionalPropertyStringDouble() {
    double defaultValue = Math.E;
    double value = ProGenContext.getOptionalProperty("optional.double", defaultValue);
    assertEquals(defaultValue, value, 0.0d);
  }

  @Test(expected = UndefinedMandatoryPropertyException.class)
  public void testGetMandatoryProperty() {
    ProGenContext.getMandatoryProperty("mandatory.property");
    fail("Mandatory property not throws new Exception");
  }

  @Test
  public void testGetSuboptionPercent() {
    String propertyName = "optional.suboption.percent";
    double defaultPercent = 0.33;
    double value1 = 0.4;
    double value2 = 0.5;
    double value3 = 0.1;
    String propertyValue = String.format("valor(sub1=%f,sub2=%f,sub3=%f)", value1, value2, value3);
    
    double subOption;
    ProGenContext.setProperty(propertyName, propertyValue);
    subOption = ProGenContext.getSuboptionPercent(propertyName, "sub1", defaultPercent);
    assertEquals(subOption, value1, 0.0);
    subOption = ProGenContext.getSuboptionPercent(propertyName, "sub2", defaultPercent);
    assertEquals(subOption, value2, 0.0);
    subOption = ProGenContext.getSuboptionPercent(propertyName, "sub3", defaultPercent);
    assertEquals(subOption, value3, 0.0);
    subOption = ProGenContext.getSuboptionPercent(propertyName, "sub4", defaultPercent);
    assertEquals(subOption, defaultPercent, 0.0);
    
  }

  @Ignore@Test
  public void testGetParameter() {
    String option;
    String subOption1;
    String subOptionA;
    String optionNotDefined;

    option = ProGenContext.getOptionalProperty("option.suboptions", "");
    subOption1 = ProGenContext.getParameter("option.suboptions", "subopt1");
    subOptionA = ProGenContext.getParameter("option.suboptions", "suboptA");
    optionNotDefined = ProGenContext.getParameter("option.notDefined", "subopt1");

    assertTrue(option.compareTo("value") == 0);
    assertTrue(subOption1.compareTo("1") == 0);
    assertTrue(subOptionA.compareTo("A") == 0);
    assertNull(optionNotDefined);

  }

  @Ignore@Test
  public void testGetParameters() {
    Map<String, String> option1 = ProGenContext.getParameters("option.suboptions");
    assertTrue(option1.size() == 2);
    assertTrue(option1.containsKey("subopt1"));
    assertTrue(option1.containsKey("suboptA"));

    Map<String, String> option2 = ProGenContext.getParameters("option.suboptions.notExists");
    assertTrue(option2.size() == 0);

    ProGenContext.setProperty("option.without.params", "value");
    Map<String, String> option3 = ProGenContext.getParameters("option.without.params");
    assertTrue(option3.size() == 0);

  }


  @Ignore@Test
  public void testGetOptionalPercent() {
    String defaultPercent = "0.25";
    double percent = ProGenContext.getOptionalPercent("optional.percent",
        defaultPercent);
    assertEquals(percent, 0.87, 0.0);
    assertNotSame(percent, Double.parseDouble(defaultPercent));
    percent = ProGenContext.getOptionalPercent("undefined.percent",
        defaultPercent);
    assertEquals(percent, Double.parseDouble(defaultPercent), 0.001);
  }

  @Ignore@Test(expected = UndefinedMandatoryPropertyException.class)
  public void testGetMandatoryPercent() {
    double percent;
    percent = ProGenContext.getMandatoryPercent("mandatory.percent.string");
    assertTrue(new BigDecimal(percent).equals(new BigDecimal(0.99)));
    percent = ProGenContext.getMandatoryPercent("mandatory.percent.double");
    assertTrue(new BigDecimal(percent).equals(new BigDecimal(0.75)));
    ProGenContext.getMandatoryPercent("undefined.property");
  }

  @Ignore@Test
  public void testGetFamilyOptions() {
    List<String> properties = ProGenContext.getFamilyOptions("optional.");
    assertTrue(properties.size() == 4);
    properties = ProGenContext.getFamilyOptions("mandatory.percent");
    assertTrue(properties.size() == 2);
    properties = ProGenContext.getFamilyOptions("undefined.family");
    assertTrue(properties.size() == 0);
  }

  @Ignore@Test
  public void testSetProperty() {
	String defaultValue = "default";
	String newProperty = "new.property";
    String value = ProGenContext.getOptionalProperty(newProperty, defaultValue);
    assertEquals(defaultValue, value);
    ProGenContext.setProperty(newProperty, newProperty);
    value = ProGenContext.getOptionalProperty(newProperty, defaultValue);
    assertEquals(newProperty, value);
  }

  @Ignore@Test(expected = MissingContextFileException.class)
  public void testThrowMissingContextFile() {
    ProGenContext.makeInstance("missing.context.file");
  }

  @Ignore@Test(expected = UndefinedMandatoryPropertyException.class)
  public void testThrowUndefindedMandatoryProperty() {
    ProGenContext.getMandatoryProperty("undefined.mandatory.option");
  }

  @Ignore@Test(expected = MalformedParameterException.class)
  public void testMalformedSuboptions() {
    String defaultValue = "value";
    String defaultSubOpt1 = "1";
    String defaultSubOptA = "A";

    String value, subOpt1, subOptA;

    ProGenContext.setProperty("option.suboptions.format1",
        "value(subopt1=1, suboptA=A)");
    ProGenContext.setProperty("option.suboptions.format2",
        "value(subopt1=1, suboptA=A)");
    ProGenContext.setProperty("option.suboptions.format3",
        "value(subopt1=1, suboptA=A)");
    ProGenContext.setProperty("option.suboptions.format4",
        "value(subopt1=1,     suboptA=A    )");
    ProGenContext.setProperty("option.suboptions.format5",
        "value(subopt1=1,   suboptA;A)");

    for (int i = 1; i <= 5; i++) {
      value = ProGenContext
          .getMandatoryProperty("option.suboptions.format" + i);
      subOpt1 = ProGenContext.getParameter(
          "option.suboptions.format" + i, "subopt1");
      subOptA = ProGenContext.getParameter(
          "option.suboptions.format" + i, "suboptA");
      assertTrue(defaultValue.equals(value));
      assertTrue(defaultSubOpt1.equals(subOpt1));
      assertTrue(defaultSubOptA.equals(subOptA));
    }
  }

}
