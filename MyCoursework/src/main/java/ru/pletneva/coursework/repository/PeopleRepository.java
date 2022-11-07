package ru.pletneva.coursework.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pletneva.coursework.entity.Group;
import ru.pletneva.coursework.entity.People;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends CrudRepository<People, BigDecimal> {

    @Query("SELECT s FROM People s WHERE s.group=?1")
    List<People> findPeopleByGroup(Group group);

    @Query("SELECT s FROM People s WHERE s.firstName=?1 and s.lastName=?2")
    Optional<People> findPeopleByFirstNameAndLastName(String firstName, String lastName);

    @Transactional
    @Modifying
    @Query("DELETE FROM People s WHERE s.firstName=?1 and s.lastName=?2")
    void deletePeopleByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT s FROM People s JOIN s.group g WHERE g.name=?1")
    List<People> findPeopleByGroup_Name(String groupName);
}
