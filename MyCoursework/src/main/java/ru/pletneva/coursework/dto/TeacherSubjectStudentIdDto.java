package ru.pletneva.coursework.dto;

import java.math.BigDecimal;

public class TeacherSubjectStudentIdDto {
    BigDecimal teacherId;
    BigDecimal subjectId;
    BigDecimal studentId;

    public BigDecimal getTeacherId() {
        return teacherId;
    }

    public BigDecimal getSubjectId() {
        return subjectId;
    }

    public BigDecimal getStudentId() {
        return studentId;
    }

}
