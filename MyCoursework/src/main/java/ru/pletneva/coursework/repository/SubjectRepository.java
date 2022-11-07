package ru.pletneva.coursework.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pletneva.coursework.entity.Subject;

import java.math.BigDecimal;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, BigDecimal> {
}
