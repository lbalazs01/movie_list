package prog5.assignment.movielist.service.ui.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import prog5.assignment.movielist.bean.MovieDTO;
import prog5.assignment.movielist.service.MovieService;
import prog5.assignment.movielist.service.SecurityService;
import prog5.assignment.movielist.service.ui.MainLayout;

import java.util.List;

@Route(value = "movies", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class MovieListView extends VerticalLayout {

    private final MovieService movieService;
    private final SecurityService securityService;
    private final Grid<MovieDTO> movieGrid;

    public MovieListView(MovieService movieService, SecurityService securityService) {
        this.movieService = movieService;
        this.securityService = securityService;

        setSizeFull();

        movieGrid = new Grid<>(MovieDTO.class, false);
        movieGrid.addColumn(MovieDTO::getId).setHeader("ID").setSortable(true);
        movieGrid.addColumn(MovieDTO::getTitle).setHeader("Title").setSortable(true);
        movieGrid.addColumn(MovieDTO::getGenre).setHeader("Genre").setSortable(true);
        movieGrid.addColumn(MovieDTO::getReleaseDate).setHeader("Release Date").setSortable(true);
        movieGrid.addColumn(MovieDTO::getLeadActorId).setHeader("Lead Actor Id").setSortable(true);
        movieGrid.addColumn(MovieDTO::getStudioId).setHeader("Studio Id").setSortable(true);

        // Conditionally add "Actions" column based on user role
        if (securityService.isAdmin()) {
            movieGrid.addComponentColumn(movie -> {
                Button editButton = new Button("Edit", event ->
                        getUI().ifPresent(ui -> ui.navigate("edit-movie/" + movie.getId())));
                Button deleteButton = new Button("Delete", event -> {
                    movieService.deleteMovie(movie.getId());
                    refreshMovieList();
                });

                return new HorizontalLayout(editButton, deleteButton);
            }).setHeader("Actions");
        }

        // add "Add Movie" button for all users
        Button addMovieButton = new Button("Add Movie", e -> {
            getUI().ifPresent(ui -> ui.navigate("add-movie"));
        });

        add(addMovieButton, movieGrid);
        loadMovieList();
    }

    private void loadMovieList() {
        List<MovieDTO> movies = movieService.getAllMovies();
        movieGrid.setItems(movies);
    }

    private void refreshMovieList() {
        loadMovieList();
    }
}
