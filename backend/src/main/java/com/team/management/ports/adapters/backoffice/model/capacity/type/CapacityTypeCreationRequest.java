package com.team.management.ports.adapters.backoffice.model.capacity.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record CapacityTypeCreationRequest(
        @NotNull String name,
        @NotNull Integer defaultDuration
) {
}
