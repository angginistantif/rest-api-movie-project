package project.restapimovie.service;

import project.restapimovie.model.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieUnitTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MovieService movieService;

    @Test
    public void checkAllMovie () {
	List<Movie> movie = movieService.getAllMovies();
	assertThat(movie).size().isGreaterThan(0);
    }

    @Test /**read a movie by valid movie id**/
    public void checkOneMovieAvailable () {
	Optional<Movie> movie = movieService.getMovieById(25);
    assertTrue(movie.isPresent());
    assertEquals(Long.valueOf(25), movie.get().getId());
    assertEquals("Arrival", movie.get().getMovie());
    assertEquals("(II) (2016)", movie.get().getYear());
    assertEquals("\r\nDrama, Sci-Fi            ", movie.get().getGenre());
    assertEquals("7.9", movie.get().getRating());
    assertEquals("623,912", movie.get().getVotes());
    assertEquals("\r\n    Director:\r\nDenis Villeneuve\r\n| \r\n    Stars:\r\nAmy Adams, \r\nJeremy Renner, \r\nForest Whitaker, \r\nMichael Stuhlbarg\r\n", movie.get().getStars());
    assertEquals("\r\nA linguist works with the military to communicate with alien lifeforms after twelve mysterious spacecraft appear around the world.", movie.get().getOneLine());
    assertEquals("116", movie.get().getRuntime());
    assertEquals("$100.55M", movie.get().getGross());
    }

    @Test /**read a movie by invalid movie id**/
    public void checkOneMovieNonAvailable ()  {
	Optional<Movie> movie = movieService.getMovieById(0);
    assertTrue(movie.isEmpty());
    }

     @Test /**add new movie valid**/
    public void addValidMovie() {
        Movie movie = new Movie();
        movie.setMovie("Squid Game");
        movie.setYear("2021");
        movie.setGenre("Comedy");
        movie.setRating("9.9");
        movie.setRuntime("100 minutes");
        movie.setGross("$100");
        movie.setOneLine("Lorem ipsum dolor");
        movie.setStars("Lorem ipsum");
        movie.setVotes("100");
        Movie newMovieData = movieService.saveMovie(movie).get();
        assertEquals(newMovieData.getMovie(), movie.getMovie());
        assertEquals(newMovieData.getYear(), movie.getYear());
        assertEquals(newMovieData.getGenre(), movie.getGenre());
        assertEquals(newMovieData.getGross(), movie.getGross());
        assertEquals(newMovieData.getVotes(), movie.getVotes());
        assertEquals(newMovieData.getRating(), movie.getRating());
        assertEquals(newMovieData.getOneLine(), movie.getOneLine());
        assertEquals(newMovieData.getStars(), movie.getStars());
        assertEquals(newMovieData.getRuntime(), movie.getRuntime());
    }

    @Test  /**add new movie by null movie data**/
    public void addInvalidMovie(){
        Movie movie = new Movie();
        movieService.saveMovie(movie);
        Optional<Movie> newMovie = movieService.getMovieById(465);
        assertTrue(newMovie.isEmpty());
    }

    @Test  /**delete movie by valid movie id (from new data)**/
    public void deleteValidMovie(){
        Movie movie = new Movie();
        movie.setMovie("Squid Game467");
        movie.setYear("2021");
        movie.setGenre("Comedy");
        movie.setRating("9.9");
        movie.setRuntime("100 minutes");
        movie.setGross("$100");
        movie.setOneLine("Lorem ipsum dolor");
        movie.setStars("Lorem ipsum");
        movie.setVotes("100");
        Movie newMovieData = movieService.saveMovie(movie).get();
        assertEquals(newMovieData.getMovie(), movie.getMovie());
        movieService.deleteMovie(newMovieData.getId());
    }

    @Test /**delete movie by invalid movie id**/
    public void deleteInvalidMovie(){
        movieService.deleteMovie(700);
    }

    @Test /**update movie by valid movie data with existing movie id**/
    public void updateValidMovie()  {
        Movie newMovieData = new Movie();
        newMovieData.setMovie("Squid Game Updated");
        newMovieData.setYear("2021 Updated");
        newMovieData.setGenre("Comedy Updated");
        newMovieData.setRating("9.9 Updated");
        newMovieData.setRuntime("100 minutes Updated");
        newMovieData.setGross("$100 Updated");
        newMovieData.setOneLine("Lorem ipsum dolor Updated");
        newMovieData.setStars("Lorem ipsum Updated");
        newMovieData.setVotes("100 Updated");
        movieService.updateMovie(newMovieData, 461);
        Movie updatedMovie = movieService.getMovieById(461).get();
        assertEquals(updatedMovie.getMovie(), newMovieData.getMovie());
        assertEquals(updatedMovie.getYear(), newMovieData.getYear());
        assertEquals(updatedMovie.getGenre(), newMovieData.getGenre());
        assertEquals(updatedMovie.getGross(), newMovieData.getGross());
        assertEquals(updatedMovie.getVotes(), newMovieData.getVotes());
        assertEquals(updatedMovie.getRating(), newMovieData.getRating());
        assertEquals(updatedMovie.getOneLine(), newMovieData.getOneLine());
        assertEquals(updatedMovie.getStars(), newMovieData.getStars());
        assertEquals(updatedMovie.getRuntime(), newMovieData.getRuntime());
    }

    @Test   /**update movie by null movie data**/
    public void updateNullDataMovie()  {
        Movie newMovieData = new Movie();
        movieService.updateMovie(newMovieData, 461);
    }

    @Test   /**update movie by null movie id**/
    public void updateNullIDMovie()  {
        Movie newMovieData = new Movie();
        newMovieData.setMovie("Squid Game Updated");
        newMovieData.setYear("2021 Updated");
        newMovieData.setGenre("Comedy Updated");
        newMovieData.setRating("9.9 Updated");
        newMovieData.setRuntime("100 minutes Updated");
        newMovieData.setGross("$100 Updated");
        newMovieData.setOneLine("Lorem ipsum dolor Updated");
        newMovieData.setStars("Lorem ipsum Updated");
        newMovieData.setVotes("100 Updated");
        movieService.updateMovie(newMovieData, 600);
    }
    
}

