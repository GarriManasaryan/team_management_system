package com.team.management.application;

import com.team.management.domain.user.Role;
import com.team.management.domain.user.User;
import com.team.management.domain.user.UserRepository;
import com.team.management.ports.adapters.backoffice.model.UserBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.UserCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(@NotNull String id) {
        userRepository.delete(id);
    }

    @NotNull
    public String save(@NotNull UserCreationRequest userCreationRequest) {
        return userRepository.save(User.of(
                userCreationRequest.name(),
                userCreationRequest.email(),
                Role.valueOf(userCreationRequest.role())
        ));
    }

    public @NotNull List<UserBackofficeModel> all() {
        return userRepository.all().stream()
                .map(user -> new UserBackofficeModel(
                        user.id(),
                        user.name(),
                        user.email(),
                        user.role()
                ))
                .toList();
    }
}
