package com.team.management.ports.adapters.backoffice.model.capacity.eater;

import com.team.management.application.IdGenerator;
import com.team.management.domain.capacity.CapacityEaterStatus;
import com.team.management.domain.capacity.CapacityPriority;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public record CapacityEaterBackofficeModel(
        @NotNull String id,
        @NotNull String name,
        @NotNull Integer duration,
        @NotNull String typeId,
        @NotNull CapacityPriority priority,
        @NotNull OffsetDateTime deadline,
        @NotNull String status
) {
}
