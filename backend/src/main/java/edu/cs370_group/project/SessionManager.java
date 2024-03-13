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
	}

	public String endSession(int sessionID) throws Exception {
		String response = "{\n";

//		response += "\t\"consensus\": false,\n";
//		response += getOptionsJSON(sessionID);
//		response += "}";
//		databaseHelper.endSession(sessionID);
//		return response;

		double majority = 0.60;
		int tallyTotal = 0;
		List<Integer> options = databaseHelper.getOptions(sessionID);
		List<Integer[]> optionsTallies = new ArrayList<Integer[]>();
		Integer[] greatestOption = new Integer[2];
		
		for (int optionID : options) {
			int tally = databaseHelper.getOptionVoteTally(sessionID, optionID);
			Integer tallyMap[] = new Integer[2];
			tallyMap[0] = Integer.valueOf(optionID);
			tallyMap[1] = Integer.valueOf(tally);
			optionsTallies.add(tallyMap);
			tallyTotal += tally;
		}
		
		greatestOption[0] = optionsTallies.get(0)[0];
		greatestOption[1] = optionsTallies.get(0)[1];

		for (Integer[] tallyMap : optionsTallies) {
			int optionID = tallyMap[0];
			int optionTally = tallyMap[1];
			if ((optionTally / tallyTotal) >= majority) {
				response += "\t\"consensus\": true,\n";
				response += getOptionsJSON(sessionID);
				response += "}";
				databaseHelper.endSession(sessionID);
				return response;
			}
			if (optionTally > greatestOption[1]) {
				greatestOption[0] = optionID;
				greatestOption[1] = optionTally;
			}
		}

		if (databaseHelper.isFilmSession(sessionID)) {
			// Uncomment the below block once APIHelper.getSimilar and APIHelper.getFilmTitle is implemented


/*			List<Map<Integer, String>> newFilmList = new ArrayList<Map<Integer, String>>();
			for (Integer[] tallyMap : optionsTallies) {
				int similarPortion = ((tallyMap[1] / tallyTotal) * listTotal);
				List<Integer> similarList = apiHelper.getSimilar(tallyMap[0]);
				for (int i = 0; i < similarPortion; i++) {
					Map <Integer, String> tempMap = new HashMap<Integer, String>();
					tempMap.put(similarList.get(i), apiHelper.getFilmTitle(similarList.get(i)));
					newFilmList.add(tempMap);
					if ((i + 1) >= similarList.size()) {
						break;
					}
				}
			}
			databaseHelper.setOptions(sessionID, newFilmList);
*/
			response += "\t\"consensus\": false,\n";
			response += getOptionsJSON(sessionID);
			response += "}";
			return response;
		}
		response += "\t\"consensus\": true,\n";
		response += "\t\"winningOption\": " + greatestOption[0] + "\n";
		response += getOptionsJSON(sessionID);
		response += "}";
		databaseHelper.endSession(sessionID);
		return response;
	}

	// Create new session with unique ID
	private int createSession(List<Map<Integer, String>> options, Boolean isFilmSession) {
		Random randomNumber = new Random();
		int sessionID = randomNumber.nextInt(999999);
		databaseHelper.createSession(sessionID, options, isFilmSession);
		return sessionID;
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
