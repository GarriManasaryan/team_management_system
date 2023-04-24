package com.team.management.ports.adapters.backoffice.model.capacity.eater;

import com.team.management.domain.capacity.CapacityEaterStatus;
import com.team.management.domain.capacity.CapacityPriority;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public record CapacityEaterCreationRequest(
        @NotNull String name,
        @NotNull Integer duration,
        @NotNull String typeId,
        @NotNull String priority,
        @NotNull String deadline,
        @NotNull String status
) {
}
