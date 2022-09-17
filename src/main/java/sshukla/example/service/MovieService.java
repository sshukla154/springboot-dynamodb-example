package sshukla.example.service;

import sshukla.example.entity.Movie;

import java.util.List;

/**
 * @author 'Seemant Shukla' on '09/09/2022'
 */
public interface MovieService {
    String createTable(String tableName, String partitionKey, String sortKey, String globalSecondaryIndex, String localSecondaryIndex);

    void deleteTable(String tableName);

    void createMovie(String tableName, Movie movie);

    void updateMovie(String tableName, Movie movie);

    List<Movie> getAllMovies(String tableName);

    Movie getMovieById(String tableName, String filmId, String title);

}
