package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Authentication;
import java.util.List;

public interface AuthenticationRepository extends CrudRepository<Authentication> {
    List<Authentication> findAllByUserId(Long id);
}
