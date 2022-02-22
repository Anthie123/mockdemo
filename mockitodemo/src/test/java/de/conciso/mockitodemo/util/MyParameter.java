package de.conciso.mockitodemo.util;

public class MyParameter {

	public String doSomething(String doIt) {
		return doIt + "-doSomething";
	};

	public String doSomethingElse(String doIt) {
		return doIt + "-doSomethingElse";
	};

	public String doSomethingMore(String doIt, String doItToo) {
		return doIt + "-" + doItToo + "-" + "-doSomething";
	};

	public void doSomethingSecret(String doIt) {
		System.out.println(doIt);
	};

}
