package com.team.management.domain.user;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UserRepository {

    void delete(@NotNull String id);

    @NotNull
    String save(@NotNull User user);

    @NotNull List<User> all();

}
