package ilarkesto.imdb;

import ilarkesto.testng.ATest;

import org.testng.annotations.Test;

public class ImdbTest extends ATest {

	@Test
	public void loadRecord() {
		ImdbRecord r = Imdb.loadRecord("tt0266697");
		assertEquals(r.getTitle(), "Kill Bill: Vol. 1");
		assertEquals(r.getYear(), Integer.valueOf(2003));
		assertEquals(r.getCoverId(), "MV5BMTM3Mjk3MzUwN15BMl5BanBnXkFtZTcwMTgzMTYyMQ@@");
	}

	@Test
	public void determineIdByTitleNoGuess() {
		assertEquals(Imdb.determineIdByTitle("Kill Bill: Vol. 1", false), "tt0266697");
	}

	@Test
	public void determineIdByTitleGuess() {
		assertEquals(Imdb.determineIdByTitle("Seven Samurai", true), "tt0047478");
	}

}
