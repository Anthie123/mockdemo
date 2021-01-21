package powermockdemo;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import powermockdemo.util.MyTestClass;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("de.conciso.temp.*")
@PowerMockRunnerDelegate(Parameterized.class)
@PrepareForTest(MyTestClass.class)
public class PowerMockWithDelegateRunner {

  @Parameter(0)
  public String value;

  @Parameterized.Parameters(name = "expected={0}")
  public static Collection<?> expections() {
    return Arrays.asList(new Object[][]{
        {"Hello"}, {"something"}, {"test"}
    });
  }

  @Test
  public void mockStaticMethod() {
    PowerMockito.mockStatic(MyTestClass.class);
    Mockito.when(MyTestClass.anotherFunction(value))
        .thenReturn(value);

    // Test Method calling static class method (here simulated to call it directly)
    MyTestClass.anotherFunction(value);

    // Verify the call of the static method
    PowerMockito.verifyStatic(MyTestClass.class);
    MyTestClass.anotherFunction(value);
  }

}

