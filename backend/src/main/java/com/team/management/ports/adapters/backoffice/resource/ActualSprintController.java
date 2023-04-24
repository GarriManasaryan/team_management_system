package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.ActualSprintService;
import com.team.management.ports.adapters.backoffice.model.sprint.ActualSprintBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.sprint.ActualSprintCreationRequest;
import com.team.management.ports.adapters.backoffice.model.sprint.ActualSprintPatchRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ActualSprintController {

    private final ActualSprintService actualSprintService;

    public ActualSprintController(ActualSprintService actualSprintService) {
        this.actualSprintService = actualSprintService;
    }

    @PostMapping("/api/actual-sprint")
    public void save(@RequestBody ActualSprintCreationRequest actualSprintCreationRequest) {
        actualSprintService.save(actualSprintCreationRequest);
    }

    @DeleteMapping("/api/actual-sprint/{id}")
    public void delete(@PathVariable(name = "id", required = true) String id) {
        actualSprintService.delete(id);
    }

    @PatchMapping("/api/actual-sprint/{id}")
    public void patch(
            @PathVariable(name = "id", required = true) String id,
            @RequestBody ActualSprintPatchRequest actualSprintPatchRequest
    ) {
        actualSprintService.patch(
                id,
                actualSprintPatchRequest.name(),
                actualSprintPatchRequest.planningId(),
                actualSprintPatchRequest.startAt()
        );
    }

    @GetMapping("/api/actual-sprint")
    public List<ActualSprintBackofficeModel> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "planningId", required = false) String planningId,
            @RequestParam(name = "startAt", required = false) String startAt
    ) {
        return actualSprintService.search(name, planningId, startAt);
    }

    @GetMapping("/api/actual-sprint/{id}")
    public Optional<ActualSprintBackofficeModel> ofId(
            @PathVariable(name = "id", required = true) String id
    ) {
        return actualSprintService.ofId(id);
    }
}
