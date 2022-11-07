package com.pletneva.vaadin.coursework_java.service;

import com.pletneva.vaadin.coursework_java.entity.MyUser;
import com.pletneva.vaadin.coursework_java.entity.UserAuth;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;

import org.springframework.stereotype.Component;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SecurityService {

    public String getToken(MyUser user)
    {

        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        UserAuth userAuth = webClient
                .post()
                .uri("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(UserAuth.class)
                .block();
        if (userAuth == null)
        {
            return null;
        }
        return userAuth.getToken();
    }
}
