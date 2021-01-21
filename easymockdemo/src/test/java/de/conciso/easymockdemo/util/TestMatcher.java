package de.conciso.easymockdemo.util;

import de.conciso.easymockdemo.MockTest;
import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;

public class TestMatcher implements IArgumentMatcher {

  private final static String ARGUMENT_VALUE = MockTest.STRING_VALUE;

  public static String eqTestMatcher() {
    EasyMock.reportMatcher(new TestMatcher());
    return null;
  }

  @Override
  public boolean matches(Object argument) {
    return ARGUMENT_VALUE.equals(argument);
  }

  @Override
  public void appendTo(StringBuffer buffer) {
    buffer.append("eqTestMatcher()");
  }
}
