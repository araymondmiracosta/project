package edu.cs370_group.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class APIHelperTest {
	APIHelper apiHelper;

    @BeforeEach
    void setUp() {
		apiHelper = new APIHelper();
    }
//
//	@AfterEach
//    void tearDown() {
//    }	

	@Test
    void getFilmList() throws Exception {
		List<Integer> list = new ArrayList<>();

		list.add(28);

		assertTrue(!(apiHelper.getFilmList(list).isEmpty()));
    }

	@Test
	void getFilmTitle() throws Exception {
		String expected = "Ant-Man and the Wasp: Quantumania";

		int filmID = 640146;

		String candidate = apiHelper.getFilmTitle(filmID);

		assertTrue(expected.compareTo(candidate) == 0);
	}

	@Test
	void getSimilar() throws Exception {
		int filmID = 640146;
		assertTrue(!(apiHelper.getSimilar(filmID).isEmpty()));
	}

	@Test
	void getGenreList() throws Exception {
		assertTrue(!(apiHelper.getGenreList().isEmpty()));
	}
}
