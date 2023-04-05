package com.team.management.ports.adapters.backoffice.model.responsibility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record UserResponsibilityBackofficeModel(
        @Nullable String userId,
        @Nullable String respId
) {
}
