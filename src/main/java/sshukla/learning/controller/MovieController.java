package sshukla.learning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sshukla.learning.entity.Movie;
import sshukla.learning.service.impl.MovieServiceImpl;

import java.util.List;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */

@Slf4j
@RestController
@RequestMapping("/v1")
public class MovieController {

    private final MovieServiceImpl movieServiceImpl;

    @Autowired
    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    @PostMapping("/table/")
    public ResponseEntity<String> createMovieTable(@RequestParam String tableName, @RequestParam String partitionKey,
                                                   @RequestParam(required = false) String sortKey,
                                                   @RequestParam(required = false) String globalSecondaryIndex,
                                                   @RequestParam(required = false) String localSecondaryIndex) {
        String createdTable = movieServiceImpl.createTable(tableName, partitionKey, sortKey, globalSecondaryIndex, localSecondaryIndex);
        return new ResponseEntity<>("Table " + createdTable + ", is created", HttpStatus.CREATED);
    }

    @GetMapping("/table/all/")
    public ResponseEntity<List<String>> getAllTables() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/table/{tableName}/")
    public ResponseEntity<String> deleteTable(@PathVariable String tableName) {
        movieServiceImpl.deleteTable(tableName);
        return new ResponseEntity<>("Table " + tableName + ", is deleted", HttpStatus.OK);
    }

    @PostMapping("/movie/")
    public ResponseEntity<String> createMovie(@RequestParam String tableName, @RequestBody Movie movie) {
        movieServiceImpl.createMovie(tableName, movie);
        return new ResponseEntity<>("Movie " + movie.getTitle() + ", is created", HttpStatus.CREATED);
    }

    @PutMapping("/movie/update/")
    public ResponseEntity<String> updateMovie(@RequestParam String tableName, @RequestBody Movie movie) {
        movieServiceImpl.updateMovie(tableName, movie);
        return new ResponseEntity<>("Movie " + movie.getTitle() + ", is updated", HttpStatus.OK);
    }

    @GetMapping("/movie/all/")
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam String tableName) {
        List<Movie> movies = movieServiceImpl.getAllMovies(tableName);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PutMapping("/movie/")
    public ResponseEntity<Movie> getMovie(@RequestParam String tableName, @RequestParam String filmId, @RequestParam String title) {
        Movie movie = movieServiceImpl.getMovieById(tableName, filmId, title);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
}
