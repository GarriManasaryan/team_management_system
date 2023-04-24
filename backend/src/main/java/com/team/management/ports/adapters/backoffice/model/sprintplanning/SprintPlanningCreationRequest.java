package com.team.management.ports.adapters.backoffice.model.sprintplanning;

import org.jetbrains.annotations.NotNull;

public record SprintPlanningCreationRequest(
        @NotNull String name,
        @NotNull String timeUnitId,
        @NotNull String capacityTemplateId,
        @NotNull Integer maxCapacity
) {
}
