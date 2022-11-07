package com.pletneva.vaadin.coursework_java.view;

import com.pletneva.vaadin.coursework_java.editor.GroupEditor;
import com.pletneva.vaadin.coursework_java.entity.Group;
import com.pletneva.vaadin.coursework_java.service.GroupService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Route("/client/group")
public class GroupView extends VerticalLayout {
    private final GroupService groupService;
    private final GroupEditor groupEditor;

    private Grid<Group> groupGrid = new Grid<>(Group.class);
    private final IntegerField filter = new IntegerField();
    private final Button addNewButton = new Button("Edit groups", VaadinIcon.PLUS.create());
    private Button mainMenu = new Button(new RouterLink("Main menu ", LoginView.class));
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton, mainMenu);

    @Autowired
    public GroupView(GroupService groupService, GroupEditor groupEditor) {
        this.groupService = groupService;
        this.groupEditor = groupEditor;

        filter.setPlaceholder("Enter group id");
        filter.setWidth("150px");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> fillList(field.getValue()));

        add(toolbar, groupGrid, groupEditor);
        groupGrid.setHeight("400px");
        groupGrid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);

        groupGrid
                .asSingleSelect()
                .addValueChangeListener(e -> {

                    this.groupEditor.editGroup(e.getValue());

                });

        addNewButton.addClickListener(e -> {

            this.groupEditor.editGroup(new Group());

        });

        groupEditor.setChangeHandler(() -> {
            this.groupEditor.setVisible(false);
            fillList(filter.getValue());
        });

        fillList(null);
    }

    private void fillList(Integer id) {
        if (id == null) {
            groupGrid.setItems(this.groupService.listGroups());
        } else {
            groupGrid.setItems(this.groupService.findById(id));
        }
    }
}