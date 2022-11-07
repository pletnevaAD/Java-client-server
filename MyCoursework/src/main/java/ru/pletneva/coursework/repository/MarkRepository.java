package ru.pletneva.coursework.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pletneva.coursework.entity.Mark;
import ru.pletneva.coursework.entity.People;
import ru.pletneva.coursework.entity.Subject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends CrudRepository<Mark, BigDecimal> {

    @Query("SELECT m FROM Mark m JOIN m.student s WHERE s.lastName=?1 and s.group.id=?2")
    List<Mark> findMarkByStudent_LastNameAndStudent_Group_Id(String lastName, BigDecimal id);

    @Query("SELECT s FROM Mark s WHERE s.student.id=?1 and s.subject.id=?2")
    Optional<Mark> findMarkByStudentIdAndSubjectId(BigDecimal studentId, BigDecimal subjectId);

    @Query("SELECT m FROM Mark m JOIN m.student s join m.teacher t join m.subject sub WHERE s.lastName =?1 " +
            "and t.lastName=?2 and sub.name=?3")
    Optional<Mark> findMarkByStudent_LastNameAndTeacher_LastNameAndSubject_Name(String studentLastName,
                                                                                String teacherLastName, String subject);

    @Query("SELECT s FROM Mark s WHERE s.subject=?1")
    Optional<Mark> findBySubject(Subject subject);

    @Transactional
    @Modifying
    @Query("DELETE FROM Mark s WHERE s.subject=?1")
    void deleteMarksBySubject(Subject subject);

}
