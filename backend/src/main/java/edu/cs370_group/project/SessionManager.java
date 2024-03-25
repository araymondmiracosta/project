package edu.cs370_group.project;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

class SessionManager {
	private DatabaseHelper databaseHelper;
	private APIHelper apiHelper;
	public final int listTotal = 20;

	// Controller provides DatabaseHelper object
	public SessionManager(DatabaseHelper databaseHelper, APIHelper apiHelper) throws Exception {
		this.databaseHelper = databaseHelper;
		this.apiHelper = apiHelper;
		
		// Testing
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		list.add(28);
//		list.add(16);
//		list.add(18);
//		for (Integer item : apiHelper.getFilmList(list)) {
//			System.out.println(item + "  :  " + apiHelper.getFilmTitle(item));
//		}
//		System.exit(0);
	}

	public String endSession(int sessionID) {
		String response = "{\n";
		double majority = 0.60;

		// It is a generic session
		if (!(databaseHelper.isFilmSession(sessionID))) {
			response += "\t\"consensus\": true,\n";
			response += getOptionsJSON(sessionID);
			response += "}";
			databaseHelper.endSession(sessionID);
			return response;
		}
		// It is a film session
		else {
			int tallyTotal = 0;
			List<Integer> options = databaseHelper.getOptions(sessionID);
			Integer[] greatestOption = new Integer[2];
			// Initialize greatestOption to the values of the first option for this session
			greatestOption[0] = options.get(0);
			greatestOption[1] = databaseHelper.getOptionVoteTally(sessionID, greatestOption[0]);

			for (int optionID : options) {
				int tally = databaseHelper.getOptionVoteTally(sessionID, optionID);
				// If this option has more votes than the previous one, replace the old record
				if (tally > greatestOption[1]) {
					greatestOption[0] = optionID;
					greatestOption[1] = tally;
				}
				tallyTotal += tally;
			}

			double highestRatio = greatestOption[1] / tallyTotal;

			// If an option recieved at least 60% of the votes, return the options
			if (highestRatio >= majority) {
				response += "\t\"consensus\": true,\n";
				response += getOptionsJSON(sessionID);
				response += "}";
				databaseHelper.endSession(sessionID);
				return response;
			}
			// If no option recieved at least 60% of the votes, revote
			else {
				List<Map<Integer, String>> newFilmList = new ArrayList<Map<Integer, String>>();
				// Get films similar to the highest rated one
				List<Integer> similarList = apiHelper.getSimilar(greatestOption[0]);
				int count = 0;
				for (Integer newOption : similarList) {
					if (count > listTotal) {
						break;
					}
					Map<Integer, String> filmMap = new HashMap<Integer, String>();
					filmMap.put(newOption, apiHelper.getFilmTitle(newOption));
					newFilmList.add(filmMap);
					count++;
				}
				// Set the new options
				databaseHelper.setOptions(sessionID, newFilmList);

				response += "\t\"consensus\": false,\n";
				response += getOptionsJSON(sessionID);
				response += "}";
				return response;
			}
		}
	}

	// Create new session with unique ID
	private int createSession(List<Map<Integer, String>> options, Boolean isFilmSession) {
		Random randomNumber = new Random();
		int sessionID = randomNumber.nextInt(999999);
		databaseHelper.createSession(sessionID, options, isFilmSession);
		return sessionID;
	}

	public String getGenreList() {
		return (apiHelper.getGenreList());
	}

	public String createGenericSession(List<String> options) {
		List<Map<Integer, String>> optionList = new ArrayList<Map<Integer, String>>();
		for (int i = 0; i < options.size(); i++) {
			Map<Integer, String> map = new HashMap<Integer, String>();
			map.put(Integer.valueOf(i), options.get(i));
			optionList.add(map);
		}
		int sessionID = createSession(optionList, false);

		String response = "";
		response = "{\n\t\"sessionID\": " + sessionID + "\n}";
		return response;
	}

	public String createFilmSession(List<Integer> genres) throws Exception {
		List<Integer> filmSelections = apiHelper.getFilmList(genres);
		List<Map<Integer, String>> options = new ArrayList<Map<Integer, String>>();
		for (Integer option : filmSelections) {
			Map<Integer, String> filmMap = new HashMap<Integer, String>();
			int filmCode = option;
			String filmTitle = apiHelper.getFilmTitle(filmCode);
			filmMap.put(filmCode, filmTitle);
			options.add(filmMap);
		}
		int sessionID = createSession(options, true);

		String response = "";
		response = "{\n\t\"sessionID\": " + sessionID + "\n}";
		return response;
	}

	public void newVote(int sessionID, int optionID) {
		databaseHelper.newVote(sessionID, optionID);
	}

	public void delVote(int sessionID, int optionID) {
 		databaseHelper.delVote(sessionID, optionID);
	}

	private String getOptionsJSON(int sessionID) {
		String response = "";

		List<Integer> optionList = databaseHelper.getOptions(sessionID);

		response += "\n\t\"options\": [\n";

		for (Integer option : optionList) {
			int voteTally = databaseHelper.getOptionVoteTally(sessionID, option);
			String optionDescription = databaseHelper.getOptionDescription(sessionID, option);


			response += "\t\t{\n\t\t\t\"optionID\": " + option.toString() + ",\n\t\t\t\"description\": \"" + optionDescription + "\",\n\t\t\t\"voteTally\": " + voteTally + "\n\t\t}";

			if (!(option.equals(optionList.getLast()))) {
				response += ",";
			}

			response += "\n";
		}
		response += "\t]\n";

		return response;
	}
	
	public String getSessionInfo(int sessionID) {
		String output = "";

		Boolean isFilmSession = databaseHelper.isFilmSession(sessionID);

		output += "{\n\t\"sessionID\": " + sessionID + ",\n\t\"isFilmSession\": " + isFilmSession.toString() + ",\n";

		output += getOptionsJSON(sessionID);
		output += "\n}";
		output += "\n";
		
		return output;
	}
}
