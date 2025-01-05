package prog5.assignment.movielist.service.ui.edit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import prog5.assignment.movielist.bean.ActorDTO;
import prog5.assignment.movielist.service.ActorService;

@Route("edit-actor/:id")
@RolesAllowed("ADMIN")

public class EditActorView extends VerticalLayout implements BeforeEnterObserver {

    private final ActorService actorService;
    private Long actorId;

    public EditActorView(ActorService actorService) {
        this.actorService = actorService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String idString = event.getRouteParameters().get("id").orElse(null);
        if (idString != null) {
            this.actorId = Long.parseLong(idString);
            initializeView();
        } else {
            Notification.show("Actor ID is missing in the URL");
        }
    }

    private void initializeView() {
        ActorDTO actor = actorService.getActorById(actorId);

        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Actor Name");
        nameField.setValue(actor.getName());

        ComboBox<String> genderField = new ComboBox<>("Gender");
        genderField.setItems("Male", "Female", "Other");
        genderField.setValue(actor.getGender());

        TextField nationalityField = new TextField("Nationality");
        nationalityField.setValue(actor.getNationality());

        Button saveButton = new Button("Save", e -> {
            if (nameField.isEmpty() || genderField.isEmpty() || nationalityField.isEmpty()) {
                Notification.show("Please fill in all fields!");
                return;
            }

            actor.setName(nameField.getValue());
            actor.setGender(genderField.getValue());
            actor.setNationality(nationalityField.getValue());

            actorService.updateActor(actorId, actor);

            Notification.show("Actor updated successfully!");
            getUI().ifPresent(ui -> ui.navigate("actors"));
        });

        formLayout.add(nameField, genderField, nationalityField, saveButton);

        add(formLayout);
    }
}
