package prog5.assignment.movielist.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("actors")
public class Actor {

    @Id
    private Long id;
    private String name;
    private String gender;
    private String nationality;

    public Actor() {
    }

    public Actor(Long id, String name, String gender, String nationality) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.nationality = nationality;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

}

