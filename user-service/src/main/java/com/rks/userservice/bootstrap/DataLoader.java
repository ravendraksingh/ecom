package com.rks.userservice.bootstrap;

import com.rks.userservice.domains.User;
import com.rks.userservice.repositories.UserRepository;
import com.rks.userservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        log.info("DataLoader");
        loadData();
    }

    private void loadData() {
        Long count = userRepository.count();
        if (!(count>0)) {
            log.info("User count is zero. Creating new user");
            User u1 = User.builder().email("ravendraksingh@gmail.com").name("Ravendra Singh").build();
            userService.createNewUser(u1);
        }
    }
}
