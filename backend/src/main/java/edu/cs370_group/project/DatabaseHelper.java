package edu.cs370_group.project;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/*
 * DATABASE SCHEMA:
 *
 * Session
 * ---------------------------
 * | SessionID | SessionType | 
 * ---------------------------
 * | 110       | 0           | (0 = generic, 1 = film)
 * | 111       | 1           |
 *
 * Options
 * ---------------------------------------
 * | OptionID  | SessionID   | VoteTally |
 * |-----------|-------------|-----------|
 * | 9402      | 110         | 4         |
 * | 10        | 110         | 2         |
 * | 11        | 111         | 9         |
 * | 12        | 111         | 5         |
 *
 */

class DatabaseHelper {
	/**
	 * Create new database connection
	*/
	public DatabaseHelper() {

	}

	/**
	 * Returns a List<Integer> of session IDs.
	 *
	 * @return The list
	*/
	public List<Integer> getSessions() {
		ArrayList<Integer> sessionList = new ArrayList<Integer>();

		return sessionList;
	}

	/**
	 * Creates a new entry in the Session table
	 * and a new set of rows in the Options table
	 * with the given SessionID and OptionID for each option
	 *
	 * @param sessionID The session ID
	 * @param options The list of options containing each option with its
	 * 				 ID and description
	 * @param isFilmSession If this session should be marked as film
	 * 					   selection
	*/
	public void createSession(int sessionID, List<Map<Integer, String>> options, Boolean isFilmSession) {
		setOptions(sessionID, options);
	}

	/**
	 * Removes the given sesssion from the Session table and any
	 * corresponding option rows from the Option table
	 *
	 * @param sessionID The session ID to remove
	*/
	public void endSession(int sessionID) {

	}

	/**
	 * Iterates the vote tally of the given option ID associated
	 * with the given session ID by one in the Option table
	 *
	 * @param sessionID The session ID
	 * @param optionID The option ID
	*/
	public void newVote(int sessionID, int optionID) {

	}

	/**
	 * Returns the vote tally for the given option in the
	 * given session
	 *
	 * @param sessionID The session ID
	 * @param optionID The option ID
	 *
	 * @return The vote tally for the option
	*/
	public int getOptionVoteTally(int sessionID, int optionID) {
		int voteTally = 0;

		return voteTally;
	}

	/**
	 * Returns a list of the options for the given session ID
	 *
	 * @param sessionID The session ID
	 *
	 * @return The list of option IDs
	*/
	public List<Integer> getOptions(int sessionID) {
		List<Integer> newList = new ArrayList<Integer>();

		return newList;
	}

	/**
	 * Removes the current options for the given session and replaces them
	 * with the options in the given list
	 *
	 * @param sessionID The session ID to replace the options of
	 * @param list The list containing the options with their IDs and descriptions
	 */
	public void setOptions(int sessionID, List<Map<Integer, String>> list) {
	}

	/**
	 * Returns true if the session indicated by the given session ID is
	 * a film selection session.
	 *
	 * @param sessionID The ID of the session
	 *
	 * @return True if film session, false otherwise
	 */
	public Boolean isFilmSession(int sessionID) {
		return true;
	}

	/**
	 * Returns the description of the option indicated by the
	 * given session ID and option ID.
	 *
	 * @param sessionID The session ID
	 * @param optionID The option ID
	 *
	 * @return The description
	*/
	public String getOptionDescription(int sessionID, int optionID) {
		return "";
	}
}
