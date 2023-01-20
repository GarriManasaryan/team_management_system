package com.team.management.ports.adapters.backoffice.model.user;

import org.jetbrains.annotations.NotNull;

public record UserNewEntityBackOfficeModel(
        @NotNull String id,
        @NotNull String message
) {
}
