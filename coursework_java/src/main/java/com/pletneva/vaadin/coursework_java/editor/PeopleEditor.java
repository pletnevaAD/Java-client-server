package com.pletneva.vaadin.coursework_java.editor;

import com.pletneva.vaadin.coursework_java.entity.Group;
import com.pletneva.vaadin.coursework_java.entity.People;

import com.pletneva.vaadin.coursework_java.service.GroupService;
import com.pletneva.vaadin.coursework_java.service.PeopleService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;


@SpringComponent
@UIScope
public class PeopleEditor extends VerticalLayout implements KeyNotifier {

    private PeopleService peopleService;
    private GroupService groupService;

    private People people;

    private TextField firstName = new TextField("", "People name");
    private TextField lastName = new TextField("", "People lname");
    private TextField patherName = new TextField("", "People pname");
    private ComboBox<Group> group_id = new ComboBox<>("Group");
    private Dialog dialog = new Dialog();
    private ComboBox<People> people_id = new ComboBox<>("People id");
    private ComboBox<String> type = new ComboBox<>("People type");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");
    private Button dialogDeleteButton = new Button("Delete");
    private HorizontalLayout buttons = new HorizontalLayout(cancel, delete);

    private Binder<People> binder = new Binder<>(People.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public PeopleEditor(PeopleService peopleService, GroupService groupService) {
        this.peopleService = peopleService;
        this.groupService = groupService;
        group_id.setItems(groupService.listGroups());
        group_id.setItemLabelGenerator(Group::getName);

        type.setItems(List.of("S", "P"));

        add(firstName, lastName,buttons, patherName, group_id, type, save);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        dialog.add(new Label("Существует более 1 человека с такими именем и фамилией"));


        addKeyPressListener(Key.ENTER, e -> save());
        save.addClickListener(e -> save());
        delete.addClickListener(e -> {
           People[] peopleList = peopleService.findPeopleByFirstNameAndLastName(firstName.getValue(),
                    lastName.getValue());
            if (peopleList.length == 1) {
                delete();
            } else {
                createDialogLayout(peopleList);
                dialog.open();
                dialogDeleteButton.addClickListener(d -> deleteInDialog());
            }
        });
        cancel.addClickListener(e -> setVisible(false));
        setVisible(false);
    }

    private void createDialogLayout(People[] peopleList) {
        VerticalLayout verticalLayout = new VerticalLayout();
        people_id.setItems(peopleList);
        people_id.setItemLabelGenerator(people1 -> String.valueOf(people1.getId()));
        verticalLayout.add(people_id, dialogDeleteButton);
        dialog.add(verticalLayout);
    }

    private void save() {
        Integer id;
        if (group_id.getValue() == null) {
            id = null;
        } else {
            id = group_id.getValue().getId();
        }
        peopleService.addPeople(people, id);
        changeHandler.onChange();
    }

    private void deleteInDialog() {
        peopleService.deleteById(people_id.getValue().getId());
        changeHandler.onChange();
    }

    private void delete() {
        peopleService.deletePeople(firstName.getValue(), lastName.getValue());
        changeHandler.onChange();
    }

    public void editPeople(People people) {
        if (people == null) {
            setVisible(false);
            return;
        }

        if (people.getId() != null) {
            this.people = peopleService.findById(people.getId());
        } else {
            this.people = people;
        }

        binder.setBean(this.people);

        setVisible(true);
    }
}