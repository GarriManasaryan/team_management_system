package com.team.management.domain.capacity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface CapacityTemplateRepository {

    void save(@NotNull CapacityTemplate capacityTemplate);

    void delete(@NotNull String id);

    void patch(@NotNull String id, @Nullable String title, @Nullable String description);

    List<CapacityTemplate> search(@Nullable String title, @Nullable String description);

    Optional<CapacityTemplate> ofId(@NotNull String id);

}
