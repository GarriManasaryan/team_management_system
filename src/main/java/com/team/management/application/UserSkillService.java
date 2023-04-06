package com.team.management.application;

import com.team.management.domain.responsibility.UserResponsibilityRepository;
import com.team.management.domain.skill.UserSkillRepository;
import com.team.management.ports.adapters.backoffice.model.responsibility.UserResponsibilityBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.skill.UserSkillBackofficeModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillService {
    private final UserSkillRepository userSkillRepository;

    public UserSkillService(UserSkillRepository userSkillRepository) {
        this.userSkillRepository = userSkillRepository;
    }

    public void addUserSkill(@NotNull String userId, @NotNull String skillId) {
        userSkillRepository.addUserSkill(userId, skillId);
    }

    public void removeUserSkill(@NotNull String userId, @NotNull String skillId) {
        userSkillRepository.removeUserSkill(userId, skillId);
    }

    public void deleteAllUserSkill(@NotNull String userId) {
        userSkillRepository.deleteAllUserSkill(userId);
    }

    public List<UserSkillBackofficeModel> search(@Nullable String userId, @Nullable String skillId) {
        return userSkillRepository.search(userId, skillId).stream()
                .map(userResponsibility -> new UserSkillBackofficeModel(
                        userResponsibility.userId(), userResponsibility.skillId()))
                .toList();
    }
}
