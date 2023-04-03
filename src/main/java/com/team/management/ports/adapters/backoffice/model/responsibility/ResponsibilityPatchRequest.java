package com.team.management.ports.adapters.backoffice.model.responsibility;

import org.jetbrains.annotations.Nullable;

public record ResponsibilityPatchRequest(
        @Nullable String name,
        @Nullable String outerId,
        @Nullable String label
) {
}
