package com.team.management.domain.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void delete(@NotNull String id);

    void replaceEmail(@NotNull String email, @NotNull String id);

    void replaceRole(@NotNull Role role, @NotNull String id);

    void patch(
            @NotNull String id,
            @Nullable String role,
            @Nullable String email,
            @Nullable String name
    );

    @NotNull
    Optional<User> ofId(@NotNull String userId);

    @NotNull
    String save(@NotNull User user);

    @NotNull List<User> all();

    @NotNull List<User> searchByEmail(@NotNull String email);

    @NotNull List<User> searchByRole(@NotNull Role role);

    @NotNull List<User> search(
            @Nullable String role,
            @Nullable String email,
            @Nullable String name

    );

}
