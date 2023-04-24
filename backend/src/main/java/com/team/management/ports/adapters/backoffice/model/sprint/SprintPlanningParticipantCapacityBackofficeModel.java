package com.team.management.ports.adapters.backoffice.model.sprint;

import org.jetbrains.annotations.NotNull;

public record SprintPlanningParticipantCapacityBackofficeModel(
        @NotNull String userId,
        @NotNull String planningId,
        @NotNull String capacityEaterId,
        @NotNull Integer duration
) {
}
