package prog5.assignment.movielist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import prog5.assignment.movielist.entity.Studio;

@Repository
public interface StudioRepository extends CrudRepository<Studio, Long> {

    Studio findByName(String name);
}

