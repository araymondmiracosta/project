package edu.cs370_group.project;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class SessionManagerTest {
	SessionManager sessionManager;
	DatabaseHelper databaseHelper;
	APIHelper apiHelper;
	Connection connection;

	List<String> populateOptionList(int a) {
		List<String> optionList = new ArrayList<>();
		for (int i = 0; i < a; i++) {
			optionList.add("Option " + i);
		}
		return optionList;
	}

	@BeforeEach
    void setUp() throws Exception {
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

		databaseHelper = new DatabaseHelper();
		apiHelper = new APIHelper();
		sessionManager = new SessionManager(databaseHelper, apiHelper);

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
	void createGenericSession() throws Exception {
		int optionsCount = 3;
		List<String> options = populateOptionList(optionsCount);

		JSONObject jsonObject = new JSONObject(sessionManager.createGenericSession(options));
		int sessionID = jsonObject.getInt("sessionID");

		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT SessionID, SessionType FROM Session";
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				assertTrue(resultSet.getInt("SessionID") == sessionID);
				assertTrue(resultSet.getInt("SessionType") == 0);
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
	void createFilmSession() throws Exception {
		List<Integer> genres = new ArrayList<>();

		genres.add(28);

		JSONObject jsonObject = new JSONObject(sessionManager.createFilmSession(genres));
		int sessionID = jsonObject.getInt("sessionID");

		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM Session";
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				int candidateSessionID = resultSet.getInt("SessionID");
				int candidateSessionType = resultSet.getInt("SessionType");
				assertTrue(candidateSessionID == sessionID);
				assertTrue(candidateSessionType == 1);
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
    void newVote() throws Exception {
		int optionsCount = 3;
		int optionID = 0;
		List<String> optionList = populateOptionList(optionsCount);

		int sessionID = new JSONObject(sessionManager.createGenericSession(optionList)).getInt("sessionID");;
		sessionManager.newVote(sessionID, optionID);

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
    void delVote() throws Exception {
		int optionsCount = 3;
		int optionID = 0;
		List<String> optionList = populateOptionList(optionsCount);

		int sessionID = new JSONObject(sessionManager.createGenericSession(optionList)).getInt("sessionID");;
		sessionManager.newVote(sessionID, optionID);
		sessionManager.newVote(sessionID, optionID);
		sessionManager.delVote(sessionID, optionID);

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
	void getSessionInfo() throws Exception {
		int optionsCount = 2;
		List<String> optionList = populateOptionList(optionsCount);

		int sessionID = new JSONObject(sessionManager.createGenericSession(optionList)).getInt("sessionID");
		String expected = """
		{
		  "sessionID": 804499,
		  "isFilmSession": false,
		  "options": [
			{
			  "optionID": 0,
			  "description": "Option 0",
			  "voteTally": 0
			},
			{
			  "optionID": 1,
			  "description": "Option 1",
			  "voteTally": 0
			}
		  ]
		}
		""";
		JSONObject expectedJSON = new JSONObject(expected);

		JSONObject candidateJSON = new JSONObject(sessionManager.getSessionInfo(sessionID));

		assertTrue(expectedJSON.equals(candidateJSON));
	}

	@Test
	void endSession() throws Exception {
		int optionsCount = 3;

		List<String> optionList = populateOptionList(optionsCount);

		try {
			JSONObject session = new JSONObject(sessionManager.createGenericSession(optionList));

			int sessionID = session.getInt("sessionID");

			JSONObject endSession = new JSONObject(sessionManager.endSession(sessionID));

			JSONArray options = new JSONArray(endSession.getJSONArray("options"));

			for (int i = 0; i < optionsCount; i++) {
				JSONObject option = options.getJSONObject(i);
				int optionID = option.getInt("optionID");
				String description = option.getString("description");
				int voteTally = option.getInt("voteTally");

				assertTrue(optionID == i);
				assertTrue(description.compareTo("Option " + i) == 0);
				assertTrue(voteTally == 0);
			}

		}
		catch (Exception exception) {
			System.out.println(exception);
			assertTrue(false);
		}
	}
}
