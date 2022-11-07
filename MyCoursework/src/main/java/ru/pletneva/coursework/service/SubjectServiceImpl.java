package ru.pletneva.coursework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pletneva.coursework.entity.Subject;
import ru.pletneva.coursework.exceptions.NotFoundException;
import ru.pletneva.coursework.repository.SubjectRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subject findById(BigDecimal id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isPresent()) {
            return optionalSubject.get();
        } else {
            throw new NotFoundException("Subject not found");
        }
    }

    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

}
