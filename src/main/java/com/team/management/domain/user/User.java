package com.team.management.domain.user;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

public record User(
        @NotNull String id,
        @NotNull String name,
        @NotNull String email,
        @NotNull Role role
) {

    public static User of(
            @NotNull String name,
            @NotNull String email,
            @NotNull Role role
    ) {
        return new User(
                IdGenerator.generate("user"),
                name,
                email,
                role
        );

    }
}
