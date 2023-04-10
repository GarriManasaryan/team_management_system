package com.team.management.application;

import com.team.management.domain.timeunit.TimeUnit;
import com.team.management.domain.timeunit.TimeUnitRepository;
import com.team.management.ports.adapters.backoffice.model.timeunit.TimeUnitBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.timeunit.TimeUnitCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeUnitService {

    private final TimeUnitRepository timeUnitRepository;

    public TimeUnitService(TimeUnitRepository timeUnitRepository) {
        this.timeUnitRepository = timeUnitRepository;
    }

    public void save(@NotNull TimeUnitCreationRequest timeUnitCreationRequest) {
        timeUnitRepository.save(TimeUnit.of(timeUnitCreationRequest.name(), timeUnitCreationRequest.duration()));
    }

    public void delete(@NotNull String id) {
        timeUnitRepository.delete(id);
    }

    public void patch(@NotNull String id, @Nullable String name, @Nullable Integer duration) {
        timeUnitRepository.patch(id, name, duration);
    }

    public List<TimeUnitBackofficeModel> search(@Nullable String name, @Nullable Integer duration) {
        return timeUnitRepository.search(name, duration).stream()
                .map(timeUnit -> new TimeUnitBackofficeModel(
                        timeUnit.id(),
                        timeUnit.name(),
                        timeUnit.duration()
                ))
                .toList();
    }

    public Optional<TimeUnitBackofficeModel> ofId(@NotNull String id) {
        return timeUnitRepository.ofId(id)
                .map(timeUnit -> new TimeUnitBackofficeModel(
                        timeUnit.id(),
                        timeUnit.name(),
                        timeUnit.duration()
                ));
    }
}
