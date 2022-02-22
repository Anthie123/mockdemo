package de.conciso.mockitodemo.util;

public class MyTestClass {

  public String myFunction(MyParameter param, String value) {
    return param.doSomething(value);
  }

  public static String anotherFunction(String value) {
    return value + "-anotherFunction";
  }

  public String mySpecialFunction(MyParameter param, String value) {
	    return param.doSomethingMore(value, value);
	  }

}
