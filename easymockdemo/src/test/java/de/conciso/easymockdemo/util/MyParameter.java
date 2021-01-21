package de.conciso.easymockdemo.util;

public class MyParameter {

  public String doSomething(String doIt) {
    return doIt + "-doSomething";
  };

  public String doSomethingElse(String doIt) {
    return doIt + "-doSomethingElse";
  };

  public void doSomethingSecret(String doIt) {
    System.out.println(doIt);
  };

}
