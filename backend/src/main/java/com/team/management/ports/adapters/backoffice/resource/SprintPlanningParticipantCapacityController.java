package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.SprintPlanningParticipantCapacityService;
import com.team.management.ports.adapters.backoffice.model.sprint.SprintPlanningParticipantCapacityBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.sprint.SprintPlanningParticipantCapacityCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SprintPlanningParticipantCapacityController {

    private final SprintPlanningParticipantCapacityService sprintPlanningParticipantCapacityService;

    public SprintPlanningParticipantCapacityController(SprintPlanningParticipantCapacityService sprintPlanningParticipantCapacityService) {
        this.sprintPlanningParticipantCapacityService = sprintPlanningParticipantCapacityService;
    }

    @PostMapping("/api/sprint-planning/participant-capacity")
    public void save(@RequestBody SprintPlanningParticipantCapacityCreationRequest sprintPlanningParticipantCapacityCreationRequest) {
        sprintPlanningParticipantCapacityService.save(
                sprintPlanningParticipantCapacityCreationRequest.userId(),
                sprintPlanningParticipantCapacityCreationRequest.planningId(),
                sprintPlanningParticipantCapacityCreationRequest.capacityEaterId(),
                sprintPlanningParticipantCapacityCreationRequest.duration()
        );
    }

    @DeleteMapping("/api/sprint-planning/participant-capacity/{userId}/{planningId}/{capacityEaterId}")
    public void delete(
            @PathVariable(name = "userId", required = true) String userId,
            @PathVariable(name = "planningId", required = true) String planningId,
            @PathVariable(name = "capacityEaterId", required = true) String capacityEaterId
    ) {
        sprintPlanningParticipantCapacityService.delete(userId, planningId, capacityEaterId);
    }

    @GetMapping("/api/sprint-planning/participant-capacity")
    public List<SprintPlanningParticipantCapacityBackofficeModel> all() {
        return sprintPlanningParticipantCapacityService.all();
    }

    @GetMapping("/api/sprint-planning/participant-capacity/{userId}/{planningId}/{capacityEaterId}")
    public Optional<SprintPlanningParticipantCapacityBackofficeModel> ofId(
            @PathVariable(name = "userId", required = true) String userId,
            @PathVariable(name = "planningId", required = true) String planningId,
            @PathVariable(name = "capacityEaterId", required = true) String capacityEaterId
    ) {
        return sprintPlanningParticipantCapacityService.ofId(userId, planningId, capacityEaterId);
    }
}
