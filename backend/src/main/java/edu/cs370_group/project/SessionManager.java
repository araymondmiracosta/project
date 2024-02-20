package edu.cs370_group.project;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class SessionManager {
	private DatabaseHelper databaseHelper;
	private APIHelper apiHelper;
	public final int listTotal = 20;

	// Controller provides DatabaseHelper object
	public SessionManager(DatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}

	public int endSession(int sessionID) {
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
				return optionID;
			}
			if (optionTally > greatestOption[1]) {
				greatestOption[0] = optionID;
				greatestOption[1] = optionTally;
			}
		}

		if (databaseHelper.isFilmSession(sessionID)) {
			List<Map<Integer, String>> newFilmList = new ArrayList<Map<Integer, String>>();
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
			return -1;
		}
		return (greatestOption[0]);
	}

	// Create new session with unique ID
	private int createSession(List<Map<Integer, String>> options, Boolean isFilmSession) {
		int sessionID = 0;
		// databaseHelper.createSession(sessionID, options, isFilmSession);
		return sessionID;
	}

	public int createGenericSession(List<String> options) {
		List<Map<Integer, String>> optionList = new ArrayList<Map<Integer, String>>();
		for (int i = 0; i < options.size(); i++) {
			Map<Integer, String> map = new HashMap<Integer, String>();
			map.put(Integer.valueOf(i), options.get(i));
			optionList.add(map);
		}
		int sessionID = createSession(optionList, false);
		return sessionID;
	}

	public int createFilmSession(List<Integer> genres) {
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
		return sessionID;
	}

	public void newVote(int sessionID, int optionID) {
//		databaseHelper.newVote(sessionID, optionID);
	}

	public void delVote(int sessionID, int optionID) {
// 		databaseHelper.delVote(sessionID, optionID);
	}

//	public int getVoteTally(int sessionID, int optionID) {
//		return (databaseHelper.getVoteTally(sessionID, optionID));
//	}

	// Prints out information for each session and its options
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
	}
}
