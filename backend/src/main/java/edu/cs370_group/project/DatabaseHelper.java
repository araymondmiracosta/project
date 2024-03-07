package edu.cs370_group.project;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.sql.*;

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
 * TallyOptionTable
 * -----------------------------------------------------
 * | OptionID  | SessionID   | Description | VoteTally |
 * |-----------|-------------|-------------|-----------|
 * | 9402      | 110         | "Movie 1"   | 4         |
 * | 10        | 110         | "Movie 2"   | 2         |
 * | 11        | 111         | "Option 11" | 9         |
 * | 12        | 111         | "Option 12" | 5         |
 *
 */

class DatabaseHelper {
	Connection connection;
	/**
	 * Create new database connection
	*/
	public DatabaseHelper() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1/java", "user", "password"
			);
			System.out.println("Database connection successful!");

			// Check that tables exist, create them if they do not
			String tableName = "Session";
			Boolean found = false;
			java.sql.DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, null);
			if (resultSet.next()) {
				found = true;
			}
			if (!(found)) {
				// Session table not found, so create it
				Statement statement = connection.createStatement();
				String sql = "CREATE TABLE Session " +
                   "(" +
					   "SessionID INTEGER not NULL, " +
					   "SessionType INTEGER, " + 
					   "PRIMARY KEY (SessionID)" +
				   ")";
				statement.executeUpdate(sql);
				System.out.println("Created Session table successfully.");   	
			}
			else {
				System.out.println("Session table already created.");
			}

			tableName = "TallyOptionTable";
			found = false;
			resultSet = databaseMetaData.getTables(null, null, tableName, null);
			if (resultSet.next()) {
				found = true;
			}
			if (!(found)) {
				// Option table not found, so create it
				Statement statement = connection.createStatement();
				String sql = "CREATE TABLE TallyOptionTable" +
					"(" +
						"OptionID INTEGER not NULL, " +
						"SessionID INTEGER, " +
						"Description VARCHAR(255), " +
						"VoteTally INTEGER, " +
						"PRIMARY KEY (OptionID,SessionID)" +
					")";
				statement.executeUpdate(sql);
				System.out.println("Created TallyOptionTable table successfully.");
			}
			else {
				System.out.println("TableOptionTable table already created.");
			}
		}
		catch(Exception exception) {
			System.out.println("Database connection or initialization failed. Is the database running? Exiting.");
			System.out.println(exception);
			System.exit(1);
		}
	}

	/**
	 * Returns a List<Integer> of session IDs.
	 *
	 * @return The list
	*/
//	public List<Integer> getSessions() {
//		ArrayList<Integer> sessionList = new ArrayList<Integer>();
//
//		return sessionList;
//	}

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
		int isFilmSessionInteger = isFilmSession ? 1 : 0;
		try {
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO Session (SessionID, SessionType) " +
				   		 "VALUES (" + sessionID + ", " + isFilmSessionInteger + ")";
			statement.executeUpdate(sql);
			System.out.println("New session added to Session table.");
			setOptions(sessionID, options);
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}

	/**
	 * Removes the given sesssion from the Session table and any
	 * corresponding option rows from the Option table
	 *
	 * @param sessionID The session ID to remove
	*/
	public void endSession(int sessionID) {
		try {
			Statement statement;
			String sql;
			statement = connection.createStatement();
			sql = "DELETE FROM TallyOptionTable WHERE SessionID=" + sessionID;
			statement.executeUpdate(sql);
			System.out.println("Options deleted for sessionID: " + sessionID);

			statement = connection.createStatement();
			sql = "DELETE FROM Session WHERE SessionID=" + sessionID;
			statement.executeUpdate(sql);
			System.out.println("Session deleted for sessionID: " + sessionID);
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}

	/**
	 * Iterates the vote tally of the given option ID associated
	 * with the given session ID by one in the Option table
	 *
	 * @param sessionID The session ID
	 * @param optionID The option ID
	*/
	public void newVote(int sessionID, int optionID) {
		try{
			Statement statement;
			String sql;
			statement = connection.createStatement();
			sql = "UPDATE TallyOptionTable SET VoteTally = VoteTally + 1 WHERE OptionID=" + optionID + " AND SessionID=" + sessionID;
			statement.executeUpdate(sql);
		}
		catch (Exception exception) {
			System.out.println(exception.toString());
		}
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
		int voteTally = -1;
		try {
			Statement statement;
			String sql;
			statement = connection.createStatement();
			sql = "SELECT OptionID, SessionID, VoteTally FROM TallyOptionTable WHERE OptionID=" + optionID + " AND SessionID=" + sessionID;
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				voteTally = resultSet.getInt("VoteTally");
			}
		}
		catch (Exception exception) {
			System.out.println(exception);
		}

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
		try {
			for (Map<Integer, String> optionMap : list) {
				int optionID = optionMap.entrySet().iterator().next().getKey();
				String description = optionMap.entrySet().iterator().next().getValue(); // I love Java :P (SAME)
				Statement statement = connection.createStatement();
				String sql = "INSERT INTO TallyOptionTable (OptionID, SessionID, Description, VoteTally) " +
							 "VALUES (" + optionID + ", " + sessionID + ", \'" + description + "\', " + 0 + ")";
				statement.executeUpdate(sql);
			}
			System.out.println("New options added to Option table.");
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
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
