package edu.cs370_group.project;

class SessionManager {
	private DatabaseHelper databaseHelper;

	// Controller provides DatabaseHelper object
	public SessionManager(DatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}

	// Create new session with unique ID
	public int createSession(String[] options) {
		int sessionID = 0;
		return sessionID;
	}

	public int createGenericSession(String[] options) {
		int sessionID = createSession(options);
		return sessionID;
	}

	public int createFilmSession(int[] genres) {
//		String[] options = APIHelper.getFilmList(genres);
//		int sessionID = createSession(options);
//		return sessionID;
		return 0;
	}

	public void newVote(int sessionID, int optionID) {
		databaseHelper.newVote(sessionID, optionID);
	}

	public int getVoteTally(int sessionID, int optionID) {
		return (databaseHelper.getVoteTally(sessionID, optionID));
	}
}
