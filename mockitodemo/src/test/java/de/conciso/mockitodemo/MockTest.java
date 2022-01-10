package de.conciso.mockitodemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import de.conciso.mockitodemo.util.MyParameter;
import de.conciso.mockitodemo.util.MyTestClass;


@ExtendWith(MockitoExtension.class)
public class MockTest {

  public static final String STRING_VALUE = "value";

  @InjectMocks
  private MyTestClass cut = new MyTestClass();

  @Mock
  private MyParameter parameterMock;

  @Test
  public void whenAndVerify() {
    MyParameter mock = mock(MyParameter.class);

    when(mock.doSomething(STRING_VALUE))
        .thenReturn(STRING_VALUE);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));

    verify(mock).doSomething(STRING_VALUE);
  }

  
  @Test
  public void whenAndVerifyWithCallCount() {
    when(parameterMock.doSomething(STRING_VALUE))
        .thenReturn(STRING_VALUE);

    assertEquals(STRING_VALUE, cut.myFunction(parameterMock, STRING_VALUE));
    verify(parameterMock, times(1)).doSomething(STRING_VALUE);
    ;
  }

  @Test
  public void whenAndVerifyWithTimeoutAndCallCount() {
    when(parameterMock.doSomething(STRING_VALUE))
        .thenReturn(STRING_VALUE);

    assertEquals(STRING_VALUE, cut.myFunction(parameterMock, STRING_VALUE));
    verify(parameterMock, timeout(100).times(1)).doSomething(STRING_VALUE);
  }


  @Test
  public void whenWithArgumentMatcher() {
    MyParameter mock = mock(MyParameter.class);

    when(mock.doSomething(anyString()))
        .thenReturn(STRING_VALUE);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));
  }

  @Test
  public void whenAndVerifyArgumentMatcher() {
    when(parameterMock.doSomething(STRING_VALUE))
        .thenReturn(STRING_VALUE);

    assertEquals(STRING_VALUE, cut.myFunction(parameterMock, STRING_VALUE));
    verify(parameterMock, times(1)).doSomething(anyString());
    ;
  }

  @Test
  public void mockVoidMethodAndVerifyArgumentMatcher() {
    parameterMock.doSomethingSecret(STRING_VALUE);
    verify(parameterMock, times(1)).doSomethingSecret(anyString());
    ;
  }

  @Test
  public void whenAndException() {
    MyParameter mock = mock(MyParameter.class);

    when(mock.doSomething(STRING_VALUE))
        .thenThrow(new RuntimeException());

    assertThrows(RuntimeException.class, () -> cut.myFunction(mock, STRING_VALUE));
  }

  @Test
  public void whenWithVoidAndException() {
    MyParameter mock = mock(MyParameter.class);

    doThrow(new RuntimeException()).when(mock).doSomethingSecret(STRING_VALUE);

    assertThrows(RuntimeException.class, () -> mock.doSomethingSecret(STRING_VALUE));
  }

  @Test
  public void whenWithAnswer() {
    MyParameter mock = mock(MyParameter.class);

    when(mock.doSomething(STRING_VALUE))
        .then((invocation) -> invocation.getArgument(0).toString());

    when(mock.doSomethingElse(STRING_VALUE))
        .thenAnswer(new Answer<String>() {
          @Override
          public String answer(InvocationOnMock invocationOnMock) throws Throwable {
            return invocationOnMock.getArgument(0).toString();
          }
        });

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));
    assertEquals(STRING_VALUE, mock.doSomethingElse(STRING_VALUE));

    verify(mock).doSomething(anyString());
  }

  @Test
  public void whenWithDelegation() {
    MyParameter mock = mock(MyParameter.class);

    when(mock.doSomething(STRING_VALUE))
        .thenAnswer((invocation) -> new MyParameter() {
          @Override
          public String doSomething(String doIt) {
            return doIt + "Hallo";
          }
        }.doSomething(invocation.getArgument(0)));

    assertEquals(STRING_VALUE + "Hallo", cut.myFunction(mock, STRING_VALUE));
  }

  @Test
  public void whenWithSpy() {
    MyParameter mock = spy(new MyParameter());

    when(mock.doSomething(STRING_VALUE))
        .thenReturn(STRING_VALUE);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));

    assertEquals(new MyParameter().doSomethingElse(STRING_VALUE),
        mock.doSomethingElse(STRING_VALUE));

  }

  @Test
  public void whenWithRealPartialMock() {
    MyParameter mock = mock(MyParameter.class);

    when(mock.doSomething(STRING_VALUE))
        .thenCallRealMethod();

    assertNotEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));

    assertNotEquals(new MyParameter().doSomethingElse(STRING_VALUE),
        mock.doSomethingElse(STRING_VALUE));

  }

  @Test
  public void whenOnelinerMocks() {
    MyParameter mock = when(mock(MyParameter.class).doSomething(STRING_VALUE))
        .thenReturn(STRING_VALUE)
        .getMock();

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));
  }

  @Test
  public void mockBddStyle() {
    MyParameter mock = mock(MyParameter.class);

    given(mock.doSomething(STRING_VALUE))
        .willReturn(STRING_VALUE);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));

    then(mock).should(times(1)).doSomething(STRING_VALUE);
  }

  @Test
  public void mockWithOwnErrorDescription() {
    MyParameter mock = mock(MyParameter.class);

    when(mock.doSomething(STRING_VALUE))
        .thenReturn(STRING_VALUE);

    cut.myFunction(mock, STRING_VALUE);
    verify(mock, never().description("Should never be called, shit")).doSomething(STRING_VALUE);
  }


  @Test
  public void whenWithCustomArgumentMatcher() {
    MyParameter mock = mock(MyParameter.class);

    when(mock.doSomething(argThat(new ArgumentMatcher<String>() {
      @Override
      public boolean matches(String s) {
        return STRING_VALUE.equals(s);
      }
    })))
        .thenReturn(STRING_VALUE);

    when(mock.doSomethingElse(argThat((string) -> STRING_VALUE.equals(string))))
        .thenReturn(STRING_VALUE);

    assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));
    assertEquals(STRING_VALUE, mock.doSomethingElse(STRING_VALUE));
  }


   @Test
   public void mockWithCapturing() {
   MyParameter mock = mock(MyParameter.class);

   when(mock.doSomething(anyString()))
       .thenReturn(STRING_VALUE);

   assertEquals(STRING_VALUE, cut.myFunction(mock, STRING_VALUE));

   ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
   verify(mock).doSomething(stringCaptor.capture());

   assertEquals(STRING_VALUE, stringCaptor.getValue());
   }

  @Test
  public void mockStatic() {
    try(MockedStatic<MyTestClass> mock = Mockito.mockStatic(MyTestClass.class)) {
      mock.when(() -> MyTestClass.anotherFunction(STRING_VALUE))
          .thenReturn(STRING_VALUE);

      assertEquals(STRING_VALUE, MyTestClass.anotherFunction(STRING_VALUE));
    }
  }

}
