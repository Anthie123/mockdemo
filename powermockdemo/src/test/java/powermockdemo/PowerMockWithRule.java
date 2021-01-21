package powermockdemo;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import powermockdemo.util.MyTestClass;

@PowerMockIgnore("de.conciso.temp.*")
@PrepareForTest(MyTestClass.class)
public class PowerMockWithRule {
    @Rule
    public PowerMockRule rule = new PowerMockRule();
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

}
