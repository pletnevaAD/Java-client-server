package com.pletneva.vaadin.coursework_java.entity;

import lombok.Data;

@Data
public class Group {
    private Integer id;

    private String name;

    public Group(){}

    public Group(String name) {
        this.name = name;
    }
}
