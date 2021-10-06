package project.restapimovie.service;

import java.util.List;
import java.util.Optional;

import project.restapimovie.model.Movie;

public interface MovieService {
    Optional<Movie> saveMovie(Movie movie);
	List<Movie> getAllMovies();
	Optional<Movie> getMovieById(long id);
	Optional<Movie> updateMovie(Movie movie, long id);
	void deleteMovie(long id);
}
