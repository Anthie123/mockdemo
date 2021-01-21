package de.conciso.easymockdemo;

import static de.conciso.easymockdemo.util.TestMatcher.eqTestMatcher;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArgument;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.partialMockBuilder;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.conciso.easymockdemo.util.MyParameter;
import de.conciso.easymockdemo.util.MyTestClass;
import org.easymock.Capture;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(EasyMockExtension.class)
public class MockTest {

  public static final String STRING_VALUE = "value";

  @TestSubject
  private MyTestClass cut = new MyTestClass();
  ;

  @Mock(type = MockType.STRICT)
  private MyParameter parameterMock;

  @Test
  public void expectAndReplay() {
    MyParameter mock = mock(MyParameter.class);

    expect(mock.doSomething(STRING_VALUE))
        .andReturn(STRING_VALUE);
    expectLastCall().times(2);
    replay(mock);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));
  }

  @Test
  public void expectAndReplayVoid() {
    MyParameter mock = mock(MyParameter.class);

    mock.doSomethingSecret(STRING_VALUE);
    replay(mock);

    mock.doSomethingSecret(STRING_VALUE);
    verify(mock);
  }

  @Test
  public void expectAndReplayWithAnnotations() {
    expect(parameterMock.doSomething(STRING_VALUE))
        .andReturn(STRING_VALUE);
    expectLastCall().times(2);
    replay(parameterMock);

    assertEquals(STRING_VALUE, cut.myFunction(parameterMock, STRING_VALUE));
  }

  @Test
  public void expectAndReplayWithPartialMock() {
    MyParameter mock = partialMockBuilder(MyParameter.class)
        .addMockedMethod("doSomething")
        .createMock();

    expect(mock.doSomething(STRING_VALUE))
        .andReturn(STRING_VALUE)
        .andReturn(STRING_VALUE);
    replay(mock);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));
    assertEquals(new MyParameter().doSomethingElse(STRING_VALUE),
        mock.doSomethingElse(STRING_VALUE));
    assertNotEquals(new MyParameter().doSomething(STRING_VALUE),
        mock.doSomething(STRING_VALUE));

    verify(mock);
  }

  @Test
  public void expectAndReplayException() {
    MyParameter mock = mock(MyParameter.class);

    expect(mock.doSomething(STRING_VALUE))
        .andThrow(new RuntimeException());
    replay(mock);

    assertThrows(RuntimeException.class, () -> cut.myFunction(mock, STRING_VALUE));
    verify(mock);
  }

  @Test
  public void expectWithAnswerAndReplay() {
    MyParameter mock = mock(MyParameter.class);

    expect(mock.doSomething(STRING_VALUE))
        .andAnswer(() -> getCurrentArgument(0).toString());
    replay(mock);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));
    verify(mock);
  }

  @Test
  public void expectWithDelegationAndReplay() {
    MyParameter mock = mock(MyParameter.class);

    expect(mock.doSomething(STRING_VALUE))
        .andDelegateTo(new MyParameter() {
          @Override
          public String doSomething(String doIt) {
            return doIt + "Hallo";
          }
        });
    replay(mock);

    assertEquals(STRING_VALUE + "Hallo", cut.myFunction(mock, STRING_VALUE));
    verify(mock);
  }

  @Test
  public void expectWithArgumentMatcherAndReplay() {
    MyParameter mock = mock(MyParameter.class);

    expect(mock.doSomething(anyString()))
        .andReturn(STRING_VALUE);
    replay(mock);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));
    verify(mock);
  }

  @Test
  public void expectWithOwnArgumentMatcherAndReplay() {
    MyParameter mock = mock(MyParameter.class);

    expect(mock.doSomething(eqTestMatcher()))
        .andReturn(STRING_VALUE);
    replay(mock);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));
    verify(mock);
  }

  @Test
  public void expectWithCapturingAndReplay() {
    MyParameter mock = mock(MyParameter.class);
    Capture<String> stringCapture = Capture.newInstance();
    expect(mock.doSomething(capture(stringCapture)))
        .andReturn(STRING_VALUE);
    replay(mock);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));

    assertTrue(stringCapture.hasCaptured());
    assertEquals(STRING_VALUE, stringCapture.getValue());
  }

}
