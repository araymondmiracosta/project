package edu.cs370_group.project;

import java.util.List;
import java.util.Scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.*;

class APIHelper {
	private String apiToken = "";

	public APIHelper() {
		// Read the api key from a file
		File file = new File("./apitoken.txt");
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			Scanner scanner = new Scanner(fileInputStream);
			this.apiToken = scanner.nextLine();
			scanner.close();
			fileInputStream.close();
		}
		catch (Exception exception) {
			System.out.println(exception);
			System.out.println("Is the API token file present? Exiting...");
			System.exit(1);
		}
	}
	/** 
	 * Returns a List<Integer> containing the film IDs
	 * associated with the given genres.
	 *
	 * API documentation (see "with_genres" request attribute):
	 * https://developer.themoviedb.org/reference/discover-movie
	 *
	 * @param genres A List<Integer> containing the genre IDs
	 * 				 of movies to search for
	 *
	 * @return List<Integer> The list of films
	*/
	public List<Integer> getFilmList(List<Integer> genres) throws Exception { 	// Throws anything
		List<Integer> list = new ArrayList<Integer>();

		int perGenre = 20 / genres.size();

		try {
			for (Integer genre : genres) {
				URI endpointURI = new URI("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_genres=" + genre + "&api_key=" + this.apiToken);
				URLConnection endpointConnection = endpointURI.toURL().openConnection();
				InputStream response = endpointConnection.getInputStream();
				Scanner inputScanner = new Scanner(response);
				JSONObject jsonObject = new JSONObject(inputScanner.nextLine());
				JSONArray resultsArray = new JSONArray(jsonObject.getJSONArray("results"));
				for (int i = 0; i < perGenre; i++) {
					// Add the ith movie from the returned APIHelper
					JSONObject film = resultsArray.getJSONObject(i);
					int id = film.getInt("id");
					list.add(id);
				}
				inputScanner.close();
				response.close();
			}
		}
		catch (Exception exception) {
			System.out.println("Invalid genre code.");
		}
		
		return list;
	}

	/**
	 * Returns the film title associated with the given
	 * film ID.
	 *
	 * API documentation (see "title" JSON attribute):
	 * https://developer.themoviedb.org/reference/movie-details
	 *
	 * @param filmID The film ID
	 *
	 * @return The film title
	*/
	public String getFilmTitle(int filmID) throws Exception {
		// Parse the JSON and look for the title attribute

		URI endpointURI = new URI("https://api.themoviedb.org/3/movie/" + filmID + "?language=en-US" + "&api_key=" + this.apiToken);
		URLConnection endpointConnection = endpointURI.toURL().openConnection();
		InputStream response = endpointConnection.getInputStream();
		Scanner inputScanner = new Scanner(response);
		JSONObject jsonObject = new JSONObject(inputScanner.nextLine());
		String filmTitle = jsonObject.getString("title");
		
		inputScanner.close();
		response.close();

		return filmTitle;
	}

	/**
	 * Returns a java List<Integer> object containing
	 * film IDs similar to the one given.
	 *
	 * API documentation (see "id" JSON attribute):
	 * https://developer.themoviedb.org/reference/movie-similar
	 *
	 * @param filmID The film ID
	 * 
	 * @return A List<Integer> containing the film IDs
	*/
	public List<Integer> getSimilar(int filmID) {
		List<Integer> list = new ArrayList<Integer>();

		return list;
	}

	/**
	 * Returns a JSON array of genre IDs and their associated
	 * names.
	 *
	 * API documentation: 
	 * https://developer.themoviedb.org/reference/genre-movie-list
	 *
	 * @return A JSON array of genres
	*/
	public String getGenreList() {
		// Return the response, unedited, from the API
		return "";
	}
}
