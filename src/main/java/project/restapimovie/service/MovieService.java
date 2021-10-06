package project.restapimovie.service;

import java.util.List;
import java.util.Optional;

import project.restapimovie.model.Movie;

public interface MovieService {
    Movie saveMovie(Movie movie);
	List<Movie> getAllMovies();
	Optional<Movie> getMovieById(long id);
	Movie updateMovie(Movie movie, long id);
	void deleteMovie(long id);
}
