package project.restapimovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.restapimovie.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

}
