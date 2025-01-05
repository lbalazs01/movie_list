package prog5.assignment.movielist.service.ui.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import prog5.assignment.movielist.bean.ActorDTO;
import prog5.assignment.movielist.service.ActorService;
import prog5.assignment.movielist.service.SecurityService;
import prog5.assignment.movielist.service.ui.MainLayout;

import java.util.List;

@Route(value = "actors", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class ActorListView extends VerticalLayout {

    private final ActorService actorService;
    private final SecurityService securityService;
    private final Grid<ActorDTO> actorGrid;

    public ActorListView(ActorService actorService, SecurityService securityService) {
        this.actorService = actorService;
        this.securityService = securityService;

        setSizeFull();

        actorGrid = new Grid<>(ActorDTO.class, false);
        actorGrid.addColumn(ActorDTO::getId).setHeader("ID").setSortable(true);
        actorGrid.addColumn(ActorDTO::getName).setHeader("Name").setSortable(true);
        actorGrid.addColumn(ActorDTO::getGender).setHeader("Gender").setSortable(true);
        actorGrid.addColumn(ActorDTO::getNationality).setHeader("Nationality").setSortable(true);

        // conditionally add the "Actions" column based on the user's role
        if (securityService.isAdmin()) {
            actorGrid.addComponentColumn(actor -> createActionButtons(actor)).setHeader("Actions");
        }

        // add "Add Actor" button for all users
        Button addActorButton = new Button("Add Actor", e -> {
            getUI().ifPresent(ui -> ui.navigate("add-actor"));
        });

        add(addActorButton, actorGrid);
        loadActorList();
    }

    private HorizontalLayout createActionButtons(ActorDTO actor) {
        Button editButton = new Button("Edit", event ->
                getUI().ifPresent(ui -> ui.navigate("edit-actor/" + actor.getId())));
        Button deleteButton = new Button("Delete", event -> {
            actorService.deleteActor(actor.getId());
            refreshActorList();
        });

        return new HorizontalLayout(editButton, deleteButton);
    }

    private void loadActorList() {
        List<ActorDTO> actors = actorService.getAllActors();
        actorGrid.setItems(actors);
    }

    private void refreshActorList() {
        loadActorList();
    }
}
