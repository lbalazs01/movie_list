package prog5.assignment.movielist.bean;

import java.time.LocalDate;

public class MovieDTO {

    private Long id;
    private String title;
    private String genre;
    private LocalDate releaseDate;
    private Long leadActorId;
    private Long studioId;

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
