package com.pletneva.vaadin.coursework_java.editor;

import com.pletneva.vaadin.coursework_java.entity.Group;
import com.pletneva.vaadin.coursework_java.service.GroupService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class GroupEditor extends VerticalLayout implements KeyNotifier {

    private GroupService groupService;

    private Group group;

    private IntegerField id = new IntegerField("", "Group id");
    private TextField name = new TextField("", "Group name");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");
    private HorizontalLayout buttons = new HorizontalLayout(save, cancel);

    private Binder<Group> binder = new Binder<>(Group.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public GroupEditor(GroupService groupService) {
        this.groupService = groupService;

        add(name, buttons, id, delete);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> setVisible(false));
        setVisible(false);
    }

    private void save() {
        groupService.addGroup(group);
        changeHandler.onChange();
    }

    private void delete() {
        groupService.deleteGroup(group);
        changeHandler.onChange();
    }

    public void editGroup(Group group) {
        if (group == null) {
            setVisible(false);
            return;
        }

        if (group.getId() != null) {
            this.group = groupService.findById(group.getId());
        } else {
            this.group = group;
        }

        binder.setBean(this.group);

        setVisible(true);
    }
}