package com.pletneva.vaadin.coursework_java.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TeacherSubjectStudentIdDto {
    Integer teacherId;
    Integer subjectId;
    Integer studentId;

    public Integer getTeacherId() {
        return teacherId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public Integer getStudentId() {
        return studentId;
    }

}
