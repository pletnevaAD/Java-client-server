package ru.pletneva.coursework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pletneva.coursework.entity.Mark;
import ru.pletneva.coursework.entity.People;
import ru.pletneva.coursework.entity.Subject;
import ru.pletneva.coursework.exceptions.NotFoundException;
import ru.pletneva.coursework.repository.MarkRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MarkServiceImpl implements MarkService {

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MarkRepository markRepository;

    @Override
    public List<Mark> listMarks() {
        return (List<Mark>) markRepository.findAll();
    }

    @Override
    public Mark addMark(BigDecimal markValue, BigDecimal teacherId, BigDecimal subjectId, BigDecimal studentId) {
        if (markRepository.findMarkByStudentIdAndSubjectId(studentId, subjectId).isPresent()) {
            throw new IllegalArgumentException("Student already have mark for this subject");
        }
        People teacher = peopleService.findById(teacherId);
        if (!Objects.equals(teacher.getType(), "P")) {
            throw new IllegalArgumentException("The second argument must be teacherId");
        }
        Mark mark = new Mark();
        mark.setTeacher(teacher);
        Subject subject = subjectService.findById(subjectId);
        mark.setSubject(subject);
        People student = peopleService.findById(studentId);
        if (!Objects.equals(student.getType(), "S")) {
            throw new IllegalArgumentException("The last argument must be studentId");
        }
        mark.setStudent(student);
        mark.setValue(markValue);
        return markRepository.save(mark);
    }

    @Override
    public List<Mark> findMark(String lastName, BigDecimal groupId) {
        List<Mark> marksList = markRepository.findMarkByStudent_LastNameAndStudent_Group_Id(lastName, groupId);
        if (marksList.isEmpty()) {
            throw new NotFoundException("Mark not found");
        }
        return marksList;
    }

    @Override
    public Mark findMark(String studentLastName, String teacherLastName, String subject) {
        Optional<Mark> optionalMark = markRepository.findMarkByStudent_LastNameAndTeacher_LastNameAndSubject_Name(studentLastName, teacherLastName, subject);
        if (optionalMark.isPresent()) {
            return optionalMark.get();
        } else {
            throw new NotFoundException("Mark not found");
        }
    }

    @Override
    public void deleteMark(Subject subject) {
        Optional<Mark> markOptional = markRepository.findBySubject(subject);
        if (markOptional.isPresent()) {
            markRepository.deleteMarksBySubject(subject);
        } else {
            throw new NotFoundException("Mark not found");
        }
    }

}
