package com.team.management.domain.responsibility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface ResponsibilityRepository {

    void save(@NotNull Responsibility responsibility);

    void delete(@NotNull String respId);

    void patch(
            @NotNull String respId,
            @Nullable String newName,
            @Nullable String newOuterId,
            @Nullable String newLabel
    );

    List<Responsibility> search(
            @Nullable String name,
            @Nullable String outerId,
            @Nullable String label
    );

    Optional<Responsibility> ofId(@NotNull String respId);


}
