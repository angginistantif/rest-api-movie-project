package project.restapimovie.service;

import java.util.List;
import org.springframework.stereotype.Service;
import project.restapimovie.model.Movie;
import project.restapimovie.repository.MovieRepository;
import project.restapimovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

	@Override
	public Movie saveMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Movie getMovieById(long id) {
        Movie movie = movieRepository.findById(id).get();
        return movie;		
	}

	@Override
	public Movie updateMovie (Movie movie, long id) {
        Movie existingMovie = movieRepository.findById(id).get();
		
        existingMovie.setMovie(movie.getMovie());
		existingMovie.setYear(movie.getYear());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setRating(movie.getRating());
        existingMovie.setOneLine(movie.getOneLine());
        existingMovie.setStars(movie.getStars());
        existingMovie.setVotes(movie.getVotes());
        existingMovie.setRuntime(movie.getRuntime());
        existingMovie.setGross(movie.getGross());
		movieRepository.save(existingMovie);
		return existingMovie;
	}

	@Override
	public void deleteMovie(long id) {
        Movie movie = movieRepository.findById(id).get();
        movieRepository.delete(movie);
	}
    
}
