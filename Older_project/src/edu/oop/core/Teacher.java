package edu.oop.core;

public class Teacher extends Person {
	
	private String inChClass;
	
	public Teacher(String name, String surName, String inChClass) {
		super(name, surName);
		this.inChClass = inChClass;
	}

	public String getInChClass() {
		return inChClass;
	}

	public void setInChClass(String inChClass) {
		this.inChClass = inChClass;
	}

	@Override
	public String toString() {
		return "Teacher [getInChClass()=" + getInChClass() + ", toString()=" + super.toString() + "]";
	}
	
	
}
