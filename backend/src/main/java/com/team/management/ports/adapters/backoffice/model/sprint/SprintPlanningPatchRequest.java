package com.team.management.ports.adapters.backoffice.model.sprint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record SprintPlanningPatchRequest(
        @NotNull String id,
        @Nullable String name,
        @Nullable String timeUnitId,
        @Nullable String capacityTemplateId,
        @Nullable Integer maxCapacity
) {
}
