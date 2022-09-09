package sshukla.learning.service;

/**
 * @author 'Seemant Shukla' on '09/09/2022'
 */
public interface MovieService {
    String createMovieTable(String tableName, String partitionKey, String sortKey);

}
