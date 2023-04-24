package com.team.management.domain.capacity;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public record CapacityEater(
        @NotNull String id,
        @NotNull String name,
        @NotNull Integer duration,
        @NotNull String typeId,
        @NotNull CapacityPriority priority,
        @NotNull OffsetDateTime deadline,
        @NotNull CapacityEaterStatus status
) {
    public static CapacityEater of(
            @NotNull String name,
            @NotNull Integer duration,
            @NotNull String typeId,
            @NotNull CapacityPriority priority,
            @NotNull OffsetDateTime deadline,
            @NotNull CapacityEaterStatus status
    ) {
        return new CapacityEater(
                IdGenerator.generate("cpe"),
                name,
                duration,
                typeId,
                priority,
                deadline,
                status
        );
    }
}
