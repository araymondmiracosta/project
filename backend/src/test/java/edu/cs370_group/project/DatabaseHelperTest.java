package edu.cs370_group.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DatabaseHelperTest {
	DatabaseHelper databaseHelper;
	Connection connection;

	List<Map<Integer, String>> populateOptionList(int a) {
		List<Map<Integer, String>> optionList = new ArrayList<Map<Integer, String>>();
		for (int i = 0; i < a; i++) {
			Map<Integer, String> optionMap = new HashMap<Integer, String>();
			optionMap.put(i, "Option " + i);
			optionList.add(optionMap);
		}
		return optionList;
	}

    @BeforeEach
    void setUp() {
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1/java", "user", "password"
			);
			try {
				Statement statement = connection.createStatement();
				String sql = "DROP TABLE Session";
				statement.executeUpdate(sql);
				sql = "DROP TABLE TallyOptionTable";
				statement.executeUpdate(sql);
			}
			catch (Exception exception) {
			}

			// Tables created implicitly by constructor
	        databaseHelper = new DatabaseHelper();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
    }

	@AfterEach
    void tearDown() {
		try {
			Statement statement = connection.createStatement();
			String sql = "DROP TABLE Session";
			statement.executeUpdate(sql);
			sql = "DROP TABLE TallyOptionTable";
			statement.executeUpdate(sql);
			connection.close();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
    }	

	@Test
    void createSession() {
		int sessionID = 0;
		Boolean isFilmSession = false;
		List<Map<Integer, String>> optionList = populateOptionList(3);
		databaseHelper.createSession(sessionID, optionList, isFilmSession);

		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT SessionID FROM Session";
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				assertTrue(resultSet.getInt("SessionID") == sessionID);
			}
			else {
				assertTrue(false);
			}
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
    } 

    @Test
    void newVote() {
		int sessionID = 0;
		int optionID = 0;
		Boolean isFilmSession = false;
		List<Map<Integer, String>> optionList = populateOptionList(3);
		databaseHelper.createSession(sessionID, optionList, isFilmSession);
		databaseHelper.newVote(sessionID, optionID);

		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT OptionID, SessionID, VoteTally FROM TallyOptionTable WHERE SessionID=" + sessionID + " AND OptionID=" + optionID;
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				assertTrue(resultSet.getInt("VoteTally") == 1);
			}
			else {
				assertTrue(false);
			}
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
    }

    @Test
    void getOptionVoteTally() {
		int sessionID = 0;
		int optionID = 0;
		Boolean isFilmSession = false;
		List<Map<Integer, String>> optionList = populateOptionList(3);
		databaseHelper.createSession(sessionID, optionList, isFilmSession);

		try {
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO TallyOptionTable (SessionID, OptionID, VoteTally) VALUES (" + sessionID + ", " + optionID + ", " + 52 + ")";
			statement.executeUpdate(sql);
			int candidateValue = databaseHelper.getOptionVoteTally(sessionID, optionID);
			assertTrue(candidateValue == 52);

		}
		catch (Exception exception) {
			System.out.println(exception);
		}
    }

    @Test
    void getOptions() {
		int sessionID = 0;
		Boolean isFilmSession = false;
		List<Map<Integer, String>> optionList = populateOptionList(3);
		databaseHelper.createSession(sessionID, optionList, isFilmSession);

		assertTrue(databaseHelper.getOptions(sessionID).size() == optionList.size());
    }

    @Test
    void setOptions() {
		int sessionID = 0;
		Boolean isFilmSession = false;
		List<Map<Integer, String>> optionList = populateOptionList(3);
		databaseHelper.createSession(sessionID, optionList, isFilmSession);

		optionList = populateOptionList(6);
		databaseHelper.setOptions(sessionID, optionList);

		int count = 0;

		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT SessionID, OptionID FROM TallyOptionTable WHERE SessionID=" + sessionID;
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				count++;
			}
			assertTrue(count == optionList.size());
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
    }

    @Test
    void isFilmSession() {
		int sessionID = 0;
		Boolean isFilmSession = true;
		List<Map<Integer, String>> optionList = populateOptionList(3);
		databaseHelper.createSession(sessionID, optionList, isFilmSession);

		assertTrue(databaseHelper.isFilmSession(sessionID));

		sessionID = 1;
		isFilmSession = false;
		databaseHelper.createSession(sessionID, optionList, isFilmSession);

		assertTrue(databaseHelper.isFilmSession(sessionID) == false);
    }

    @Test
    void getOptionDescription() {
		int sessionID = 0;
		Boolean isFilmSession = false;
		List<Map<Integer, String>> optionList = populateOptionList(1);
		databaseHelper.createSession(sessionID, optionList, isFilmSession);

		assertTrue(databaseHelper.getOptionDescription(sessionID, 0).compareTo("Option 0") == 0);
    }

    @Test
    void endSession() {
		int sessionID = 0;
		Boolean isFilmSession = false;
		List<Map<Integer, String>> optionList = populateOptionList(3);
		databaseHelper.createSession(sessionID, optionList, isFilmSession);
		databaseHelper.endSession(sessionID);

		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT SessionID FROM Session WHERE SessionID=" + sessionID;
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				assertTrue(false);
			}
			else {
				assertTrue(true);
			}

			statement = connection.createStatement();
			sql = "SELECT SessionID FROM TallyOptionTable WHERE SessionID=" + sessionID;
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				assertTrue(false);
			}
			else {
				assertTrue(true);
			}
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
    }
}
