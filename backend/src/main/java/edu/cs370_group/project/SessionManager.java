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
		response += "\t\"consensus\": false,\n";
		response += getOptionsJSON(sessionID);
		response += "}";
		databaseHelper.endSession(sessionID);
		return response;
//		double majority = 0.60;
//		int tallyTotal = 0;
//		List<Integer> options = databaseHelper.getOptions(sessionID);
//		List<Integer[]> optionsTallies = new ArrayList<Integer[]>();
//		Integer[] greatestOption = new Integer[2];
//		
//		for (int optionID : options) {
//			int tally = databaseHelper.getOptionVoteTally(sessionID, optionID);
//			Integer tallyMap[] = new Integer[2];
//			tallyMap[0] = Integer.valueOf(optionID);
//			tallyMap[1] = Integer.valueOf(tally);
//			optionsTallies.add(tallyMap);
//			tallyTotal += tally;
//		}
//		
//		greatestOption[0] = optionsTallies.get(0)[0];
//		greatestOption[1] = optionsTallies.get(0)[1];
//
//		for (Integer[] tallyMap : optionsTallies) {
//			int optionID = tallyMap[0];
//			int optionTally = tallyMap[1];
//			if ((optionTally / tallyTotal) >= majority) {
//				response += "\t\"consensus\": true,\n"
//				response += getOptionsJSON(sessionID);
//				response += "}";
//				databaseHelper.endSession(sessionID);
//				return response;
//			}
//			if (optionTally > greatestOption[1]) {
//				greatestOption[0] = optionID;
//				greatestOption[1] = optionTally;
//			}
//		}
//
//		if (databaseHelper.isFilmSession(sessionID)) {
//			List<Map<Integer, String>> newFilmList = new ArrayList<Map<Integer, String>>();
//			for (Integer[] tallyMap : optionsTallies) {
//				int similarPortion = ((tallyMap[1] / tallyTotal) * listTotal);
//				List<Integer> similarList = apiHelper.getSimilar(tallyMap[0]);
//				for (int i = 0; i < similarPortion; i++) {
//					Map <Integer, String> tempMap = new HashMap<Integer, String>();
//					tempMap.put(similarList.get(i), apiHelper.getFilmTitle(similarList.get(i)));
//					newFilmList.add(tempMap);
//					if ((i + 1) >= similarList.size()) {
//						break;
//					}
//				}
//			}
//			databaseHelper.setOptions(sessionID, newFilmList);
//			response += "\t\"consensus\": false,\n"
//			response += getOptionsJSON(sessionID);
//			response += "}";
//			databaseHelper.endSession(sessionID);
//			return response;
//		}
//		response += "\t\"consensus\": true,\n"
//		response += "\t\"winningOption\": " + greatestOption[0] + "\n";
//		response += getOptionsJSON(sessionID);
//		response += "}";
//		databaseHelper.endSession(sessionID);
//		return response;

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
// 		databaseHelper.delVote(sessionID, optionID);
	}

//	public int getVoteTally(int sessionID, int optionID) {
//		return (databaseHelper.getVoteTally(sessionID, optionID));
//	}
	
	public String getOptionsJSON(int sessionID) {
		String response = "";

//		List<Integer> optionList = databaseHelper.getOptions(sessionID);
		List<Integer> optionList = new ArrayList<Integer>();

		for (int i = 0; i < 2; i++) {
			optionList.add(Integer.valueOf(i));
		}

		response += "\n\t\"options\": [\n";

		for (Integer option : optionList) {
			int voteTally = databaseHelper.getOptionVoteTally(sessionID, option);
//			String optionDescription = databaseHelper.getOptionDescription(session, option);
			String optionDescription = "Description text";


			response += "\t\t{\n\t\t\t\"optionID\": " + option.toString() + ",\n\t\t\t\"description\": \"" + optionDescription + "\",\n\t\t\t\"voteTally\": " + voteTally + "\n\t\t}";

			if (!(option.equals(optionList.getLast()))) {
				response += ",";
			}

			response += "\n";
		}
		response += "\t]\n";

		return response;
	}
	
//	public String getSessionInfo(int sessionID) {
//		String output = "";
//
////		Boolean isFilmSession = databaseHelper.isFilmSession(sessionID);
//		Boolean isFilmSession = true;
//
////		List<Integer> optionList = databaseHelper.getOptions(sessionID);
//		List<Integer> optionList = new ArrayList<Integer>();
//
//		for (int i = 0; i < 2; i++) {
//			optionList.add(Integer.valueOf(i));
//		}
//
//		output += "{\n\t\"sessionID\": " + sessionID + ",\n\t\"isFilmSession\": " + isFilmSession.toString() + ",\n";
//
//		output += getOptionsJSON(sessionID);
//		output += "\n}";
//		output += "\n";
//
//		return output;
//	}

	public String getSessionInfo(int sessionID)  throws Exception {
		// Check if the sessionID is the correct one
		if (sessionID == 862656864) {
			// If the sessionID is correct, return the specified JSON string
			return "{\n" +
					"  \"sessionID\": 862656864,\n" +
					"  \"isFilmSession\": true,\n" +
					"  \"options\": [\n" +
					"    {\n" +
					"      \"optionID\": 0,\n" +
					"      \"description\": \"Description text\",\n" +
					"      \"voteTally\": 5\n" +
					"    },\n" +
					"    {\n" +
					"      \"optionID\": 1,\n" +
					"      \"description\": \"Description text\",\n" +
					"      \"voteTally\": 3\n" +
					"    }\n" +
					"  ]\n" +
					"}";
		} else {
		throw new Exception("Not Found");
		}
	}


/*	// Prints out information for each session and its options
	public String getSessions() {
		String output = "";
//		List<Integer> sessionList = databaseHelper.getSessions();
		List<Integer> sessionList = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			sessionList.add(Integer.valueOf(i));
		}

		output = "{\n    \"Sessions\": [\n";

		for (Integer session : sessionList) {
//			Boolean isFilmSession = databaseHelper.isFilmSession(session);
			Boolean isFilmSession = true;
//			List<Integer> optionList = databaseHelper.getOptions(session);
			List<Integer> optionList = new ArrayList<Integer>();
			for (int i = 0; i < 5; i++) {
				optionList.add(Integer.valueOf(i));
			}
			output += "        {\n            \"sessionID\": " + session.toString() + ",\n            \"isFilmSession\": " + isFilmSession.toString() + ",\n            \"options\": [\n";

			for (Integer option : optionList) {
//				int voteTally = databaseHelper.getOptionVoteTally(session, option);
				int voteTally = 5;
//				String optionDescription = databaseHelper.getOptionDescription(session, option);
				String optionDescription = "Description text";

				output += "                {\n                    \"optionID\": " + option.toString() + ",\n                    \"description\": \"" + optionDescription + "\",\n                    \"voteTally\": " + voteTally + "\n                }";

				if (!(option.equals(optionList.getLast()))) {
					output += ",";
				}
				output += "\n";
			}
			output += "            ]\n        }";
			if (!(session.equals(sessionList.getLast()))) {
				output += ",";
			}
			output += "\n";
		}
		output += "    ]\n}\n";
		
		return output;
	} */
}
