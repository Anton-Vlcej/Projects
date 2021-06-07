package edu.oop.core;

public class Student extends Person{
	
	private String inClass;

	public Student(String name, String surName, String iClass) {
		super(name, surName);
		this.inClass = iClass;
	}

	public String getiClass() {
		return inClass;
	}

	public void setiClass(String iClass) {
		this.inClass = iClass;
	}

	@Override
	public String toString() {
		return "Student [getiClass()=" + getiClass() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
