package com.pletneva.vaadin.coursework_java.view;

import com.pletneva.vaadin.coursework_java.editor.PeopleEditor;
import com.pletneva.vaadin.coursework_java.entity.People;
import com.pletneva.vaadin.coursework_java.service.PeopleService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Route("/client/people")
public class PeopleView extends VerticalLayout {
    private final PeopleService peopleService;
    private final PeopleEditor peopleEditor;

    private Grid<People> peopleGrid = new Grid<>(People.class);
    private final IntegerField filter = new IntegerField();
    private final Button addNewButton = new Button("Edit peoples", VaadinIcon.PLUS.create());
    Button but = new Button(new RouterLink("Main menu ", LoginView.class));
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton, but);

    @Autowired
    public PeopleView(PeopleService peopleService, PeopleEditor peopleEditor) {
        this.peopleService = peopleService;
        this.peopleEditor = peopleEditor;

        filter.setPlaceholder("Enter people id");
        filter.setWidth("150px");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> fillList(field.getValue()));

        add(toolbar, peopleGrid, peopleEditor);
        peopleGrid.setHeight("300px");
        peopleGrid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);
        //peopleGrid.getColumnByKey("Group").setWidth("200px").setFlexGrow(0);

        peopleGrid
                .asSingleSelect()
                .addValueChangeListener(e -> {

                    this.peopleEditor.editPeople(e.getValue());

                });

        addNewButton.addClickListener(e -> {

            this.peopleEditor.editPeople(new People());

        });

        peopleEditor.setChangeHandler(() -> {
            this.peopleEditor.setVisible(false);
            fillList(filter.getValue());
        });

        fillList(null);
    }

    private void fillList(Integer id) {
        if (id == null) {
            peopleGrid.setItems(this.peopleService.listPeople());
        } else {
            peopleGrid.setItems(this.peopleService.findById(id));
        }
    }
}