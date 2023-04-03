package com.team.management.domain.responsibility;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

public record Responsibility(
        @NotNull String id,
        @NotNull String name,
        @NotNull String outerId,
        @NotNull String label
) {
    public static Responsibility of(
            @NotNull String name,
            @NotNull String outerId,
            @NotNull String label
    ) {
        return new Responsibility(
                IdGenerator.generate("resp"),
                name,
                outerId,
                label
        );
    }
}
