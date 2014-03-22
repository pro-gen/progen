package progen.context;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProGenContextTest {

  private ProGenContext proGen;

  @Before
  public void setUp() throws Exception {
    proGen = ProGenContext.makeInstance();
    /*ProGenContext.setProperty("optional", "property optional");
    ProGenContext.setProperty("optional.int", Integer.MAX_VALUE + "");
    ProGenContext.setProperty("optional.string", "Lorem ipsum dolor sit");
    ProGenContext.setProperty("optional.double", Math.PI + "");
    ProGenContext.setProperty("optional.percent", "0.87");
    ProGenContext.setProperty("mandatory.string", "Lorem ipsum dolor sit amet");
    ProGenContext.setProperty("mandatory.percent.string", "99%");
    ProGenContext.setProperty("mandatory.percent.double", "0.75");
    ProGenContext.setProperty("suboptional.percent", "option(suboption1=0.60,suboption2=40%)");
    ProGenContext.setProperty("option.suboptions", "value(subopt1=1,suboptA=A)");
*/
  }

  @Test
  public void testMakeInstance() {
    ProGenContext props = ProGenContext.makeInstance();
    assertEquals(proGen, props);
  }

  @Test
  public void testMakeInstanceFile(){
    ProGenContext props = ProGenContext.makeInstance("test.properties");
    assertNotNull(props);
    assertNotEquals(proGen, props);
    assertEquals("OK", ProGenContext.getMandatoryProperty("property"));
  }

  @Ignore@Test
  public void testGetOptionalPropertyStringInt() {
    int defaultValue = 9;
    int value = ProGenContext.getOptionalProperty("optional.int", defaultValue);
    assertFalse(value == defaultValue);
    assertEquals(value, Integer.MAX_VALUE);

    value = ProGenContext.getOptionalProperty("optional.undefined", defaultValue);
    assertTrue(value == defaultValue);
  }

  @Ignore@Test
  public void testGetOptionalPropertyString() {
    String defaultValue = "default";
    String value = ProGenContext.getOptionalProperty("optional.string",
        defaultValue);
    assertFalse(value.equals(defaultValue));
    assertTrue(value.equals("Lorem ipsum dolor sit"));

    value = ProGenContext.getOptionalProperty("optional.undefined",
        defaultValue);
    assertTrue(value.equals(defaultValue));
  }

  @Ignore@Test
  public void testGetOptionalPropertyStringDouble() {
    double defaultValue = Math.E;
    double value = ProGenContext.getOptionalProperty("optional.double",
        defaultValue);
    assertFalse(new BigDecimal(value).equals(new BigDecimal(defaultValue)));
    assertTrue(new BigDecimal(value).equals(new BigDecimal(Math.PI)));

    value = ProGenContext.getOptionalProperty("optional.undefined",
        defaultValue);
    assertTrue(new BigDecimal(value).equals(new BigDecimal(Math.E)));
  }

  @Ignore@Test(expected = UndefinedMandatoryPropertyException.class)
  public void testGetMandatoryProperty() {
    String mandatory = ProGenContext
        .getMandatoryProperty("mandatory.string");
    assertTrue(mandatory.equals("Lorem ipsum dolor sit amet"));
    // throws exception
    ProGenContext.getMandatoryPercent("undefined.mandatory");
  }

  @Ignore@Test
  public void testGetSuboptionPercent() {
    String option;
    double defaultPercent = 0.33;
    double subOption1;
    double subOption2;
    double subOption3;
    option = ProGenContext.getOptionalProperty("suboptional.percent", "");
    subOption1 = ProGenContext.getSuboptionPercent("suboptional.percent",
        "suboption1", defaultPercent);
    subOption2 = ProGenContext.getSuboptionPercent("suboptional.percent",
        "suboption2", defaultPercent);
    subOption3 = ProGenContext.getSuboptionPercent("suboptional.percent",
        "suboption3", defaultPercent);

    assertTrue(option.compareTo("option") == 0);
    assertTrue(new BigDecimal(subOption1).equals(new BigDecimal(0.6)));
    assertTrue(new BigDecimal(subOption2).equals(new BigDecimal(0.4)));
    assertTrue(new BigDecimal(subOption3).equals(new BigDecimal(
        defaultPercent)));
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
    String value = "Lorem ipsum";
    String initialProperty = ProGenContext
        .getMandatoryProperty("mandatory.string");
    ProGenContext.setProperty("mandatory.string", value);
    String modifiedProperty = ProGenContext
        .getMandatoryProperty("mandatory.string");
    assertFalse(initialProperty.compareTo(modifiedProperty) == 0);
    assertTrue(modifiedProperty.compareTo(value) == 0);

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
