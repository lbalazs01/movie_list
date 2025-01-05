package prog5.assignment.movielist.service.ui.add;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import prog5.assignment.movielist.bean.ActorDTO;
import prog5.assignment.movielist.service.ActorService;

@Route("add-actor")
@RolesAllowed({"USER", "ADMIN"}) // specifying with roles are allowed to access

public class AddActorView extends VerticalLayout {

    private final ActorService actorService;

    public AddActorView(ActorService actorService) {
        this.actorService = actorService;

        setSizeFull();

        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Actor Name");
        TextField genderField = new TextField("Gender");
        TextField nationalityField = new TextField("Nationality");

        Button saveButton = new Button("Save", e -> {
            ActorDTO actorDTO = new ActorDTO();
            actorDTO.setName(nameField.getValue());
            actorDTO.setGender(genderField.getValue());
            actorDTO.setNationality(nationalityField.getValue());

            actorService.saveActor(actorDTO);
            Notification.show("Actor saved!");
            getUI().ifPresent(ui -> ui.navigate("actors"));
        });

        formLayout.add(nameField, genderField, nationalityField, saveButton);

        add(formLayout);
    }
}
