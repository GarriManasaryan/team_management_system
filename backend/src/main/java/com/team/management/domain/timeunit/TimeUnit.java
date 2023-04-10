package com.team.management.domain.timeunit;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

public record TimeUnit(
        @NotNull String id,
        @NotNull String name,
        @NotNull Integer duration
) {

    public static TimeUnit of(
            @NotNull String name,
            @NotNull Integer duration
    ) {
        return new TimeUnit(IdGenerator.generate("tmu"), name, duration);
    }

}
