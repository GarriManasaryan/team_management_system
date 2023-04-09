package com.team.management.domain.responsibility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UserResponsibilityRepository {

    void addUserResponsibility(@NotNull String userId, @NotNull String respId);

    void removeUserResponsibility(@NotNull String userId, @NotNull String respId);

    void deleteAllUserResponsibility(@NotNull String userId);

    List<UserResponsibility> search(@Nullable String userId, @Nullable String respId);
}
