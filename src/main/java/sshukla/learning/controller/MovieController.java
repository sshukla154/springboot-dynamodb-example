package sshukla.learning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sshukla.learning.service.impl.MovieServiceImpl;

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
    public ResponseEntity<String> createMovieTable(@RequestParam String tableName, @RequestParam String partitionKey, @RequestParam(required = false) String sortKey) {
        String createdTable = movieServiceImpl.createMovieTable(tableName, partitionKey, sortKey);
        return new ResponseEntity<>("Table " + createdTable + ", is created", HttpStatus.CREATED);
    }

    @GetMapping("/table/all/")
    public ResponseEntity getAllTables() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/table/{tablename}/")
    public ResponseEntity deleteTable() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
