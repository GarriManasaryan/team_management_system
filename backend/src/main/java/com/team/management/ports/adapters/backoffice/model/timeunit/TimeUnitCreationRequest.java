package com.team.management.ports.adapters.backoffice.model.timeunit;

import org.jetbrains.annotations.NotNull;

public record TimeUnitCreationRequest(
        @NotNull String name,
        @NotNull Integer duration
) {
}
