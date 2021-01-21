package powermockdemo.util;

public class MyTestClass {

  public String callPrivate(String value) {
    return myFunction(value);
  }
  private String myFunction(String value) {
    return value + "-myFunction";
  }
  public static String anotherFunction(String value) {
    return value + "-anotherFunction";
  }

}