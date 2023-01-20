package com.team.management.ports.adapters.backoffice.model.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record UserPatchBackOfficeModel(
        @NotNull String id,
        @Nullable String name,
        @Nullable String email,
        @Nullable String role

) {
}
