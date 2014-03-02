package test.app;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import progen.ProGen;
import progen.context.ProGenContext;

public abstract class ProGenApps {

    private File file;

    @Before
    public void setUp() {
	try {
	    file = File.createTempFile("ProGen", null);

	    ProGenContext.makeInstance();
	    ProGenContext.setProperty("progen.role.factory", "local");
	    ProGenContext.setProperty("progen.role.element", "client");
	} catch (IOException e) {
	    // do nothing
	}
    }

    @After
    public void tearDown() {
	ProGenContext.clearContext();
	file.delete();
    }

    protected void runProGen() {
	try {

	    PrintWriter writer = new PrintWriter(file);
	    for (String property : ProGenContext.getFamilyOptions("progen.")) {
		writer.print(property);
		writer.print(" = ");
		writer.println(ProGenContext.getMandatoryProperty(property));
	    }
	    writer.close();
	    String args [] =new String[1];
	    args[0]=file.getAbsolutePath();
	   
	    ProGen.main(args);
	    
	} catch (IOException e) {
	    // ignore
	}

    }

    @Test
    public abstract void testApp();

}
