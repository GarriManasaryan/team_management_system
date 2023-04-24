package com.team.management.ports.adapters.backoffice.model.capacity.template;

import org.jetbrains.annotations.NotNull;

public record CapacityTemplateCreationRequest(
        @NotNull String id,
        @NotNull String title,
        @NotNull String description
) {
}
