package ru.pletneva.coursework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pletneva.coursework.entity.Group;
import ru.pletneva.coursework.exceptions.NotFoundException;
import ru.pletneva.coursework.repository.GroupRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<Group> listGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public Group findById(BigDecimal id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            return optionalGroup.get();
        } else {
            throw new NotFoundException("Group not found");
        }
    }

    @Override
    public Group addGroup(Group group) {
        return groupRepository.save(group);
    }

}
