package com.team.management.ports.adapters.backoffice.model.skill;

import org.jetbrains.annotations.Nullable;

public record UserSkillBackofficeModel(
        @Nullable String userId,
        @Nullable String skillId
) {
}
