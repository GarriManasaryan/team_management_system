package com.team.management.ports.adapters.backoffice.model.sprint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;

public record ActualSprintPatchRequest(
        @Nullable String name,
        @Nullable String planningId,
        @Nullable String startAt
) {
}
