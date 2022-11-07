package ru.pletneva.coursework.service;

import ru.pletneva.coursework.entity.Mark;
import ru.pletneva.coursework.entity.Subject;

import java.math.BigDecimal;
import java.util.List;

public interface MarkService {
    List<Mark> listMarks();

    Mark addMark(BigDecimal markValue, BigDecimal teacherId, BigDecimal subjectId, BigDecimal studentId);

    List<Mark> findMark(String lastName, BigDecimal groupId);

    Mark findMark(String studentLastName, String teacherLastName, String subject);

    void deleteMark(Subject subject);
}
