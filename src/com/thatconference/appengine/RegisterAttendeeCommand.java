package com.thatconference.appengine;

public class RegisterAttendeeCommand {
	private String firstName = null;
	private String lastName = null;
	
	public RegisterAttendeeCommand(){}
	
	public RegisterAttendeeCommand(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
}
