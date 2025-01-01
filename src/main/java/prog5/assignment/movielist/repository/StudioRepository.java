package prog5.assignment.movielist.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import prog5.assignment.movielist.entity.Studio;

import java.util.List;

@Repository
public interface StudioRepository extends CrudRepository<Studio, Long> {

    Studio findByName(String name);

    List<Studio> findAll();

    @Query("SELECT * FROM studios WHERE name LIKE :name")
    List<Studio> findByNameContaining(String name);

}

