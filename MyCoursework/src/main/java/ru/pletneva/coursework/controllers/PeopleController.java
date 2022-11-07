package ru.pletneva.coursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.pletneva.coursework.dto.FirstAndLastNameDto;
import ru.pletneva.coursework.entity.Group;
import ru.pletneva.coursework.entity.People;
import ru.pletneva.coursework.exceptions.NotFoundException;
import ru.pletneva.coursework.service.PeopleService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    PeopleService peopleService;

    @PostMapping("/add")
    public People addPeople(@RequestBody People newPeople) {
        return peopleService.addPeople(newPeople);
    }

    @PostMapping("/add/{groupId}")
    public People addPeopleInGroup(@RequestBody People newPeople, @PathVariable("groupId") BigDecimal groupId) {
        return peopleService.addPeople(newPeople, groupId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<People>> deletePeople(@RequestBody FirstAndLastNameDto firstAndLastName) {
        try {
            peopleService.deletePeople(firstAndLastName.getFirstName(), firstAndLastName.getLastName());
            return new ResponseEntity<>(peopleService.listPeople(), HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<People>> getAllPeople() {
        List<People> peopleList = peopleService.listPeople();
        return new ResponseEntity<>(peopleList, HttpStatus.OK);
    }

    @GetMapping("/byGroup")
    public ResponseEntity<List<People>> getPeopleByGroup(@RequestBody Group group) {
        try {
            List<People> peopleList = peopleService.findByGroup(group);
            return new ResponseEntity<>(peopleList, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<People> getPeopleById(@PathVariable("id") BigDecimal id) {
        try {
            People people = peopleService.findById(id);
            return new ResponseEntity<>(people, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/byGroupName")
    public ResponseEntity<List<People>> getPeopleByGroupName(@RequestBody String groupName) {
        try {
            List<People> people = peopleService.findByGroupName(groupName);
            return new ResponseEntity<>(people, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Autowired
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
}
