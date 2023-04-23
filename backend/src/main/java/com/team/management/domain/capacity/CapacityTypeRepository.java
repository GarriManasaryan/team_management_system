package com.team.management.domain.capacity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface CapacityTypeRepository {

    void save(@NotNull CapacityType capacityType);

    void delete(@NotNull String id);

    void patch(@NotNull String id, @Nullable String name, @Nullable Integer defaultDuration);

    List<CapacityType> search(@Nullable String name, @Nullable Integer defaultDuration);

    Optional<CapacityType> ofId(@NotNull String id);

}
