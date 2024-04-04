package edu.cs370_group.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

// this is not a secure way of doing it, but since we wont deploy it we allow all origins
@CrossOrigin
@org.springframework.stereotype.Controller
@ResponseBody
public class Controller {
	DatabaseHelper databaseHelper = null;
	APIHelper apiHelper = null;
	SessionManager sessionManager = null;
	public Controller() {
		try {
			databaseHelper = new DatabaseHelper();
			apiHelper = new APIHelper();
			sessionManager = new SessionManager(databaseHelper, apiHelper);
		}
		catch (Exception exception) {
			System.out.println(exception);
			System.exit(1);
		}
	}

	@GetMapping(value = "/getSessionInfo", produces = "application/json")
	public String getSessionInfo(@RequestParam int session) throws Exception{
		return (sessionManager.getSessionInfo(session));
	}


	@GetMapping(value = "/createGenericSession", produces = "application/json")
	public String createGenericSession(@RequestParam String[] options) {
		List<String> optionList = new ArrayList<String>();
		for (int i = 0; i < options.length; i++) {
			optionList.add(options[i]);
		}
	 	return (sessionManager.createGenericSession(optionList));
	}

	@GetMapping(value = "/endSession", produces = "application/json")
	public String endSession(@RequestParam int session) throws Exception {
		return (sessionManager.endSession(session));
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
	public ResponseEntity<String> getGenreList() throws Exception {
		return ResponseEntity.ok(sessionManager.getGenreList());
	}

	//getFilm by ID
	@GetMapping(value = "/getFilm", produces = "application/json")
	public String getFilm(@RequestParam int filmID) throws Exception {
		return (apiHelper.getFilm(filmID));
	}


	@GetMapping("/createFilmSession")
	@GetMapping(value = "/createFilmSession", produces = "application/json")
	public String createFilmSession(@RequestParam String[] genres) throws Exception {
		List<Integer> genreList = new ArrayList<Integer>();
		for (int i = 0; i < genres.length; i++) {
			genreList.add(Integer.valueOf(genres[i]));
		}
		return (sessionManager.createFilmSession(genreList));
	}
}
