package com.team.management.domain.skill;

import com.team.management.domain.responsibility.UserResponsibility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UserSkillRepository {

    void addUserSkill(@NotNull String userId, @NotNull String skillId);

    void removeUserSkill(@NotNull String userId, @NotNull String skillId);

    void deleteAllUserSkill(@NotNull String userId);

    List<UserSkills> search(@Nullable String userId, @Nullable String skillId);
}
