package project.restapimovie.controller;

import java.util.*;
import project.restapimovie.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import project.restapimovie.model.Movie;
import project.restapimovie.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
    public static String URI = "/api/movies/";
    
    private MovieService movieService;

	public MovieController(MovieService movieService) {
		super();
		this.movieService = movieService;
	}
	
	// build create employee REST API
	@PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
	public Optional<Movie> saveEmployee(@RequestBody Movie movie) throws CustomException{
        Optional<Movie> newData = movieService.saveMovie(movie);
        if (newData.isPresent()){
            return newData;
        } else{
            throw new CustomException( HttpStatus.BAD_REQUEST.value(),"Your request has issued a malformed or illegal request.");        
        }	
	}
	
	
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> fetchAllMembers() {
        return movieService.getAllMovies();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Movie> findMemberById(@PathVariable("id") Long id) throws CustomException {
        if ( movieService.getMovieById(id).isPresent()){
            return movieService.getMovieById(id);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Movie with ID: '" + id + "' not found.");
        }
	}

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Movie> updateMember(@RequestBody @Validated Movie movie) throws CustomException {
        Optional<Movie> updateData = movieService.updateMovie(movie, movie.getId());
		if (updateData.isPresent()) {
            return movieService.updateMovie(movie, movie.getId());
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Movie with ID: '" + movie.getId() + "' not found.");
        }
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> removeMember(@PathVariable("id") long id) throws CustomException {

		try {
			this.movieService.deleteMovie(id);
            return new ResponseEntity<>(
                    new CustomResponse(HttpStatus.OK.value(),
                            "Movie with ID: '" + id + "' deleted."), HttpStatus.OK);                                                                                                                                                                
		} catch (NoSuchElementException e) {
			throw new CustomException(HttpStatus.NOT_FOUND.value(),"Movie with ID: '" + id + "' not found.");
		}
    }

	
}