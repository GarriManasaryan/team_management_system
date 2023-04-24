package com.team.management.domain.planning;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface SprintPlanningRepository {

    void save(@NotNull SprintPlanning sprintPlanning);

    void delete(@NotNull String id);

    void patch(
            @NotNull String id,
            @Nullable String name,
            @Nullable String timeUnitId,
            @Nullable String capacityTemplateId,
            @Nullable Integer maxCapacity
    );

    List<SprintPlanning> search(
            @Nullable String name,
            @Nullable String timeUnitId,
            @Nullable String capacityTemplateId,
            @Nullable Integer maxCapacity
    );

    Optional<SprintPlanning> ofId(@NotNull String id);

}
