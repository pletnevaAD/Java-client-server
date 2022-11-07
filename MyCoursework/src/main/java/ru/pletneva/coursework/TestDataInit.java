package ru.pletneva.coursework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.pletneva.coursework.entity.Group;
import ru.pletneva.coursework.entity.MyUser;
import ru.pletneva.coursework.repository.GroupRepository;
import ru.pletneva.coursework.repository.PeopleRepository;
import ru.pletneva.coursework.repository.UserRepository;

import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PeopleRepository peopleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEncoder;

    @Override
    public void run(String... args) throws Exception {
        groupRepository.save(new Group("FirstGroup"));
        groupRepository.save(new Group("SecondGroup"));

        userRepository.save(new MyUser("14admin", pwdEncoder.encode("123")
                , Collections.singletonList("ROLE_ADMIN")));
    }
}
