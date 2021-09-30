package project.restapimovie.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="movies")
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "movies", nullable = false)
	private String movies;
	
	@Column(name = "year", nullable = false)
	private String year;
	
	@Column(name = "genre", nullable = false)
	private String genre;

    @Column(name = "rating", nullable = false)
	private String rating;

    @Column(name = "one_line", nullable = false)
	private String one_line;

    @Column(name = "stars", nullable = false)
	private String stars;

    @Column(name = "votes", nullable = false)
	private String votes;

    @Column(name = "runtime", nullable = false)
	private String runtime;

    @Column(name = "gross", nullable = false)
	private String gross;

    public void setMovie(String movie){
        this.movies = movies;
    }

    public void setYear(String year){
        this.year = year;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public void setRating(String rating){
        this.rating = rating;
    }

    public void setOneLine(String one_line){
        this.one_line = one_line;
    }

    public void setStars(String stars){
        this.stars = stars;
    }

    public void setVotes(String votes){
        this.votes = votes;
    }

    public void setRuntime (String runtime){
        this.runtime = runtime;
    }

    public void setGross(String gross){
        this.gross = gross;
    }
    
    public Long getId() {
        return this.id;
    }

    public String getMovie() {
        return this.movies;
    }

    public String getYear() {
        return this.year;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getRating() {
        return this.rating;
    }

    public String getOneLine() {
        return this.one_line;
    }

    public String getStars() {
        return this.stars;
    }

    public String getVotes() {
        return this.votes;
    }

    public String getRuntime() {
        return this.runtime;
    }

    public String getGross() {
        return this.gross;
    }

}