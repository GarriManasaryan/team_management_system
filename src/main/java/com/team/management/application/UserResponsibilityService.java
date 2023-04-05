package com.team.management.application;

import com.team.management.domain.responsibility.UserResponsibility;
import com.team.management.domain.responsibility.UserResponsibilityRepository;
import com.team.management.ports.adapters.backoffice.model.responsibility.UserResponsibilityBackofficeModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserResponsibilityService {
    private final UserResponsibilityRepository userResponsibilityRepository;

    public UserResponsibilityService(UserResponsibilityRepository userResponsibilityRepository) {
        this.userResponsibilityRepository = userResponsibilityRepository;
    }

    public void addUserResponsibility(@NotNull String userId, @NotNull String respId) {
        userResponsibilityRepository.addUserResponsibility(userId, respId);
    }

    public void removeUserResponsibility(@NotNull String userId, @NotNull String respId) {
        userResponsibilityRepository.removeUserResponsibility(userId, respId);
    }

    public void deleteAllUserResponsibility(@NotNull String userId) {
        userResponsibilityRepository.deleteAllUserResponsibility(userId);
    }

    public List<UserResponsibilityBackofficeModel> search(@Nullable String userId, @Nullable String respId) {
        return userResponsibilityRepository.search(userId, respId).stream()
                .map(userResponsibility -> new UserResponsibilityBackofficeModel(
                        userResponsibility.userId(), userResponsibility.respId()))
                .toList();
    }
}
