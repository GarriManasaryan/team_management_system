package com.team.management.domain.skill;

import com.team.management.application.IdGenerator;
import org.jetbrains.annotations.NotNull;

public record Skill(
        @NotNull String id,
        @NotNull String name
) {

    public static Skill of(
            @NotNull String name
    ){
        return new Skill(IdGenerator.generate("skill"), name);
    }
}
