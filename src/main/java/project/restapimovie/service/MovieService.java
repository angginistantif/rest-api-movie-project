package project.restapimovie.service;

import java.util.List;
import project.restapimovie.model.Movie;

public interface MovieService {
    Movie saveMovie(Movie movie);
	List<Movie> getAllMovies();
	Movie getMovieById(long id);
	Movie updateMovie(Movie movie, long id);
	void deleteMovie(long id);
}
