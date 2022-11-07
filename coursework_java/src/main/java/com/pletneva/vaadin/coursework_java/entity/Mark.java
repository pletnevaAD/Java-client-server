package com.pletneva.vaadin.coursework_java.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Mark {
    private Integer id;

    private People student;

    private Subject subject;

    private People teacher;

    private BigDecimal value;

    public Mark() {
    }

    public Mark(People student,
                Subject subject, People teacher, BigDecimal value) {
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
        this.value = value;
    }
}
