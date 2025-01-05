package prog5.assignment.movielist.service.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.HttpServletRequest;
import prog5.assignment.movielist.service.MovieService;
import prog5.assignment.movielist.bean.MovieDTO;
import com.vaadin.flow.component.applayout.AppLayout;
import org.springframework.security.core.context.SecurityContextHolder;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.Router;

@Route("")
@AnonymousAllowed // allow access to all users
public class MainLayout extends AppLayout {

    private MovieService movieService;
    private Grid<MovieDTO> movieGrid;

    public MainLayout(MovieService movieService) {
        this.movieService = movieService;

        H1 title = new H1("Movie List App");

        HorizontalLayout menu = new HorizontalLayout();
        Anchor movieLink = new Anchor("/movies", "Movies");
        Anchor actorLink = new Anchor("/actors", "Actors");
        Anchor studioLink = new Anchor("/studios", "Studios");

        menu.add(movieLink, actorLink, studioLink);

        VerticalLayout header = new VerticalLayout(title, menu);
        header.setPadding(false);
        header.setSpacing(false);

        addToNavbar(header);

        VerticalLayout body = new VerticalLayout();

        if (isRootView()) {
            addSearchField(body);
        }

        movieGrid = new Grid<>();
        movieGrid.setWidth("100%");
        movieGrid.addColumn(MovieDTO::getTitle).setHeader("Title");
        movieGrid.addColumn(MovieDTO::getLeadActorId).setHeader("Lead Actor");
        movieGrid.addColumn(MovieDTO::getStudioId).setHeader("Studio");
        movieGrid.addColumn(MovieDTO::getReleaseDate).setHeader("Release Date");

        body.add(movieGrid);

        setContent(body);

        if (isAuthenticated()) {
            Button logoutButton = new Button("Logout", event -> {
                SecurityContextHolder.clearContext();
                UI.getCurrent().navigate("login");
            });

            addToNavbar(new HorizontalLayout(logoutButton));
        }
    }

    private boolean isRootView() {
        String currentPath = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        return currentPath.equals("/") || currentPath.equals("");
    }

    private boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    private void addSearchField(VerticalLayout body) {
        // adding search field where user can search for actor, movie or studio name and list it out
        TextField searchField = new TextField();
        searchField.setLabel("Search");
        searchField.setPlaceholder("Search by movie, actor, or studio");
        searchField.setMaxLength(100);

        Button searchButton = new Button("Search", event -> {
            String searchQuery = searchField.getValue();
            performSearch(searchQuery);
        });

        HorizontalLayout searchLayout = new HorizontalLayout(searchField, searchButton);
        searchLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        body.add(searchLayout);
    }

    private void performSearch(String searchQuery) {
        var searchResults = movieService.searchMovies(searchQuery);

        movieGrid.setItems(searchResults);
    }
}
