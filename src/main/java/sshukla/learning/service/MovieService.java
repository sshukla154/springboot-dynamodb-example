package sshukla.learning.service;

import sshukla.learning.entity.Movie;

/**
 * @author 'Seemant Shukla' on '09/09/2022'
 */
public interface MovieService {
    String createTable(String tableName, String partitionKey, String sortKey);

    void deleteTable(String tableName);

    Movie createMovie(String tableName, Movie movie);

}
