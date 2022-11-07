package com.pletneva.vaadin.coursework_java.editor;

import com.pletneva.vaadin.coursework_java.entity.Subject;
import com.pletneva.vaadin.coursework_java.service.SubjectService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
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
public class SubjectEditor extends VerticalLayout implements KeyNotifier {

    private SubjectService subjectService;

    private Subject subject;

    private IntegerField id = new IntegerField("", "Subject id");
    private TextField name = new TextField("", "Subject name");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");
    private HorizontalLayout buttons = new HorizontalLayout(save, cancel);

    private Binder<Subject> binder = new Binder<>(Subject.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public SubjectEditor(SubjectService subjectService) {
        this.subjectService = subjectService;

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
        subjectService.addSubject(subject);
        changeHandler.onChange();
    }

    private void delete() {

        subjectService.deleteSubject(subject);
        //subjectService.
        changeHandler.onChange();
    }

    public void editSubject(Subject subject) {
        if (subject == null) {
            setVisible(false);
            return;
        }

        if (subject.getId() != null) {
            this.subject = subjectService.findById(subject.getId());
        } else {
            this.subject = subject;
        }

        binder.setBean(this.subject);

        setVisible(true);
    }
}