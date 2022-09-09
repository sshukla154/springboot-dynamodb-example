package sshukla.learning.service;

import sshukla.learning.entity.Movie;

/**
 * @author 'Seemant Shukla' on '09/09/2022'
 */
public interface MovieService {
    Movie createMovieTable(String tableName, String partitionKey, String sortKey);

}
