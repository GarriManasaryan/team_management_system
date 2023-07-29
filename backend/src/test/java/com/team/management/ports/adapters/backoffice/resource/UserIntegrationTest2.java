package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.DatabaseBaseTest;
import com.team.management.domain.user.Role;
import com.team.management.domain.user.User;
import com.team.management.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserIntegrationTest2 extends DatabaseBaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void test() {
        userRepository.all();
    }

    @Test
    void test2() {
        userRepository.save(User.of("dsada", "asasc", Role.MANAGER));
        userRepository.all().forEach(System.out::println);
    }

}
