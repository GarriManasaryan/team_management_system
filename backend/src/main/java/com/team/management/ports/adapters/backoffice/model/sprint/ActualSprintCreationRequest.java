package com.team.management.ports.adapters.backoffice.model.sprint;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public record ActualSprintCreationRequest(
        @NotNull String name,
        @NotNull String planningId,
        @NotNull String startAt
) {
}
