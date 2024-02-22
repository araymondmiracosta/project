package edu.cs370_group.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.ArrayList;

@org.springframework.stereotype.Controller
@ResponseBody
@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class Controller {
	DatabaseHelper databaseHelper = new DatabaseHelper();
	APIHelper apiHelper = new APIHelper();
	SessionManager sessionManager = new SessionManager(databaseHelper, apiHelper);

/*	@RequestMapping(value = "/getSessions", produces = "application/json")
	public ResponseEntity<String> getSessions() {
		return ResponseEntity.ok(sessionManager.getSessions());
	} */

	@RequestMapping(value = "/getSessionInfo", produces = "application/json")
	public String getSessionInfo(@RequestParam int session) {
		return (sessionManager.getSessionInfo(session));
	}


	@RequestMapping("/createGenericSession")
	public int createGenericSession(@RequestParam String[] options) {
		List<String> optionList = new ArrayList<String>();
		for (int i = 0; i < options.length; i++) {
			optionList.add(options[i]);
		}
		return (sessionManager.createGenericSession(optionList));
	}

	@GetMapping("/newVote")
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void newVote(@RequestParam int session, @RequestParam int option) {
		sessionManager.newVote(session, option);
	}

	@GetMapping("/delVote")
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void delVote(@RequestParam int session, @RequestParam int option) {
		sessionManager.delVote(session, option);
	}

	@GetMapping(value = "/getGenreList", produces = "application/json")
	public ResponseEntity<String> getGenreList() {
		return ResponseEntity.ok(apiHelper.getGenreList());
	}

	@GetMapping("/createFilmSession")
	public int createFilmSession(@RequestParam String[] genres) {
		List<Integer> genreList = new ArrayList<Integer>();
		for (int i = 0; i < genres.length; i++) {
			genreList.add(Integer.valueOf(genres[i]));
		}
		return (sessionManager.createFilmSession(genreList));
	}
}
