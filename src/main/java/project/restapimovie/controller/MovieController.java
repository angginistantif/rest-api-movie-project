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
	
    static String URI = "/api/movies/";
    
    private MovieService movieService;

	public MovieController(MovieService movieService) {
		super();
		this.movieService = movieService;
	}
	
	// build create employee REST API
	@PostMapping()
	public ResponseEntity<Movie> saveEmployee(@RequestBody Movie movie){
		return new ResponseEntity<Movie>(movieService.saveMovie(movie), HttpStatus.CREATED);
	}
	
	
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> fetchAllMembers() {
        return movieService.getAllMovies();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Movie> findMemberById(@PathVariable("id") Long id) throws CustomException {
		try {
			return Optional.of(movieService.getMovieById(id));                                                                                                                                                                        
		} catch (NoSuchElementException e) {
			throw new CustomException(HttpStatus.NOT_FOUND.value(),"Movie with ID: '" + id + "' not found.");
		}
	}

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Movie saveMember(@RequestBody @Validated Movie member) {
        return movieService.saveMovie(member);
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Movie updateMember(@RequestBody @Validated Movie movie) throws CustomException {
		if (movieService.getMovieById(movie.getId()) != null) {
            return movieService.saveMovie(movie);
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
		} catch (Exception e) {
			throw new CustomException(HttpStatus.NOT_FOUND.value(),"Movie with ID: '" + id + "' not found.");
		}
    }

	
}