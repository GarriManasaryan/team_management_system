package com.team.management.ports.adapters.backoffice.model.team;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record TeamUpdateRequest(
        @Nullable String name,
        @Nullable String managerId
) {
}
