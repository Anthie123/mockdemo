package powermockdemo;

import powermockdemo.util.MyTestClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@PowerMockIgnore("de.conciso.temp.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(MyTestClass.class)
public class PowerMockTest {

  public static final String STRING_VALUE = "value";

  @Test
  public void mockStaticMethod() {
    PowerMockito.mockStatic(MyTestClass.class);
    Mockito.when(MyTestClass.anotherFunction(STRING_VALUE))
        .thenReturn(STRING_VALUE);

    // Test Method calling static class method (here simulated to call it directly)
    MyTestClass.anotherFunction(STRING_VALUE);

    // Verify the call of the static method
    PowerMockito.verifyStatic(MyTestClass.class);
    MyTestClass.anotherFunction(STRING_VALUE);
  }

  @Test
  public void spyPrivateMethod() throws Exception {
    MyTestClass mock = PowerMockito.spy(new MyTestClass());

    PowerMockito.doReturn(STRING_VALUE).when(mock, "myFunction", STRING_VALUE);

    mock.callPrivate(STRING_VALUE);

    // Test Method calling static class method (here simulated to call it directly)
    PowerMockito.verifyPrivate(mock).invoke("myFunction", STRING_VALUE);
  }

}
