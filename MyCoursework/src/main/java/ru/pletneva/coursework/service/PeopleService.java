package ru.pletneva.coursework.service;

import ru.pletneva.coursework.entity.Group;
import ru.pletneva.coursework.entity.People;

import java.math.BigDecimal;
import java.util.List;

public interface PeopleService {
    List<People> listPeople();

    List<People> findByGroup(Group group);

    People addPeople(People people, BigDecimal groupId);

    void deletePeople(String firstName, String lastName);

    People findById(BigDecimal id);

    List<People> findByGroupName(String groupName);

    People addPeople(People people);
}
