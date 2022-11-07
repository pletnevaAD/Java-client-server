package ru.pletneva.coursework.service;

import ru.pletneva.coursework.entity.Subject;

import java.math.BigDecimal;

public interface SubjectService {
    Subject findById(BigDecimal id);

    Subject addSubject(Subject subject);

}
