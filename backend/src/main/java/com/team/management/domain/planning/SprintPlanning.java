package com.team.management.domain.planning;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

public record SprintPlanning(
        @NotNull String id,
        @NotNull String name,
        @NotNull String timeUnitId,
        @NotNull String capacityTemplateId,
        @NotNull Integer maxCapacity
) {

    public static SprintPlanning of(
            @NotNull String name,
            @NotNull String timeUnitId,
            @NotNull String capacityTemplateId,
            @NotNull Integer maxCapacity
    ) {
        return new SprintPlanning(
                IdGenerator.generate("spl"),
                name,
                timeUnitId,
                capacityTemplateId,
                maxCapacity
        );

    }

}
