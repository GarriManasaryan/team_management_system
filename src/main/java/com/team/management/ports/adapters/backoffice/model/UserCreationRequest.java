package com.team.management.ports.adapters.backoffice.model;

import org.jetbrains.annotations.NotNull;

public record UserCreationRequest(
        @NotNull String name,
        @NotNull String email,
        @NotNull String role
) {
}
