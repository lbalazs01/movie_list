package prog5.assignment.movielist.service.ui.edit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import prog5.assignment.movielist.bean.ActorDTO;
import prog5.assignment.movielist.bean.MovieDTO;
import prog5.assignment.movielist.bean.StudioDTO;
import prog5.assignment.movielist.service.ActorService;
import prog5.assignment.movielist.service.StudioService;
import prog5.assignment.movielist.service.MovieService;

@Route("edit-movie/:id")
public class EditMovieView extends VerticalLayout implements BeforeEnterObserver {

    private final ActorService actorService;
    private final StudioService studioService;
    private final MovieService movieService;

    private Long movieId;

    public EditMovieView(ActorService actorService, StudioService studioService, MovieService movieService) {
        this.actorService = actorService;
        this.studioService = studioService;
        this.movieService = movieService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String idString = event.getRouteParameters().get("id").orElse(null);
        if (idString != null) {
            this.movieId = Long.parseLong(idString);
            initializeView();
        } else {
            Notification.show("Movie ID is missing in the URL");
        }
    }

    private void initializeView() {
        MovieDTO movie = movieService.getMovieById(movieId);

        FormLayout formLayout = new FormLayout();

        TextField titleField = new TextField("Title");
        titleField.setValue(movie.getTitle());

        TextField genreField = new TextField("Genre");
        genreField.setValue(movie.getGenre());

        DatePicker releaseDateField = new DatePicker("Release Date");
        releaseDateField.setValue(movie.getReleaseDate());

        ComboBox<ActorDTO> actorComboBox = new ComboBox<>("Select Lead Actor");
        actorComboBox.setItemLabelGenerator(ActorDTO::getName);
        actorComboBox.setItems(actorService.getAllActors());
        actorComboBox.setValue(actorService.getActorById(movie.getLeadActorId()));

        ComboBox<StudioDTO> studioComboBox = new ComboBox<>("Select Studio");
        studioComboBox.setItemLabelGenerator(StudioDTO::getName);
        studioComboBox.setItems(studioService.getAllStudios());
        studioComboBox.setValue(studioService.getStudioById(movie.getStudioId()));

        Button saveButton = new Button("Save", e -> {
            if (actorComboBox.isEmpty() || studioComboBox.isEmpty() || releaseDateField.isEmpty()) {
                Notification.show("Please fill in all fields!");
                return;
            }

            movie.setTitle(titleField.getValue());
            movie.setGenre(genreField.getValue());
            movie.setReleaseDate(releaseDateField.getValue());
            movie.setLeadActorId(actorComboBox.getValue().getId());
            movie.setStudioId(studioComboBox.getValue().getId());

            movieService.updateMovie(movieId, movie);

            Notification.show("Movie updated successfully!");
            getUI().ifPresent(ui -> ui.navigate("movies"));
        });

        formLayout.add(titleField, genreField, releaseDateField, actorComboBox, studioComboBox, saveButton);

        add(formLayout);
    }
}
