package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.DatabaseBaseTest;
import com.team.management.application.UserService;
import com.team.management.domain.responsibility.UserResponsibility;
import com.team.management.domain.user.Role;
import com.team.management.domain.user.User;
import com.team.management.domain.user.UserRepository;
import com.team.management.ports.adapters.backoffice.model.user.UserCreationRequest;
import com.team.management.ports.adapters.persistence.postgresql.PostgresqlUserRepository;
import com.team.management.ports.adapters.persistence.postgresql.PostgresqlUserResponsibility;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

public class UserIntegrationTest extends DatabaseBaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void test() {
        // одним тестом покрыл два слоя (два save из разных слоев)
        userService.save(new UserCreationRequest("casca", "ascas", "MANAGER"));
        userRepository.all().forEach(System.out::println);

    }

    @Test
    void test2() {
        userRepository.save(User.of("dsada", "asasc", Role.MANAGER));
        userRepository.all().forEach(System.out::println);
    }

}
