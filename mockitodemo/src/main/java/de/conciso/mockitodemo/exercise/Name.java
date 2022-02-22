package de.conciso.mockitodemo.exercise;

public class Name {
	private long id;

	private String firstName;
	private String lastName;

	public Name(long id, String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public long getId() {
		return id;
	}

	
	void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
