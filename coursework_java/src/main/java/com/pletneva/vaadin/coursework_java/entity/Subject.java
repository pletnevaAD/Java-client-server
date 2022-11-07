package com.pletneva.vaadin.coursework_java.entity;

import lombok.Data;

@Data
public class Subject {
    private Integer id;

    private String name;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }
}
