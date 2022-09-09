package sshukla.learning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sshukla.learning.entity.Movie;
import sshukla.learning.service.impl.MovieServiceImpl;

import java.util.Map;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */

@Slf4j
@RestController
@RequestMapping("/v1/movie")
public class MovieController {

    private MovieServiceImpl movieServiceImpl;

    @Autowired
    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    @PostMapping("/{tableName}/{partitionKey}/{sortKey}")
    public ResponseEntity<Movie> createMovieTable(@PathVariable String tableName, @PathVariable String partitionKey, @PathVariable String sortKey) {
        movieServiceImpl.createMovieTable(tableName, partitionKey, sortKey);
        return (ResponseEntity) new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @GetMapping("/all/")
    public ResponseEntity<Map<String, Object>> getAllMovies() {
        return (ResponseEntity) new ResponseEntity<Object>(HttpStatus.CREATED);
    }
}
