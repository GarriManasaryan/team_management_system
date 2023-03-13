package com.team.management.domain.skill;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface SkillRepository {

    void save(@NotNull Skill skill);

    void rename(@NotNull String skillId, @NotNull String newName);

    void delete(@NotNull String skillId);

    List<Skill> search(@Nullable String name);

    Optional<Skill> ofId(@NotNull String skillId);

}
