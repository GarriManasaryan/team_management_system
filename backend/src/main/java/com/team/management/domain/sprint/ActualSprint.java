package com.team.management.domain.sprint;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public record ActualSprint(
        @NotNull String id,
        @NotNull String name,
        @NotNull String planningId,
        @NotNull OffsetDateTime startAt
) {
    public static ActualSprint of(
            @NotNull String name,
            @NotNull String planningId,
            @NotNull OffsetDateTime startAt
    ) {
        return new ActualSprint(
                IdGenerator.generate("asp"),
                name,
                planningId,
                startAt
        );
    }
}
