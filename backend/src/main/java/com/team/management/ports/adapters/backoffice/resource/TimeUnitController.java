package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.TimeUnitService;
import com.team.management.ports.adapters.backoffice.model.timeunit.TimeUnitBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.timeunit.TimeUnitCreationRequest;
import com.team.management.ports.adapters.backoffice.model.timeunit.TimeUnitPatchRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TimeUnitController {

    private final TimeUnitService timeUnitService;

    public TimeUnitController(TimeUnitService timeUnitService) {
        this.timeUnitService = timeUnitService;
    }

    @PostMapping("/api/time-unit")
    public void save(@RequestBody TimeUnitCreationRequest timeUnitCreationRequest) {
        timeUnitService.save(timeUnitCreationRequest);
    }

    @DeleteMapping("/api/time-unit/{id}")
    public void delete(@PathVariable(name = "id", required = true) String id) {
        timeUnitService.delete(id);
    }

    @PatchMapping("/api/time-unit/{id}")
    public void patch(
            @PathVariable(name = "id", required = true) String id,
            @RequestBody TimeUnitPatchRequest timeUnitPatchRequest
    ) {
        timeUnitService.patch(id, timeUnitPatchRequest.name(), timeUnitPatchRequest.duration());
    }

    @GetMapping("/api/time-unit")
    public List<TimeUnitBackofficeModel> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "duration", required = false) Integer duration
    ) {
        return timeUnitService.search(name, duration);
    }

    @GetMapping("/api/time-unit/{id}")
    public Optional<TimeUnitBackofficeModel> ofId(@PathVariable(name = "id", required = true) String id) {
        return timeUnitService.ofId(id);
    }
}
