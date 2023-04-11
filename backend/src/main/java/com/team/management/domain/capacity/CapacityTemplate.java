package com.team.management.domain.capacity;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

public record CapacityTemplate(
        @NotNull String id,
        @NotNull String title,
        @NotNull String description
) {

    public static CapacityTemplate ofId(
            @NotNull String title,
            @NotNull String description
    ) {
        return new CapacityTemplate(
                IdGenerator.generate("ctp"),
                title,
                description
        );

    }

}
