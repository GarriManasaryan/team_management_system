package com.team.management.ports.adapters.backoffice.model.responsibility;

import org.jetbrains.annotations.NotNull;

public record ResponsibilityBackofficeModel(
        @NotNull String id,
        @NotNull String name,
        @NotNull String outerId,
        @NotNull String label
) {
}
