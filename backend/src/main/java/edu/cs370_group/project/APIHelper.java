package edu.cs370_group.project;

import java.util.List;
import java.util.ArrayList;

class APIHelper {
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
	public List<Integer> getFilmList(List<Integer> genres) {
		List<Integer> list = new ArrayList<Integer>();

		int perGenre = 20 / genres.size();

		// Get list of movies from API
		for (Integer genre : genres) {
			for (int i = 0; i < perGenre; i++) {
				// Add the ith movie from the returned API
			}
		}
		
		return list;
	}

	/**
	 * Returns the film title associated with the given
	 * film ID.
	 *
	 * API documentation (see "original_title" JSON attribute):
	 * https://developer.themoviedb.org/reference/movie-details
	 *
	 * @param filmID The film ID
	 *
	 * @return The film title
	*/
	public String getFilmTitle(int filmID) {
		// Parse the JSON and look for the original_title attribute
		return "";
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
