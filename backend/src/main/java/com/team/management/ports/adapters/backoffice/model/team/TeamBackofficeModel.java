package com.team.management.ports.adapters.backoffice.model.team;

import org.jetbrains.annotations.NotNull;

public record TeamBackofficeModel(
        @NotNull String id,
        @NotNull String name,
        @NotNull String managerId
) {
}
