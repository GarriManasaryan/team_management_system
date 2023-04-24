package com.team.management.application;

import com.team.management.domain.planning.SprintPlanningParticipantCapacity;
import com.team.management.domain.planning.SprintPlanningParticipantCapacityRepository;
import com.team.management.ports.adapters.backoffice.model.sprint.SprintPlanningParticipantCapacityBackofficeModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintPlanningParticipantCapacityService {

    private final SprintPlanningParticipantCapacityRepository sprintPlanningParticipantCapacityRepository;

    public SprintPlanningParticipantCapacityService(SprintPlanningParticipantCapacityRepository sprintPlanningParticipantCapacityRepository) {
        this.sprintPlanningParticipantCapacityRepository = sprintPlanningParticipantCapacityRepository;
    }

    public void save(@NotNull String userId, @NotNull String planningId, @NotNull String capacityEaterId, @NotNull Integer duration) {
        sprintPlanningParticipantCapacityRepository.save(userId, planningId, capacityEaterId, duration);
    }

    public void delete(@NotNull String userId, @NotNull String planningId, @NotNull String capacityEaterId) {
        sprintPlanningParticipantCapacityRepository.delete(userId, planningId, capacityEaterId);
    }

    public List<SprintPlanningParticipantCapacityBackofficeModel> all() {
        return sprintPlanningParticipantCapacityRepository.all().stream()
                .map(sprintPlanningParticipantCapacity -> new SprintPlanningParticipantCapacityBackofficeModel(
                        sprintPlanningParticipantCapacity.userId(),
                        sprintPlanningParticipantCapacity.planningId(),
                        sprintPlanningParticipantCapacity.capacityEaterId(),
                        sprintPlanningParticipantCapacity.duration()
                ))
                .toList();
    }

    public Optional<SprintPlanningParticipantCapacityBackofficeModel> ofId(@NotNull String userId, @NotNull String planningId, @NotNull String capacityEaterId) {
        return sprintPlanningParticipantCapacityRepository.ofId(userId, planningId, capacityEaterId).stream()
                .map(sprintPlanningParticipantCapacity -> new SprintPlanningParticipantCapacityBackofficeModel(
                        sprintPlanningParticipantCapacity.userId(),
                        sprintPlanningParticipantCapacity.planningId(),
                        sprintPlanningParticipantCapacity.capacityEaterId(),
                        sprintPlanningParticipantCapacity.duration()
                ))
                .findFirst();
    }
}
