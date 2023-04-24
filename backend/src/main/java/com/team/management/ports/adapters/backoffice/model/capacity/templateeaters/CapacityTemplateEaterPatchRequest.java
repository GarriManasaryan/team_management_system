package com.team.management.ports.adapters.backoffice.model.capacity.templateeaters;

import org.jetbrains.annotations.NotNull;

public record CapacityTemplateEaterPatchRequest(
        @NotNull String capacityTemplateId,
        @NotNull String capacityEaterId
) {
}
