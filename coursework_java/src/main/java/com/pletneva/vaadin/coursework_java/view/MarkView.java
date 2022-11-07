package com.pletneva.vaadin.coursework_java.view;

import com.pletneva.vaadin.coursework_java.editor.MarkEditor;
import com.pletneva.vaadin.coursework_java.entity.Mark;
import com.pletneva.vaadin.coursework_java.service.MarkService;
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

@Route("/client/mark")
public class MarkView extends VerticalLayout {
    private final MarkService markService;
    private final MarkEditor markEditor;

    private Grid<Mark> markGrid = new Grid<>(Mark.class);
    private final IntegerField filter = new IntegerField();
    private final Button addNewButton = new Button("Edit marks", VaadinIcon.PLUS.create());
    Button but = new Button(new RouterLink("Main menu ", LoginView.class));
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton, but);

    @Autowired
    public MarkView(MarkService markService, MarkEditor markEditor) {
        this.markService = markService;
        this.markEditor = markEditor;

        filter.setPlaceholder("Enter mark id");
        filter.setWidth("150px");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(field -> fillList(field.getValue()));

        add(toolbar, markGrid, markEditor);
        markGrid.setHeight("300px");
        markGrid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);
        //markGrid.getColumnByKey("Group").setWidth("200px").setFlexGrow(0);

        markGrid
                .asSingleSelect()
                .addValueChangeListener(e -> {

                    this.markEditor.editMark(e.getValue());

                });

        addNewButton.addClickListener(e -> {

            this.markEditor.editMark(new Mark());

        });

        markEditor.setChangeHandler(() -> {
            this.markEditor.setVisible(false);
            fillList(filter.getValue());
        });

        fillList(null);
    }

    private void fillList(Integer id) {
        if (id == null) {
            markGrid.setItems(this.markService.listMark());
        } else {
            markGrid.setItems(this.markService.findById(id));
        }
    }
}