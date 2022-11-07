package ru.pletneva.coursework.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pletneva.coursework.entity.Group;

import java.math.BigDecimal;

@Repository
public interface GroupRepository extends CrudRepository<Group, BigDecimal> {
}
