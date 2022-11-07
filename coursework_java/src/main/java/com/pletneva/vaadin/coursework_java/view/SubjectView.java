package com.pletneva.vaadin.coursework_java.view;

import com.pletneva.vaadin.coursework_java.editor.SubjectEditor;
import com.pletneva.vaadin.coursework_java.entity.Subject;
import com.pletneva.vaadin.coursework_java.service.SubjectService;
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

@Route("/client/subject")
public class SubjectView extends VerticalLayout {
    private final SubjectService subjectService;
    private final SubjectEditor subjectEditor;

    private Grid<Subject> subjectGrid = new Grid<>(Subject.class);
    private final IntegerField filter = new IntegerField();
    private final Button addNewButton = new Button("Edit subjects", VaadinIcon.PLUS.create());
    Button but = new Button(new RouterLink("Main menu ", LoginView.class));
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton, but);

    @Autowired
    public SubjectView(SubjectService subjectService, SubjectEditor subjectEditor) {
        this.subjectService = subjectService;
        this.subjectEditor = subjectEditor;

        filter.setPlaceholder("Enter subject id");
        filter.setWidth("150px");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> fillList(field.getValue()));

        add(toolbar, subjectGrid, subjectEditor);
        subjectGrid.setHeight("300px");
        subjectGrid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);

        subjectGrid
                .asSingleSelect()
                .addValueChangeListener(e -> {

                    this.subjectEditor.editSubject(e.getValue());

                });

        addNewButton.addClickListener(e -> {

            this.subjectEditor.editSubject(new Subject());

        });

        subjectEditor.setChangeHandler(() -> {
            this.subjectEditor.setVisible(false);
            fillList(filter.getValue());
        });

        fillList(null);
    }

    private void fillList(Integer id) {
        if (id == null) {
            subjectGrid.setItems(this.subjectService.listSubject());
        } else {
            subjectGrid.setItems(this.subjectService.findById(id));
        }
    }
}