package edu.cs370_group.project;

import java.util.ArrayList;

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
	public void createSession(int sessionID) {

	}

	// Remove the given session from the
	// database
	public void endSession(int sessionID) {

	}

	// Add a new vote for the given optionID
	public void newVote(int sessionID, int optionID) {

	}

	// Get votes for the given optionID
	public int getVoteTally(int sessionID, int optionID) {
		int voteTally = 0;

		return voteTally;
	}
}
