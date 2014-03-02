package test.progen.output.outputers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.output.HistoricalData;
import progen.output.outputers.StandardConsole;

public class StandardConsoleTest{
    private StandardConsole console;
    private HistoricalData historical;

    @Before
    public void setUp() throws Exception {
	ProGenContext.makeInstance();
	historical = HistoricalData.makeInstance();
	this.newGeneration();
	console= new StandardConsole();

    }

    @Test
    public void testPrint(){
	fail("Not implemented");
    }

    @Test
    public void newGeneration(){
	fail("Not implemented");
    }

}
