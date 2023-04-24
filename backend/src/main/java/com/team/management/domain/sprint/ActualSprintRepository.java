package com.team.management.domain.sprint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ActualSprintRepository {

    void save(@NotNull ActualSprint actualSprint);

    void delete(@NotNull String id);

    void patch(
            @NotNull String id,
            @Nullable String name,
            @Nullable String planningId,
            @Nullable OffsetDateTime startAt
    );

    List<ActualSprint> search(
            @Nullable String name,
            @Nullable String planningId,
            @Nullable OffsetDateTime startAt
    );

    Optional<ActualSprint> ofId(@NotNull String id);

}
