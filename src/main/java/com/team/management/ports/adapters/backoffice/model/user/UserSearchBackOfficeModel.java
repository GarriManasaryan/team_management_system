package com.team.management.ports.adapters.backoffice.model.user;

import org.jetbrains.annotations.Nullable;

public record UserSearchBackOfficeModel(
        @Nullable String name,
        @Nullable String email,
        @Nullable String role

) {
}
