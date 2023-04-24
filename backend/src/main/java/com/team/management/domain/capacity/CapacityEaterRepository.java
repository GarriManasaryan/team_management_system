package com.team.management.domain.capacity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface CapacityEaterRepository {

    void save(@NotNull CapacityEater capacityEater);

    void delete(@NotNull String id);

    void patch(
            @NotNull String id,
            @Nullable String name,
            @Nullable Integer duration,
            @Nullable String typeId,
            @Nullable CapacityPriority priority,
            @Nullable OffsetDateTime deadline,
            @Nullable CapacityEaterStatus status
    );

    List<CapacityEater> search(
            @Nullable String name,
            @Nullable Integer duration,
            @Nullable String typeId,
            @Nullable CapacityPriority priority,
            @Nullable OffsetDateTime deadline,
            @Nullable CapacityEaterStatus status
    );

    Optional<CapacityEater> ofId(@NotNull String id);

}
