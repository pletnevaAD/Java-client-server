package com.pletneva.vaadin.coursework_java.entity;

import lombok.Data;

@Data
public class People {
    private Integer id;

    private String firstName;

    private String lastName;

    private String patherName;

    private String type;

    private Group group;

    public People() {
    }

    public People(String firstName, String lastName,
                  String patherName, Group group, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patherName = patherName;
        this.group = group;
        this.type = type;
    }
}
