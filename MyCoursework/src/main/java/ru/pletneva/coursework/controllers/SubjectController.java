package ru.pletneva.coursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.pletneva.coursework.entity.Subject;
import ru.pletneva.coursework.exceptions.NotFoundException;
import ru.pletneva.coursework.service.SubjectService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/sub")
public class SubjectController {
    SubjectService subjectService;

    @PostMapping("/add")
    public ResponseEntity<?> addSubject(@RequestBody Subject newSubject) {
        Subject subject = subjectService.addSubject(newSubject);
        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable("id") BigDecimal id) {
        try {
            Subject subject = subjectService.findById(id);
            return new ResponseEntity<>(subject, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Autowired
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
}
