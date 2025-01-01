package prog5.assignment.movielist.service.ui.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import prog5.assignment.movielist.bean.StudioDTO;
import prog5.assignment.movielist.service.StudioService;
import prog5.assignment.movielist.service.ui.MainLayout;

import java.util.List;

@Route(value = "studios", layout = MainLayout.class)
public class StudioListView extends VerticalLayout {

    private final StudioService studioService;
    private final Grid<StudioDTO> studioGrid;

    public StudioListView(StudioService studioService) {
        this.studioService = studioService;

        setSizeFull();

        // Initialize the grid with StudioDTO class
        studioGrid = new Grid<>(StudioDTO.class, false);
        studioGrid.addColumn(StudioDTO::getId).setHeader("ID").setSortable(true);
        studioGrid.addColumn(StudioDTO::getName).setHeader("Name").setSortable(true);
        studioGrid.addColumn(StudioDTO::getLocation).setHeader("Location").setSortable(true);

        // Add action buttons for all users
        studioGrid.addComponentColumn(studio -> {
            Button editButton = new Button("Edit", event -> {
                getUI().ifPresent(ui -> ui.navigate("edit-studio/" + studio.getId()));
            });
            Button deleteButton = new Button("Delete", event -> {
                studioService.deleteStudio(studio.getId());
                refreshStudioList();
            });

            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Actions");

        // Button to navigate to add studio page
        Button addStudioButton = new Button("Add Studio", e -> {
            getUI().ifPresent(ui -> ui.navigate("add-studio"));
        });

        add(addStudioButton, studioGrid);
        loadStudioList();
    }

    private void loadStudioList() {
        List<StudioDTO> studios = studioService.getAllStudios();
        studioGrid.setItems(studios);
    }

    private void refreshStudioList() {
        loadStudioList();
    }
}
