package com.team.management.ports.adapters.backoffice.model.user;

import com.team.management.domain.user.Role;
import org.jetbrains.annotations.NotNull;

public record UserBackofficeModel(
        @NotNull String id,
        @NotNull String name,
        @NotNull String email,
        @NotNull Role role
) {
}

