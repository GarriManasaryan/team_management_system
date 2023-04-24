package com.team.management.ports.adapters.backoffice.model.capacity.eater;

import com.team.management.domain.capacity.CapacityEaterStatus;
import com.team.management.domain.capacity.CapacityPriority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;

public record CapacityEaterPatchRequest(
        @NotNull String id,
        @Nullable String name,
        @Nullable Integer duration,
        @Nullable String typeId,
        @Nullable String priority,
        @Nullable String deadline,
        @Nullable String status
) {
}
