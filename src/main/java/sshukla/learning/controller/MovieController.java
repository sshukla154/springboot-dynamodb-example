package sshukla.learning.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */

@RestController("/v1/movie")
public class MovieController {

    @GetMapping("/all/")
    public ResponseEntity<Map<String, Object>> getAllMovies() {
        return (ResponseEntity) new ResponseEntity<Object>(HttpStatus.CREATED);
    }
}
