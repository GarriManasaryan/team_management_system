package com.team.management.domain.team;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {

    void delete(@NotNull String teamId);

    void patch(
            @NotNull String teamId,
            @Nullable String newManagerId,
            @Nullable String newName
    );

    void save(@NotNull Team team);

    List<Team> search(
            @Nullable String name,
            @Nullable String managerId
    );

    Optional<Team> ofId(@NotNull String teamId);

}
