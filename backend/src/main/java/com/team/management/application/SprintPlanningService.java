package com.team.management.application;

import com.team.management.domain.planning.SprintPlanning;
import com.team.management.domain.planning.SprintPlanningRepository;
import com.team.management.ports.adapters.backoffice.model.sprint.SprintPlanningBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.sprint.SprintPlanningCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintPlanningService {

    private final SprintPlanningRepository sprintPlanningRepository;

    public SprintPlanningService(SprintPlanningRepository sprintPlanningRepository) {
        this.sprintPlanningRepository = sprintPlanningRepository;
    }

    public void save(@NotNull SprintPlanningCreationRequest sprintPlanningCreationRequest) {
        sprintPlanningRepository.save(SprintPlanning.of(
                sprintPlanningCreationRequest.name(),
                sprintPlanningCreationRequest.timeUnitId(),
                sprintPlanningCreationRequest.capacityTemplateId(),
                sprintPlanningCreationRequest.maxCapacity()
        ));
    }

    public void delete(@NotNull String id) {
        sprintPlanningRepository.delete(id);
    }

    public void patch(@NotNull String id, @Nullable String name, @Nullable String timeUnitId, @Nullable String capacityTemplateId, @Nullable Integer maxCapacity) {
        sprintPlanningRepository.patch(id, name, timeUnitId, capacityTemplateId, maxCapacity);
    }

    public List<SprintPlanningBackofficeModel> search(@Nullable String name, @Nullable String timeUnitId, @Nullable String capacityTemplateId, @Nullable Integer maxCapacity) {
        return sprintPlanningRepository.search(name, timeUnitId, capacityTemplateId, maxCapacity).stream()
                .map(sprintPlanning -> new SprintPlanningBackofficeModel(
                        sprintPlanning.id(),
                        sprintPlanning.name(),
                        sprintPlanning.timeUnitId(),
                        sprintPlanning.capacityTemplateId(),
                        sprintPlanning.maxCapacity()
                ))
                .toList();
    }

    public Optional<SprintPlanningBackofficeModel> ofId(@NotNull String id) {
        return sprintPlanningRepository.ofId(id).stream()
                .map(sprintPlanning -> new SprintPlanningBackofficeModel(
                        sprintPlanning.id(),
                        sprintPlanning.name(),
                        sprintPlanning.timeUnitId(),
                        sprintPlanning.capacityTemplateId(),
                        sprintPlanning.maxCapacity()
                ))
                .findFirst();
    }
}
