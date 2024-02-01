package edu.cs370_group.project;

import java.util.HashMap;
import java.util.Map;

class Person {
	int age;
	int id;
	String name;

	Person() {
		this.age = 0;
		this.id = 0;
		this.name = "";
	}

	Person(int id, int age, String name) {
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

	Map<String, String> getMap() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("id", Integer.toString(this.id)); 
		data.put("age", Integer.toString(this.age));
		data.put("name", this.name);

		return data;
	}
}
