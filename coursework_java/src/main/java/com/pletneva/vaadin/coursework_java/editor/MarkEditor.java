package com.pletneva.vaadin.coursework_java.editor;

import com.pletneva.vaadin.coursework_java.entity.Mark;
import com.pletneva.vaadin.coursework_java.entity.People;
import com.pletneva.vaadin.coursework_java.entity.Subject;
import com.pletneva.vaadin.coursework_java.service.MarkService;
import com.pletneva.vaadin.coursework_java.service.PeopleService;
import com.pletneva.vaadin.coursework_java.service.SubjectService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;


@SpringComponent
@UIScope
public class MarkEditor extends VerticalLayout implements KeyNotifier {

    private MarkService markService;
    private SubjectService subjectService;
    private PeopleService peopleService;

    private Mark mark;

    private BigDecimalField value = new BigDecimalField("", "Mark value");
    private ComboBox<People> student_id = new ComboBox<>("Student");
    private ComboBox<People> teacher_id = new ComboBox<>("Teacher");
    private ComboBox<Subject> subject_id = new ComboBox<>("Subject");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");
    private HorizontalLayout buttons = new HorizontalLayout(cancel, delete);

    private Binder<Mark> binder = new Binder<>(Mark.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public MarkEditor(MarkService markService, SubjectService subjectService,
                      PeopleService peopleService) {
        this.markService = markService;
        this.subjectService = subjectService;
        this.peopleService = peopleService;
        subject_id.setItems(this.subjectService.listSubject());
        subject_id.setItemLabelGenerator(subject -> String.valueOf(subject.getId()));

        student_id.setItems(this.peopleService.listStudent());
        student_id.setItemLabelGenerator(people -> String.valueOf(people.getId()));

        teacher_id.setItems(this.peopleService.listTeacher());
        teacher_id.setItemLabelGenerator(people -> String.valueOf(people.getId()));

        add(student_id, teacher_id, subject_id, buttons, value, save);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());
        save.addClickListener(e -> save());
        delete.addClickListener(e -> {
            delete();
        });
        cancel.addClickListener(e -> setVisible(false));
        setVisible(false);
    }

    private void save() {
        markService.addMark(value.getValue(), teacher_id.getValue().getId(), subject_id.getValue().getId(),
                student_id.getValue().getId());
        changeHandler.onChange();
    }

    private void delete() {
        markService.deleteMark(teacher_id.getValue().getId(), subject_id.getValue().getId(), student_id.getValue().getId());
        changeHandler.onChange();
    }

    public void editMark(Mark mark) {
        if (mark == null) {
            setVisible(false);
            return;
        }

        if (mark.getId() != null) {
            this.mark = markService.findById(mark.getId());
        } else {
            this.mark = mark;
        }

        binder.setBean(this.mark);

        setVisible(true);
    }
}