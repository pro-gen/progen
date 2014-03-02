package test.app;

import static org.junit.Assert.assertTrue;

import progen.context.ProGenContext;

public class RegressionTest extends ProGenApps {

    @Override
    public void testApp() {
	ProGenContext.setProperty("progen.experiment.file", "app.regression.Regression.txt");
	super.runProGen();
	assertTrue("TEST OK", true);
    }
}
