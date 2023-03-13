package com.team.management.ports.adapters.backoffice.model.skill;

import org.jetbrains.annotations.NotNull;

public record SkillBackofficeModel(
        @NotNull String id,
        @NotNull String name
) {
}
