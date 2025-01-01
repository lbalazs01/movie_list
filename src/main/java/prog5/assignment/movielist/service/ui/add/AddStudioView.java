package prog5.assignment.movielist.service.ui.add;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import prog5.assignment.movielist.bean.StudioDTO;
import prog5.assignment.movielist.service.StudioService;

@Route("add-studio")
public class AddStudioView extends VerticalLayout {

    private final StudioService studioService;

    public AddStudioView(StudioService studioService) {
        this.studioService = studioService;

        setSizeFull();

        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Studio Name");
        TextField locationField = new TextField("Location");

        Button saveButton = new Button("Save", e -> {
            StudioDTO studioDTO = new StudioDTO();
            studioDTO.setName(nameField.getValue());
            studioDTO.setLocation(locationField.getValue());

            studioService.saveStudio(studioDTO);
            Notification.show("Studio saved!");

            getUI().ifPresent(ui -> ui.navigate("studios"));
        });

        formLayout.add(nameField, locationField, saveButton);

        add(formLayout);
    }
}
