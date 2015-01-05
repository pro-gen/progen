package progen.userprogram;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProGenContext.class})
@Ignore
public class UserProgramTest {
  
  private UserProgram userProgram; 

  @Before
  public void setUp() throws Exception {
    mockStatic(ProGenContext.class);
    when(ProGenContext.getMandatoryProperty("progen.user.home")).thenReturn("progen.userprogram.");
    userProgram = new UserProgramDefaultTest();
  }

  @Test
  public void testGetUserProgram() {
    UserProgram userProgram = UserProgram.getUserProgram();
  }

  @Test
  public void testInitialize() {
    fail("Not yet implemented");
  }

  @Test
  public void testPostProcess() {
    fail("Not yet implemented");
  }

  @Test
  public void testFitness() {
    fail("Not yet implemented");
  }
  
  private class UserProgramDefaultTest extends UserProgram{

    @Override
    public double fitness(Individual individual) {
      return 0;
    }
    
  }

}
