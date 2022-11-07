package ru.pletneva.coursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.pletneva.coursework.entity.Mark;
import ru.pletneva.coursework.entity.Subject;
import ru.pletneva.coursework.exceptions.NotFoundException;
import ru.pletneva.coursework.dto.TeacherSubjectStudentIdDto;
import ru.pletneva.coursework.service.MarkService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/mark")
public class MarkController {
    MarkService markService;

    @GetMapping
    public ResponseEntity<List<Mark>> getAllMarks() {
        List<Mark> markList = markService.listMarks();
        return new ResponseEntity<>(markList, HttpStatus.OK);
    }

    @PostMapping("/add/{mark}")
    public ResponseEntity<?> addMark(@RequestBody TeacherSubjectStudentIdDto teacherSubjectStudentId,
                                     @PathVariable("mark") BigDecimal markValue) {
        try {
            Mark mark = markService.addMark(markValue, teacherSubjectStudentId.getTeacherId(),
                    teacherSubjectStudentId.getSubjectId(), teacherSubjectStudentId.getStudentId());
            return new ResponseEntity<>(mark, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<Mark>> deleteMark(@RequestBody Subject subject) {
        try {
            markService.deleteMark(subject);
            return new ResponseEntity<>(markService.listMarks(), HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/byLastName/{groupId}")
    public ResponseEntity<List<Mark>> getMarksByLastNameInGroup(@RequestBody String lastName,
                                                                @PathVariable(name = "groupId") BigDecimal id) {
        try {
            List<Mark> people = markService.findMark(lastName, id);
            return new ResponseEntity<>(people, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Autowired
    public void setMarkService(MarkService markService) {
        this.markService = markService;
    }
}
