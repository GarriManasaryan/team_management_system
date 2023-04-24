package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.CapacityEaterService;
import com.team.management.ports.adapters.backoffice.model.capacity.eater.CapacityEaterBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.capacity.eater.CapacityEaterCreationRequest;
import com.team.management.ports.adapters.backoffice.model.capacity.eater.CapacityEaterPatchRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CapacityEaterController {

    private final CapacityEaterService capacityEaterService;

    public CapacityEaterController(CapacityEaterService capacityEaterService) {
        this.capacityEaterService = capacityEaterService;
    }

    @PostMapping("/api/capacity-eater")
    public void save(@RequestBody CapacityEaterCreationRequest capacityEaterCreationRequest) {
        capacityEaterService.save(capacityEaterCreationRequest);
    }

    @DeleteMapping("/api/capacity-eater/{id}")
    public void delete(@PathVariable(name = "id", required = true) String id) {
        capacityEaterService.delete(id);
    }

    @PatchMapping("/api/capacity-eater/{id}")
    public void patch(
            @PathVariable(name = "id", required = true) String id,
            @RequestBody CapacityEaterPatchRequest capacityEaterPatchRequest
    ) {
        capacityEaterService.patch(
                id,
                capacityEaterPatchRequest.name(),
                capacityEaterPatchRequest.duration(),
                capacityEaterPatchRequest.typeId(),
                capacityEaterPatchRequest.priority(),
                capacityEaterPatchRequest.deadline(),
                capacityEaterPatchRequest.status()
        );
    }

    @GetMapping("/api/capacity-eater")
    public List<CapacityEaterBackofficeModel> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "duration", required = false) Integer duration,
            @RequestParam(name = "typeId", required = false) String typeId,
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "deadline", required = false) String deadline,
            @RequestParam(name = "status", required = false) String status
    ) {
        return capacityEaterService.search(
                name,
                duration,
                typeId,
                priority,
                deadline,
                status
        );
    }

    @GetMapping("/api/capacity-eater/{id}")
    public Optional<CapacityEaterBackofficeModel> ofId(@PathVariable(name = "id", required = true) String id) {
        return capacityEaterService.ofId(id);
    }
}
