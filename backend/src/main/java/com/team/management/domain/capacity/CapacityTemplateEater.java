package com.team.management.domain.capacity;

import org.jetbrains.annotations.NotNull;

public record CapacityTemplateEater(
        @NotNull String capacityTemplateId,
        @NotNull String capacityEaterId
) {
}
