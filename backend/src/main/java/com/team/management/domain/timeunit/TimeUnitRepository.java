package com.team.management.domain.timeunit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface TimeUnitRepository {

    void save(@NotNull TimeUnit timeUnit);

    void delete(@NotNull String id);

    void patch(@NotNull String id, @Nullable String name, @Nullable Integer duration);

    List<TimeUnit> search(@Nullable String name, @Nullable Integer duration);

    Optional<TimeUnit> ofId(@NotNull String id);

}
