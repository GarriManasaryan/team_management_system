package com.team.management.domain.planning;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface SprintPlanningParticipantCapacityRepository {

    void save(
            @NotNull String userId,
            @NotNull String planningId,
            @NotNull String capacityEaterId,
            @NotNull Integer duration
    );

    void delete(
            @NotNull String userId,
            @NotNull String planningId,
            @NotNull String capacityEaterId
    );

    List<SprintPlanningParticipantCapacity> all();

    Optional<SprintPlanningParticipantCapacity> ofId(
            @NotNull String userId,
            @NotNull String planningId,
            @NotNull String capacityEaterId
    );

}
