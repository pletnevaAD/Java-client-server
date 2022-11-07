package ru.pletneva.coursework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pletneva.coursework.entity.Group;
import ru.pletneva.coursework.entity.People;
import ru.pletneva.coursework.exceptions.NotFoundException;
import ru.pletneva.coursework.repository.PeopleRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private GroupService groupService;

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public List<People> listPeople() {
        return (List<People>) peopleRepository.findAll();
    }

    @Override
    public List<People> findByGroup(Group group) {
        List<People> listPeople = peopleRepository.findPeopleByGroup(group);
        if (listPeople.isEmpty()) {
            throw new NotFoundException("People not found");
        } else {
            return listPeople;
        }
    }

    @Override
    public People addPeople(People people, BigDecimal groupId) {
        if (!Objects.equals(people.getType(), "S") && !Objects.equals(people.getType(), "P")) {
            throw new IllegalArgumentException("Type of person must be S or P");
        }

        if (Objects.equals(people.getType(), "P") && !Objects.equals(groupId, null)) {
            throw new IllegalArgumentException("The teacher mustn't be in the group");
        }
        Group group = groupService.findById(groupId);
        people.setGroup(group);
        return peopleRepository.save(people);
    }

    @Override
    public void deletePeople(String firstName, String lastName) {
        Optional<People> peopleOptional = peopleRepository.findPeopleByFirstNameAndLastName(firstName, lastName);
        if (peopleOptional.isPresent()) {
            peopleRepository.deletePeopleByFirstNameAndLastName(firstName, lastName);
        } else {
            throw new NotFoundException("Person not found");
        }
    }

    @Override
    public People findById(BigDecimal id) {
        Optional<People> optionalPeople = peopleRepository.findById(id);
        if (optionalPeople.isPresent()) {
            return optionalPeople.get();
        } else {
            throw new NotFoundException("Person not found");
        }
    }

    @Override
    public List<People> findByGroupName(String groupName) {
        List<People> peopleList = peopleRepository.findPeopleByGroup_Name(groupName);
        if ((peopleList.isEmpty())){
            throw new NotFoundException("Group not found");
        }
        return peopleList;
    }

    @Override
    public People addPeople(People people) {
        return peopleRepository.save(people);
    }

}
