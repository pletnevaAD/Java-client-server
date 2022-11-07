package ru.pletneva.coursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.pletneva.coursework.entity.Group;
import ru.pletneva.coursework.exceptions.NotFoundException;
import ru.pletneva.coursework.service.GroupService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    GroupService groupService;

    @PostMapping("/add")
    public ResponseEntity<?> addGroup(@RequestBody Group newGroup) {
        Group group = groupService.addGroup(newGroup);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable("id") BigDecimal id) {
        try {
            Group group = groupService.findById(id);
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groupList = groupService.listGroups();
        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

}
