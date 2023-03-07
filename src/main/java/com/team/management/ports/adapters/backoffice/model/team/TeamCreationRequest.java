package com.team.management.ports.adapters.backoffice.model.team;

import org.jetbrains.annotations.NotNull;

public record TeamCreationRequest(
        @NotNull String name,
        @NotNull String managerId
) {
}
