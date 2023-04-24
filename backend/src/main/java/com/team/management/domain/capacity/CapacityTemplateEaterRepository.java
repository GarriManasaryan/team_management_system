package com.team.management.domain.capacity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface CapacityTemplateEaterRepository {

    void saveTemplateEater(@NotNull String capacityTemplateId, @NotNull String capacityEaterId);

    void removeAllEatersByTemplateId(@NotNull String capacityTemplateId);

    void removeEater(@NotNull String capacityTemplateId, @NotNull String capacityEaterId);

    List<CapacityTemplateEater> search(@Nullable String capacityTemplateId, @Nullable String capacityEaterId);

    Optional<CapacityTemplateEater> ofId(@NotNull String capacityTemplateId, @NotNull String capacityEaterId);

}
