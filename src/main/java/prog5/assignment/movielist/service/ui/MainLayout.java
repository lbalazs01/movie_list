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
import jakarta.servlet.http.HttpServletRequest;
import prog5.assignment.movielist.service.MovieService;
import prog5.assignment.movielist.bean.MovieDTO;
import com.vaadin.flow.component.applayout.AppLayout;
import org.springframework.security.core.context.SecurityContextHolder;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.Router;

@Route("")
public class MainLayout extends AppLayout {

    private MovieService movieService;
    private Grid<MovieDTO> movieGrid; // The grid to display search results

    public MainLayout(MovieService movieService) {
        this.movieService = movieService;

        // Title for the application
        H1 title = new H1("Movie List App");

        // Horizontal menu layout with links to other views
        HorizontalLayout menu = new HorizontalLayout();
        Anchor movieLink = new Anchor("/movies", "Movies");
        Anchor actorLink = new Anchor("/actors", "Actors");
        Anchor studioLink = new Anchor("/studios", "Studios");

        menu.add(movieLink, actorLink, studioLink);

        // Layout for header (title and menu)
        VerticalLayout header = new VerticalLayout(title, menu);
        header.setPadding(false);
        header.setSpacing(false);

        // Add header to the navbar
        addToNavbar(header);

        // Main body of the layout where the search bar will go
        VerticalLayout body = new VerticalLayout();

        // Add search field only if it's the root view
        if (isRootView()) {
            addSearchField(body);
        }

        // Initialize the grid
        movieGrid = new Grid<>();
        movieGrid.setWidth("100%");
        movieGrid.addColumn(MovieDTO::getTitle).setHeader("Title");
        movieGrid.addColumn(MovieDTO::getLeadActorId).setHeader("Lead Actor");
        movieGrid.addColumn(MovieDTO::getStudioId).setHeader("Studio");
        movieGrid.addColumn(MovieDTO::getReleaseDate).setHeader("Release Date");

        // Add the grid to the body
        body.add(movieGrid);

        // Add the body to the layout
        setContent(body);  // Use setContent() for AppLayout instead of add()

        // Add the logout button for authenticated users
        if (isAuthenticated()) {
            Button logoutButton = new Button("Logout", event -> {
                // Clear the authentication context and log the user out
                SecurityContextHolder.clearContext();
                UI.getCurrent().navigate("login"); // Redirect to the login page after logout
            });

            // Add the logout button to the layout
            addToNavbar(new HorizontalLayout(logoutButton));
        }
    }

    // Method to check if the current route is the root view ("/")
    private boolean isRootView() {
        // Get the current location from the UI internals
        String currentPath = UI.getCurrent().getInternals().getActiveViewLocation().getPath();
        System.out.println("Current path: " + currentPath);  // Debugging line
        return currentPath.equals("/") || currentPath.equals("");  // Check if it's the root route
    }

    // Method to check if the user is authenticated
    private boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    // Method to add the search field
    private void addSearchField(VerticalLayout body) {
        // Create the search TextField
        TextField searchField = new TextField();
        searchField.setLabel("Search");
        searchField.setPlaceholder("Search by movie, actor, or studio");
        searchField.setMaxLength(100); // Optional: set a max length for the search input

        // Search button to trigger action
        Button searchButton = new Button("Search", event -> {
            String searchQuery = searchField.getValue();
            // Call the search method with the entered value
            performSearch(searchQuery);
        });

        // Layout to hold the search field and button
        HorizontalLayout searchLayout = new HorizontalLayout(searchField, searchButton);
        searchLayout.setAlignItems(FlexComponent.Alignment.CENTER);  // Center the components in the layout

        body.add(searchLayout);  // Add the search layout to the body
    }

    // Method to perform the search and update the grid with results
    private void performSearch(String searchQuery) {
        // Call the MovieService to perform the search
        var searchResults = movieService.searchMovies(searchQuery);  // Call the searchMovies method from the service

        // Update the grid with the search results
        movieGrid.setItems(searchResults);
    }
}
