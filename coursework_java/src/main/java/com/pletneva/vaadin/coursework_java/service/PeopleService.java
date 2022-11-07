package com.pletneva.vaadin.coursework_java.service;

import com.pletneva.vaadin.coursework_java.dto.FirstAndLastNameDto;
import com.pletneva.vaadin.coursework_java.entity.Group;
import com.pletneva.vaadin.coursework_java.entity.People;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PeopleService{

    private final WebClient webClient;

    public PeopleService() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    public People[] listPeople() {
        return webClient
                .get()
                .uri("/people")
                .retrieve()
                .bodyToMono(People[].class)
                .block();
    }

    public People[] listTeacher() {
        return webClient
                .get()
                .uri("/people/teachers")
                .retrieve()
                .bodyToMono(People[].class)
                .block();
    }

    public People[] listStudent() {
        return webClient
                .get()
                .uri("/people/students")
                .retrieve()
                .bodyToMono(People[].class)
                .block();
    }

    public void addPeople(People people, Integer groupId) {
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        if (token == null)
        {
            Notification.show("The token was not found. You need to log in.");
            return;
        }
        ResponseEntity respone = webClient
                .post()
                .uri("/people/add/" + groupId)
                .headers(headers -> headers.setBearerAuth(token.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(people)
                .retrieve()
                .toBodilessEntity()
                .block();

        System.out.println(respone);
    }

    public void deletePeople(String firstName, String lastName) {
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        if (token == null)
        {
            Notification.show("The token was not found. You need to log in.");
            return;
        }
        String respone = webClient.method(HttpMethod.DELETE)
                .uri("/people/delete")
                .headers(headers -> headers.setBearerAuth(token.toString()))
                .bodyValue(new FirstAndLastNameDto(firstName, lastName))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(respone);
    }

    public void deleteById(Integer id) {
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        if (token == null)
        {
            Notification.show("The token was not found. You need to log in.");
            return;
        }
        String respone = webClient.method(HttpMethod.DELETE)
                .uri("/people/delete/"+id)
                .headers(headers -> headers.setBearerAuth(token.toString()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(respone);
    }

    public People findById(Integer id) {
        return webClient
                .get()
                .uri("/people/" + id)
                .retrieve()
                .bodyToMono(People.class)
                .block();
    }

    public People[] findPeopleByFirstNameAndLastName(String firstName, String lastName){
        return webClient
                .method(HttpMethod.GET)
                .uri("/people/byFLName")
                .bodyValue(new FirstAndLastNameDto(firstName, lastName))
                .retrieve()
                .bodyToMono(People[].class)
                .block();
    }

}
