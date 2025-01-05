package prog5.assignment.movielist.service.ui.edit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import prog5.assignment.movielist.bean.StudioDTO;
import prog5.assignment.movielist.service.StudioService;

@Route("edit-studio/:id")
@RolesAllowed("ADMIN")

public class EditStudioView extends VerticalLayout implements BeforeEnterObserver {

    private final StudioService studioService;
    private Long studioId;

    public EditStudioView(StudioService studioService) {
        this.studioService = studioService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String idString = event.getRouteParameters().get("id").orElse(null);
        if (idString != null) {
            this.studioId = Long.parseLong(idString);
            initializeView();
        } else {
            Notification.show("Studio ID is missing in the URL");
        }
    }

    private void initializeView() {
        StudioDTO studio = studioService.getStudioById(studioId);

        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Studio Name");
        nameField.setValue(studio.getName());

        TextField locationField = new TextField("Location");
        locationField.setValue(studio.getLocation());

        Button saveButton = new Button("Save", e -> {
            if (nameField.isEmpty() || locationField.isEmpty()) {
                Notification.show("Please fill in all fields!");
                return;
            }

            studio.setName(nameField.getValue());
            studio.setLocation(locationField.getValue());

            studioService.updateStudio(studioId, studio);

            Notification.show("Studio updated successfully!");
            getUI().ifPresent(ui -> ui.navigate("studios"));
        });

        formLayout.add(nameField, locationField, saveButton);

        add(formLayout);
    }
}
