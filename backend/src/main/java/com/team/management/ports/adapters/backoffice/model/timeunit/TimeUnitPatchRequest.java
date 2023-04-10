package com.team.management.ports.adapters.backoffice.model.timeunit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record TimeUnitPatchRequest(
        @NotNull String id,
        @Nullable String name,
        @Nullable Integer duration
) {
}
