package com.team.management.domain.planning;

import org.jetbrains.annotations.NotNull;

public record SprintPlanningParticipantCapacity(
        @NotNull String userId,
        @NotNull String planningId,
        @NotNull String capacityEaterId,
        @NotNull Integer duration
) {
}
