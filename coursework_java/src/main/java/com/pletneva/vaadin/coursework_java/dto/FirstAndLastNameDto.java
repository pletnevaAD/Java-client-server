package com.pletneva.vaadin.coursework_java.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirstAndLastNameDto {
    String firstName;
    String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
