package prog5.assignment.movielist.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import prog5.assignment.movielist.entity.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query("SELECT * FROM movies WHERE title LIKE :title")
    List<Movie> searchByTitle(String title);

    @Query("SELECT * FROM movies WHERE lead_actor_id = :actorId")
    List<Movie> findByLeadActorId(Long actorId);

    @Query("SELECT * FROM movies WHERE studio_id = :studioId")
    List<Movie> findByStudioId(Long studioId);
}

