package edu.oop.core;

public class Person {
	
	private String name;
	private String surName;
	
	public Person(String name, String surName) {
		super();
		this.name = name;
		this.surName = surName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	@Override
	public String toString() {
		return "Person [name=" + getName() + ", surName=" + getSurName() + "]";
	}
	
	
}
