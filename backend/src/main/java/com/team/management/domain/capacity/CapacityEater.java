package com.team.management.domain.capacity;

import org.jetbrains.annotations.NotNull;

public record CapacityEater(
        @NotNull String id,
        @NotNull String name,
        @NotNull Integer duration,
        @NotNull String typeId,
        @NotNull CapacityPriority priority
) {
}
