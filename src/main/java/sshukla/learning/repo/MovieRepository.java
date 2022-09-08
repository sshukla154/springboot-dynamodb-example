package sshukla.learning.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sshukla.learning.entity.Movie;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */
@Repository
public interface MovieRepository  extends CrudRepository<Movie,String> {

}
