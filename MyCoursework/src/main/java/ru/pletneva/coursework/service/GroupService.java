package ru.pletneva.coursework.service;

import ru.pletneva.coursework.entity.Group;

import java.math.BigDecimal;
import java.util.List;

public interface GroupService {
    List<Group> listGroups();

    Group findById(BigDecimal id);

    Group addGroup(Group group);
}
