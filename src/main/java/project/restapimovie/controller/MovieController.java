package project.restapimovie.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.restapimovie.model.Movie;
import project.restapimovie.service.MovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
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
	
	// build get all employees REST API
	@GetMapping
	public List<Movie> getAllMovies(){
		return movieService.getAllMovies();
	}
	
	// build get employee by id REST API
	// http://localhost:8080/api/employees/1
	@GetMapping("{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable("id") long employeeId){
		return new ResponseEntity<Movie>(movieService.getMovieById(employeeId), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Movie> updateEmployee(@PathVariable("id") long id
												  ,@RequestBody Movie movie){
		return new ResponseEntity<Movie>(movieService.updateMovie(movie, id), HttpStatus.OK);
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable("id") long id){
				movieService.deleteMovie(id);
		return new ResponseEntity<String>("Movie deleted successfully!.", HttpStatus.OK);
	}
	
}