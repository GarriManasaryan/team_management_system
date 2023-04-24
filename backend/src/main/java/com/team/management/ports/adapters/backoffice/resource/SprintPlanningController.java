package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.SprintPlanningService;
import com.team.management.ports.adapters.backoffice.model.sprint.SprintPlanningBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.sprint.SprintPlanningCreationRequest;
import com.team.management.ports.adapters.backoffice.model.sprint.SprintPlanningPatchRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SprintPlanningController {

    private final SprintPlanningService sprintPlanningService;

    public SprintPlanningController(SprintPlanningService sprintPlanningService) {
        this.sprintPlanningService = sprintPlanningService;
    }

    @PostMapping("/api/sprint-planning")
    public void save(@RequestBody SprintPlanningCreationRequest sprintPlanningCreationRequest) {
        sprintPlanningService.save(sprintPlanningCreationRequest);
    }

    @DeleteMapping("/api/sprint-planning/{id}")
    public void delete(@PathVariable(name = "id", required = true) String id) {
        sprintPlanningService.delete(id);
    }

    @PatchMapping("/api/sprint-planning/{id}")
    public void patch(
            @PathVariable(name = "id", required = true) String id,
            @RequestBody SprintPlanningPatchRequest sprintPlanningPatchRequest
    ) {
        sprintPlanningService.patch(
                id,
                sprintPlanningPatchRequest.name(),
                sprintPlanningPatchRequest.timeUnitId(),
                sprintPlanningPatchRequest.capacityTemplateId(),
                sprintPlanningPatchRequest.maxCapacity()
        );
    }

    @GetMapping("/api/sprint-planning")
    public List<SprintPlanningBackofficeModel> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "timeUnitId", required = false) String timeUnitId,
            @RequestParam(name = "capacityTemplateId", required = false) String capacityTemplateId,
            @RequestParam(name = "maxCapacity", required = false) Integer maxCapacity
    ) {
        return sprintPlanningService.search(name, timeUnitId, capacityTemplateId, maxCapacity);
    }

    @GetMapping("/api/sprint-planning/{id}")
    public Optional<SprintPlanningBackofficeModel> ofId(
            @PathVariable(name = "id", required = true) String id
    ) {
        return sprintPlanningService.ofId(id);
    }
}
