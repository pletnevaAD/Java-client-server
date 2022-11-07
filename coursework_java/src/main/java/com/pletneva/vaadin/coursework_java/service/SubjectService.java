package com.pletneva.vaadin.coursework_java.service;

import com.pletneva.vaadin.coursework_java.entity.Group;
import com.pletneva.vaadin.coursework_java.entity.Subject;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SubjectService{
    private final WebClient webClient;

    public SubjectService() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    public Subject[] listSubject() {
        return webClient
                .get()
                .uri("/sub")
                .retrieve()
                .bodyToMono(Subject[].class)
                .block();
    }


    public Subject findById(Integer id) {
        return webClient
                .get()
                .uri("/sub/" + id)
                .retrieve()
                .bodyToMono(Subject.class)
                .block();
    }

    public void addSubject(Subject subject) {
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        if (token == null) {
            Notification.show("The token was not found. You need to log in.");
            return;
        }
        ResponseEntity respone = webClient
                .post()
                .uri("/sub/add")
                .headers(headers -> headers.setBearerAuth(token.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(subject)
                .retrieve()
                .toBodilessEntity()
                .block();

        System.out.println(respone);
    }

    public void deleteSubject(Subject subject) {
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        if (token == null) {
            Notification.show("The token was not found. You need to log in.");
            return;
        }
        String respone = webClient.method(HttpMethod.DELETE)
                .uri("/sub/delete")
                .headers(headers -> headers.setBearerAuth(token.toString()))
                .bodyValue(subject)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(respone);
    }

}
