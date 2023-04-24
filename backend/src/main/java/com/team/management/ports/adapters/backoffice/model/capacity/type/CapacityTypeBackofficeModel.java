package com.team.management.ports.adapters.backoffice.model.capacity.type;

import org.jetbrains.annotations.NotNull;

public record CapacityTypeBackofficeModel(
        @NotNull String id,
        @NotNull String name,
        @NotNull Integer defaultDuration
) {
}
