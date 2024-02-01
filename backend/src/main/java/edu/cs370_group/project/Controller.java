package edu.cs370_group.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping("/")
	public ResponseEntity<Object> test() {
		Person newPerson = new Person(5, 2, "John");
		return new ResponseEntity<>(newPerson.getMap(), HttpStatus.OK);
	}
}
