package com.pletneva.vaadin.coursework_java.service;

import com.pletneva.vaadin.coursework_java.dto.FirstAndLastNameDto;
import com.pletneva.vaadin.coursework_java.dto.TeacherSubjectStudentIdDto;
import com.pletneva.vaadin.coursework_java.entity.Mark;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class MarkService {
    private final WebClient webClient;

    public MarkService() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    public Mark[] listMark() {
        return webClient
                .get()
                .uri("/mark")
                .retrieve()
                .bodyToMono(Mark[].class)
                .block();
    }

    public void addMark(BigDecimal value, Integer teacherId, Integer subjectId, Integer studentId) {
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        if (token == null) {
            Notification.show("The token was not found. You need to log in.");
            return;
        }
        ResponseEntity respone = webClient
                .post()
                .uri("/mark/add/" + value)
                .headers(headers -> headers.setBearerAuth(token.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TeacherSubjectStudentIdDto(teacherId, subjectId, studentId))
                .retrieve()
                .toBodilessEntity()
                .block();

        System.out.println(respone);
    }

    public void deleteMark(Integer teacherId, Integer subjectId, Integer studentId) {
        Object token = VaadinSession.getCurrent().getSession().getAttribute("token");
        if (token == null) {
            Notification.show("The token was not found. You need to log in.");
            return;
        }
        String respone = webClient.method(HttpMethod.DELETE)
                .uri("/mark/delete")
                .headers(headers -> headers.setBearerAuth(token.toString()))
                .bodyValue(new TeacherSubjectStudentIdDto(teacherId, subjectId, studentId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(respone);
    }

    public Mark findById(Integer id) {
        return webClient
                .get()
                .uri("/mark/" + id)
                .retrieve()
                .bodyToMono(Mark.class)
                .block();
    }

}
