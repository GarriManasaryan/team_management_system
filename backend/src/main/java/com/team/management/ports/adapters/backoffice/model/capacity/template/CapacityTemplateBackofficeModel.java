package com.team.management.ports.adapters.backoffice.model.capacity.template;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record CapacityTemplateBackofficeModel(
        @NotNull String id,
        @NotNull String title,
        @NotNull String description
) {
}
