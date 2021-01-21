package de.conciso.easymockdemo;

import static org.easymock.EasyMock.expect;

import de.conciso.easymockdemo.util.MyParameter;
import de.conciso.easymockdemo.util.MyTestClass;
import org.easymock.EasyMockSupport;
import org.junit.jupiter.api.Test;

public class MockSupportTest extends EasyMockSupport {

  public static final String STRING_VALUE = "value";

  private MyTestClass cut = new MyTestClass();
  ;

  private MyParameter parameterMock1;
  private MyParameter parameterMock2;

  @Test
  public void expectAndReplay() {
    parameterMock1 = mock(MyParameter.class);
    parameterMock2 = mock(MyParameter.class);

    expect(parameterMock1.doSomething(STRING_VALUE))
        .andReturn(STRING_VALUE);
    expect(parameterMock2.doSomething(STRING_VALUE))
        .andReturn(STRING_VALUE);
    replayAll();

    cut.myFunction(parameterMock1, STRING_VALUE);
    cut.myFunction(parameterMock2, STRING_VALUE);

    verifyAll();
  }

}
