package edu.cs370_group.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	DatabaseHelper databaseHelper = new DatabaseHelper();
	SessionManager sessionManager = new SessionManager(databaseHelper);

//	@GetMapping("/")
//	public ResponseEntity<Object> test() {
//		Person newPerson = new Person(5, 2, "John");
//		return new ResponseEntity<>(newPerson.getMap(), HttpStatus.OK);
//	}

//	@PostMapping("/sessions/{id}")
//	@GetMapping("/sessions/{id}")
//	public String postTest(@PathVariable String id) {
//		return ("{\"sessionId\":\"" + id + "\"}");
//	}

	@RequestMapping("/createGenericSession")
	public int createGenericSession(@RequestParam String[] options) {
		return (sessionManager.createGenericSession(options));
	}

	@GetMapping("/session/{id}/getVoteTally")
	public int getVoteTally(@RequestParam String[] options, @PathVariable String id) {
		int firstOption = Integer.getInteger(options[0]);
		int sessionID = Integer.getInteger(id);
		return (sessionManager.getVoteTally(sessionID, firstOption));
	}

	@GetMapping("/session/{id}/newVote")
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void newVote(@RequestParam String[] options, @PathVariable String id) {
		int firstOption = Integer.getInteger(options[0]);
		int sessionID = Integer.getInteger(id);
		sessionManager.newVote(sessionID, firstOption);
	}
}
