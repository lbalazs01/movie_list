package prog5.assignment.movielist.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("movies")
public class Movie {

    @Id
    private Long id;
    private String title;
    private String genre;
    private LocalDate releaseDate;
    private Long leadActorId;
    private Long studioId;


    public Movie() {
    }

    public Movie(Long id, String title, String genre, LocalDate releaseDate, Long leadActorId, Long studioId) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.leadActorId = leadActorId;
        this.studioId = studioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getLeadActorId() {
        return leadActorId;
    }

    public void setLeadActorId(Long leadActorId) {
        this.leadActorId = leadActorId;
    }

    public Long getStudioId() {
        return studioId;
    }

    public void setStudioId(Long studioId) {
        this.studioId = studioId;
    }

}
