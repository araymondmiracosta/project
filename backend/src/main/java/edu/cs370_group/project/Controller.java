package edu.cs370_group.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@org.springframework.stereotype.Controller
@ResponseBody
@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class Controller {
	DatabaseHelper databaseHelper = new DatabaseHelper();
	SessionManager sessionManager = new SessionManager(databaseHelper);

//	public ResponseEntity<Object> test() {
//		Person newPerson = new Person(5, 2, "John");
//		return new ResponseEntity<>(newPerson.getMap(), HttpStatus.OK);
//	}

//	@PostMapping("/sessions/{id}")
//	@GetMapping("/sessions/{id}")
//	public String postTest(@PathVariable String id) {
//		return ("{\"sessionId\":\"" + id + "\"}");
//	}
	
	@RequestMapping(value = "/getSessions", produces="application/json")
	public ResponseEntity<String> getSessions() {
		return ResponseEntity.ok(sessionManager.getSessions());
	}

	@RequestMapping("/createGenericSession")
	/*public int createGenericSession(@RequestParam String[] options) {
		//return (sessionManager.createGenericSession(options));
	}*/

	@GetMapping("/newVote")
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void newVote(@RequestParam int session, @RequestParam int option) {
		sessionManager.newVote(session, option);
	}

	@GetMapping("/devVote")
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void delVote(@RequestParam int session, @RequestParam int option) {
		sessionManager.delVote(session, option);
	}
}
