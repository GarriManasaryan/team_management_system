package com.team.management.ports.adapters.backoffice.model.sprint;

import org.jetbrains.annotations.NotNull;

public record SprintPlanningBackofficeModel(
        @NotNull String id,
        @NotNull String name,
        @NotNull String timeUnitId,
        @NotNull String capacityTemplateId,
        @NotNull Integer maxCapacity
) {
}
