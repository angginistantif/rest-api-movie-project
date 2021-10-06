package project.restapimovie.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import project.restapimovie.model.Movie;
import project.restapimovie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;


@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

	@Override
	public Optional<Movie> saveMovie(Movie movie) {
		try {
			return Optional.of(movieRepository.save(movie));
		} catch (DataIntegrityViolationException e){
			return null;
		}
		
	}

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Optional<Movie> getMovieById(long id) {
		return movieRepository.findById(id);	
	}

	@Override
	public Optional<Movie> updateMovie (Movie movie, long id) {
		try {
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
			return Optional.of(movieRepository.save(existingMovie));
		} catch (Exception e) {
            return null;
        }
        
	}

	@Override
	public void deleteMovie(long id) {
		try {
			Movie movie = movieRepository.findById(id).get();
        	movieRepository.delete(movie);
		} catch (Exception e){
			return;
		}
        
	}
    
}
