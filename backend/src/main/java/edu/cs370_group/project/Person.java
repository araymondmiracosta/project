package edu.cs370_group.project;

class Person {
	int age;
	int id;
	String name;

	Person() {
		this.age = 0;
		this.id = 0;
		this.name = "";
	}

	Person(int age, int id, String name) {
		this.age = age;
		this.id = id;
		this.name = name;
	}

	int getAge() {
		return this.age;
	}

	int getId() {
		return this.id;
	}

	String getName() {
		return this.name;
	}
}
