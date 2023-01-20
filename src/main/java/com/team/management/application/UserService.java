package com.team.management.application;

import com.team.management.domain.user.Role;
import com.team.management.domain.user.User;
import com.team.management.domain.user.UserRepository;
import com.team.management.ports.adapters.backoffice.model.user.UserBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.user.UserCreationRequest;
import com.team.management.ports.adapters.backoffice.model.user.UserNewEntityBackOfficeModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void replaceEmail(@NotNull String email, @NotNull String id) {
        userRepository.replaceEmail(email, id);
    }

    public void replaceRole(@NotNull String role, @NotNull String id) {
        userRepository.replaceRole(Role.valueOf(role), id);
    }

    @NotNull
    public List<UserBackofficeModel> search(@Nullable String role, @Nullable String email, @Nullable String name) {
        return userRepository.search(role, email, name).stream()
                .map(user ->  new UserBackofficeModel(
                        user.id(),
                        user.name(),
                        user.email(),
                        user.role()
                ))
                .toList();
    }

    public void patch(@NotNull String id, @Nullable String role, @Nullable String email, @Nullable String name) {
        userRepository.patch(id, role, email, name);
    }

    public @NotNull Optional<UserBackofficeModel> ofId(@NotNull String userId) {
        return userRepository.ofId(userId)
                .map(user ->  new UserBackofficeModel(
                        user.id(),
                        user.name(),
                        user.email(),
                        user.role()
                ));
    }

    @NotNull
    public UserNewEntityBackOfficeModel saveUser(@NotNull UserCreationRequest userCreationRequest) {
        return new UserNewEntityBackOfficeModel(
                userRepository.save(new User(
                        IdGenerator.generate("user"),
                        userCreationRequest.name(),
                        userCreationRequest.email(),
                        Role.valueOf(userCreationRequest.role())
                )),
                "Successfully created " + userCreationRequest.name()
        );
    }

    @NotNull
    public List<UserBackofficeModel> searchByEmail(@NotNull String email) {
        return userRepository.searchByEmail(email).stream()
                .map(user -> new UserBackofficeModel(
                        user.id(),
                        user.name(),
                        user.email(),
                        user.role()
                        ))
                .toList();
    }

    @NotNull
    public List<UserBackofficeModel> searchByRole(@NotNull String role) {
        return userRepository.searchByRole(Role.valueOf(role)).stream()
                .map(user -> new UserBackofficeModel(
                        user.id(),
                        user.name(),
                        user.email(),
                        user.role()
                        ))
                .toList();
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
