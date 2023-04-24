package com.team.management.ports.adapters.backoffice.model.capacity.template;

import org.jetbrains.annotations.Nullable;

public record CapacityTemplatePatchRequest(
        @Nullable String title,
        @Nullable String description
) {
}
