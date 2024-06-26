package edu.cs370_group.project;

import java.util.List;
import java.util.Scanner;

import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.*;

class APIHelper {
	private String apiToken = "ff08d7c9ff8eb9db93d17e72e06f213c";
	private int listTotal = 20;

	public APIHelper() {
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

		int perGenre = listTotal / genres.size();

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
			throw new Exception("Invalid genre code");
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
	public String getFilmTitle(int filmID) {
		// Parse the JSON and look for the title attribute

		String filmTitle = "";
		try {
			URI endpointURI = new URI("https://api.themoviedb.org/3/movie/" + filmID + "?language=en-US" + "&api_key=" + this.apiToken);
			URLConnection endpointConnection = endpointURI.toURL().openConnection();
			InputStream response = endpointConnection.getInputStream();
			Scanner inputScanner = new Scanner(response);
			JSONObject jsonObject = new JSONObject(inputScanner.nextLine());
			filmTitle = jsonObject.getString("title");
			
			inputScanner.close();
			response.close();
		}
		catch (Exception exception) {
			System.out.println("Invalid film ID");
		}

		return filmTitle;
	}

	public String getFilm(int filmID) {
		// Parse the JSON and look for the title attribute

		String apiResponse = "";
		try {
			URI endpointURI = new URI("https://api.themoviedb.org/3/movie/" + filmID + "?language=en-US" + "&api_key=" + this.apiToken);
			URLConnection endpointConnection = endpointURI.toURL().openConnection();
			InputStream response = endpointConnection.getInputStream();
			Scanner inputScanner = new Scanner(response);
			JSONObject jsonObject = new JSONObject(inputScanner.nextLine());
			apiResponse = jsonObject.toString();

			inputScanner.close();
			response.close();
		}
		catch (Exception exception) {
			System.out.println("Invalid film ID");
		}

		return apiResponse;
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
	public List<Integer> getSimilar(int filmID) throws Exception {
		List<Integer> list = new ArrayList<Integer>();

		try{
			URI endpointURI = new URI("https://api.themoviedb.org/3/movie/" + filmID + "/similar?language=en-US&api_key=" + this.apiToken);
			URLConnection endpointConnection = endpointURI.toURL().openConnection();
			InputStream response = endpointConnection.getInputStream();
			Scanner inputScanner = new Scanner(response);
			StringBuilder responseData = new StringBuilder();
			while(inputScanner.hasNextLine()){
				responseData.append(inputScanner.nextLine());
			}
			inputScanner.close();
			response.close();

			JSONObject jsonResponse = new JSONObject(responseData.toString());
			JSONArray resultsArray = jsonResponse.getJSONArray("results");

			for(int i = 0; (i < resultsArray.length() && i < listTotal); i++){
				JSONObject movieObject = resultsArray.getJSONObject(i);
				int id = movieObject.getInt("id");
				list.add(id);
			}

		}catch (Exception e) {
			throw new Exception(e);
		}

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
	public String getGenreList() throws Exception {
		// Return the response, unedited, from the API
		StringBuilder genreListResponse = new StringBuilder();

		try {
			URI endpointURI = new URI("https://api.themoviedb.org/3/genre/movie/list?language=en-US&api_key=" + this.apiToken);
			URLConnection endpointConnection = endpointURI.toURL().openConnection();
			InputStream response = endpointConnection.getInputStream();
			Scanner inputScanner = new Scanner(response);

			while (inputScanner.hasNextLine()) {
				genreListResponse.append(inputScanner.nextLine());
			}

			inputScanner.close();
			response.close();
		} catch (Exception e) {
			throw new Exception("Error retrieving genre list: " + e.getMessage());
		}

		return genreListResponse.toString();
	}
}
