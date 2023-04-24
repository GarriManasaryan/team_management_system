package com.team.management.application;

import com.team.management.domain.sprint.ActualSprint;
import com.team.management.domain.sprint.ActualSprintRepository;
import com.team.management.ports.adapters.backoffice.model.sprint.ActualSprintBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.sprint.ActualSprintCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActualSprintService {

    private final ActualSprintRepository actualSprintRepository;

    public ActualSprintService(ActualSprintRepository actualSprintRepository) {
        this.actualSprintRepository = actualSprintRepository;
    }

    public void save(@NotNull ActualSprintCreationRequest actualSprintCreationRequest) {
        actualSprintRepository.save(ActualSprint.of(
                actualSprintCreationRequest.name(),
                actualSprintCreationRequest.planningId(),
                OffsetDateTime.parse(actualSprintCreationRequest.startAt())
        ));
    }

    public void delete(@NotNull String id) {
        actualSprintRepository.delete(id);
    }

    public void patch(@NotNull String id, @Nullable String name, @Nullable String planningId, @Nullable String startAt) {
        actualSprintRepository.patch(
                id,
                name,
                planningId,
                startAt != null ? OffsetDateTime.parse(startAt) : null
        );
    }

    public List<ActualSprintBackofficeModel> search(@Nullable String name, @Nullable String planningId, @Nullable String startAt) {
        return actualSprintRepository.search(
                        name,
                        planningId,
                        startAt != null ? OffsetDateTime.parse(startAt) : null)
                .stream()
                .map(actualSprint -> new ActualSprintBackofficeModel(
                        actualSprint.id(),
                        actualSprint.name(),
                        actualSprint.planningId(),
                        actualSprint.startAt()
                ))
                .toList();
    }

    public Optional<ActualSprintBackofficeModel> ofId(@NotNull String id) {
        return actualSprintRepository.ofId(id).stream()
                .map(actualSprint -> new ActualSprintBackofficeModel(
                        actualSprint.id(),
                        actualSprint.name(),
                        actualSprint.planningId(),
                        actualSprint.startAt()
                ))
                .findFirst();
    }
}
