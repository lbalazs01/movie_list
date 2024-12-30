package prog5.assignment.movielist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import prog5.assignment.movielist.entity.Actor;

@Repository
public interface ActorRepository extends CrudRepository<Actor, Long> {

    Actor findByName(String name);
}
