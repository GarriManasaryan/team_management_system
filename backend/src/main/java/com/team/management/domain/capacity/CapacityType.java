package com.team.management.domain.capacity;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

public record CapacityType(
        @NotNull String id,
        @NotNull String name,
        @NotNull Integer defaultDuration
) {
    public static CapacityType ofId(
            @NotNull String name,
            @NotNull Integer defaultDuration
    ) {
        return new CapacityType(
                IdGenerator.generate("ctp"),
                name,
                defaultDuration
        );
    }
}
