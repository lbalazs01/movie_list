package prog5.assignment.movielist.service.ui.add;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import prog5.assignment.movielist.service.ActorService;
import prog5.assignment.movielist.service.StudioService;
import prog5.assignment.movielist.service.MovieService;
import prog5.assignment.movielist.bean.ActorDTO;
import prog5.assignment.movielist.bean.StudioDTO;
import prog5.assignment.movielist.bean.MovieDTO;

import java.time.LocalDate;
import java.util.List;

@Route("add-movie")
public class AddMovieView extends VerticalLayout {

    private final ActorService actorService;
    private final StudioService studioService;
    private final MovieService movieService;

    public AddMovieView(ActorService actorService, StudioService studioService, MovieService movieService) {
        this.actorService = actorService;
        this.studioService = studioService;
        this.movieService = movieService;

        setSizeFull();

        FormLayout formLayout = new FormLayout();

        ComboBox<ActorDTO> actorComboBox = new ComboBox<>("Select Lead Actor");
        ComboBox<StudioDTO> studioComboBox = new ComboBox<>("Select Studio");

        List<ActorDTO> actors = actorService.getAllActors();
        List<StudioDTO> studios = studioService.getAllStudios();

        actorComboBox.setItemLabelGenerator(ActorDTO::getName);
        actorComboBox.setItems(actors);

        studioComboBox.setItemLabelGenerator(StudioDTO::getName);
        studioComboBox.setItems(studios);

        TextField titleField = new TextField("Title");
        TextField genreField = new TextField("Genre");

        DatePicker releaseDateField = new DatePicker("Release Date");

        Button saveButton = new Button("Save", e -> {
            if (actorComboBox.isEmpty() || studioComboBox.isEmpty() || releaseDateField.isEmpty()) {
                Notification.show("Please select both lead actor, studio, and release date!");
                return;
            }

            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setTitle(titleField.getValue());
            movieDTO.setGenre(genreField.getValue());
            movieDTO.setReleaseDate(releaseDateField.getValue());
            movieDTO.setLeadActorId(actorComboBox.getValue().getId());
            movieDTO.setStudioId(studioComboBox.getValue().getId());

            movieService.saveMovie(movieDTO);
            Notification.show("Movie saved!");
            getUI().ifPresent(ui -> ui.navigate("movies"));
        });

        formLayout.add(titleField, genreField, releaseDateField, actorComboBox, studioComboBox, saveButton);

        add(formLayout);
    }
}
