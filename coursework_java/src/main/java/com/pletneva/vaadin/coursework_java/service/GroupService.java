package com.pletneva.vaadin.coursework_java.service;

import com.pletneva.vaadin.coursework_java.entity.Group;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GroupService {

    private final WebClient webClient;

    public GroupService() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    public Group[] listGroups() {
        return webClient
                .get()
                .uri("/groups")
                .retrieve()
                .bodyToMono(Group[].class)
                .block();
    }


    public Group findById(Integer id) {
        return webClient
                .get()
                .uri("/groups/" + id)
                .retrieve()
                .bodyToMono(Group.class)
                .block();
    }

    public void addGroup(Group group) {
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        if (token == null) {
            Notification.show("The token was not found. You need to log in.");
            return;
        }
        ResponseEntity respone = webClient
                .post()
                .uri("groups/add")
                .headers(headers -> headers.setBearerAuth(token.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(group)
                .retrieve()
                .toBodilessEntity()
                .block();

        System.out.println(respone);
    }

    public void deleteGroup(Group group) {
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        if (token == null) {
            Notification.show("The token was not found. You need to log in.");
            return;
        }
        String respone = webClient.method(HttpMethod.DELETE)
                .uri("groups/delete")
                .headers(headers -> headers.setBearerAuth(token.toString()))
                .bodyValue(group)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(respone);
    }

}
