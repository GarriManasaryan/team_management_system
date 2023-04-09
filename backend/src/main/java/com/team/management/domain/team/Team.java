package com.team.management.domain.team;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

public record Team(
        @NotNull String id,
        @NotNull String name,
        @NotNull String managerId
) {
    public static Team of(
            @NotNull String name,
            @NotNull String managerId
    ){
        return new Team(
                IdGenerator.generate("team"),
                name,
                managerId
        );
    }
}
