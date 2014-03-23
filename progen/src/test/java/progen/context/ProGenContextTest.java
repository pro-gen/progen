package progen.context;

import org.junit.After;
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
  
  @After
  public void tearDown(){
    ProGenContext.clearContext();
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
  
  @Test(expected=MissingContextFileException.class)
  public void testMakeInstanceNullFile(){
	  ProGenContext props = ProGenContext.makeInstance(null);
	  fail("MissingContextFileException must be thrown");
  }
  
  @Test(expected=MissingContextFileException.class)
  public void testMakeInstanceNotFoundFile(){
	  ProGenContext props = ProGenContext.makeInstance("file-not-found.properties");
	  fail("MissingContextFileException must be thrown");
  }
  
  @Test
  public void testClearContext(){
    ProGenContext currentContext = ProGenContext.makeInstance();
    assertEquals(proGenContext, currentContext);
    ProGenContext.clearContext();
    currentContext = ProGenContext.makeInstance();
    assertNotEquals(proGenContext, currentContext);
  }

  @Test
  public void testGetOptionalPropertyStringInt() {
    int defaultValue = 9;
    int value = 42;
    ProGenContext.setProperty("optional.int.defined", value+"");
    int contextValue = ProGenContext.getOptionalProperty("optional.int.default", defaultValue);
    assertEquals(defaultValue, contextValue);
    contextValue = ProGenContext.getOptionalProperty("optional.int.defined", defaultValue);
    assertEquals(value, contextValue);
    assertNotEquals(value, defaultValue);
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
    String propertyValue = "valor(sub1="+value1+",sub2="+value2+",sub3="+value3+")";
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

  @Test
  public void testGetParameter() {
    ProGenContext.setProperty("option", "value(parameter=42");
    String parameterValue = ProGenContext.getParameter("option", "parameter.undefined");
    assertNull(parameterValue);
    parameterValue = ProGenContext.getParameter("option", "parameter");
    assertNotNull(parameterValue);
    assertEquals("42", parameterValue);
  }

  @Test
  public void testGetParameters() {
    ProGenContext.setProperty("options", "value(param1=a, param2=b, param3=c");
    Map<String, String> option = ProGenContext.getParameters("options");
    assertEquals(3, option.size());
    assertTrue(option.containsKey("param1"));
    assertTrue(option.containsKey("param2"));
  }
  
  @Test
  public void testGetParametersUndefined(){
    Map<String, String> option = ProGenContext.getParameters("option.suboptions.notExists");
    assertEquals(0, option.size());

    ProGenContext.setProperty("option.without.params", "value");
    option = ProGenContext.getParameters("option.without.params");
    assertEquals(0, option.size());

  }
  
  @Test(expected=MalformedParameterException.class)
  public void testGetParameterMalformed(){
    ProGenContext.setProperty("mal.formed.parameter", "value(a:1;b:2)");
    ProGenContext.getParameters("mal.formed.parameter");
    fail("MalformedParameterException must be thrown");
  }


  @Test
  public void testGetOptionalPercent() {
    String percentOption = "percent";
    String percentNumber = "0.25";
    String percentString = "40%";
    String defaultPercent = "0.5";
    
    double percent = ProGenContext.getOptionalPercent("undefined.percent", defaultPercent);
    assertEquals(percent, Double.parseDouble(defaultPercent), 0.0);
    ProGenContext.setProperty(percentOption, percentNumber);
    percent = ProGenContext.getOptionalPercent(percentOption, defaultPercent);
    assertEquals(Double.parseDouble(percentNumber), percent, 0.0);
    ProGenContext.setProperty(percentOption, percentString);
    percent = ProGenContext.getOptionalPercent(percentOption, defaultPercent);
    assertEquals(0.4, percent, 0.0);
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
