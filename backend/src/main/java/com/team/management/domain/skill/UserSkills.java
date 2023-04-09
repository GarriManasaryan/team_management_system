package com.team.management.domain.skill;

import org.jetbrains.annotations.NotNull;

public record UserSkills(
        @NotNull String userId,
        @NotNull String skillId
) {

}
