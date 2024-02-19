package edu.cs370_group.project;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

class DatabaseHelper {

	// Create new database connection
	public DatabaseHelper() {

	}

	// Get all session ids from database
	public ArrayList<Integer> getSessions() {
		ArrayList<Integer> sessionList = new ArrayList<Integer>();

		return sessionList;
	}

	// Create a new session in database,
	// calling function responsible for
	// checking for duplicates
	public void createSession(int sessionID, List<Map<Integer, String>> options, Boolean isFilmSession) {
		setOptions(sessionID, options);
	}

	// Remove the given session from the
	// database
	public void endSession(int sessionID) {

	}

	// Add a new vote for the given optionID
	public void newVote(int sessionID, int optionID) {

	}

	// Get votes for the given optionID
	public int getOptionVoteTally(int sessionID, int optionID) {
		int voteTally = 0;

		return voteTally;
	}

	public List<Integer> getOptions(int sessionID) {
		List<Integer> newList = new ArrayList<Integer>();

		return newList;
	}

	public void setOptions(int sessionID, List<Map<Integer, String>> list) {
	}

	public Boolean isFilmSession(int sessionID) {
		return true;
	}

	public String getOptionDescription(int sessionID, int optionID) {
		return "";
	}
}
