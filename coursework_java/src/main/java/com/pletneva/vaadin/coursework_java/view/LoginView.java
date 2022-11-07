package com.pletneva.vaadin.coursework_java.view;

import com.pletneva.vaadin.coursework_java.entity.Group;
import com.pletneva.vaadin.coursework_java.entity.MyUser;
import com.pletneva.vaadin.coursework_java.service.SecurityService;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;
import org.springframework.web.reactive.function.client.WebClientException;

import java.nio.file.LinkOption;

@Route(value = "/client/login")
@PageTitle("Main menu")
public class LoginView extends VerticalLayout {

    SecurityService securityService;

    public LoginView(SecurityService securityService) {
        this.securityService = securityService;

        setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout menu = new HorizontalLayout();
        Label label = new Label("Tables");
        Button groupTable = new Button(new RouterLink("Groups ", GroupView.class));
        Button peopleTable = new Button(new RouterLink("People ", PeopleView.class));
        Button subjectTable = new Button(new RouterLink("Subjects ", SubjectView.class));
        Button markTable = new Button(new RouterLink("Marks ", MarkView.class));
        menu.setSpacing(true);
        menu.add(groupTable, peopleTable, subjectTable, markTable);
        add(label, menu, new H1("Log in"));

        TextField userName = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        Button button = new Button("Log in");
        Button logout = new Button("Log out");
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        button.setEnabled(token == null);
        logout.setEnabled(token != null);
        HorizontalLayout horizontalLayout = new HorizontalLayout(button, logout);

        add(userName, password, horizontalLayout);

        button.addClickListener(e ->
        {

            try {
                String tokenString = securityService.getToken(new MyUser(userName.getValue(), password.getValue()));
                Notification.show("You have successfully logged in");
                button.setEnabled(false);
                VaadinSession.getCurrent().getSession().setAttribute("token", tokenString);
//                VaadinSession.getCurrent().getSession().setMaxInactiveInterval(18000);
                System.out.println(tokenString);
                logout.setEnabled(true);
            } catch (WebClientException ex) {
                Notification.show("Invalid data, try again ");
            }

        });

        logout.addClickListener(event ->
        {
            VaadinSession.getCurrent().getSession().setAttribute("token", null);
            button.setEnabled(true);
            logout.setEnabled(false);
        });

    }
}
