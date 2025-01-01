package prog5.assignment.movielist.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import prog5.assignment.movielist.entity.Actor;
import prog5.assignment.movielist.entity.Studio;

import java.util.List;

@Repository
public interface ActorRepository extends CrudRepository<Actor, Long> {

    Actor findByName(String name);

    List<Actor> findAll();

    @Query("SELECT * FROM actors WHERE name LIKE :name")
    List<Actor> findByNameContaining(String name);
}
