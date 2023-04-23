package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.CapacityTypeService;
import com.team.management.ports.adapters.backoffice.model.capacity.CapacityTypeBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.capacity.CapacityTypeCreationRequest;
import com.team.management.ports.adapters.backoffice.model.capacity.CapacityTypePatchRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CapacityTypeController {

    private final CapacityTypeService capacityTypeService;

    public CapacityTypeController(CapacityTypeService capacityTypeService) {
        this.capacityTypeService = capacityTypeService;
    }

    @PostMapping("/api/capacity-type")
    public void save(@RequestBody CapacityTypeCreationRequest capacityTypeCreationRequest) {
        capacityTypeService.save(capacityTypeCreationRequest);
    }

    @DeleteMapping("/api/capacity-type/{id}")
    public void delete(@PathVariable(name = "id", required = true) String id) {
        capacityTypeService.delete(id);
    }

    @PatchMapping("/api/capacity-type/{id}")
    public void patch(
            @PathVariable(name = "id", required = true) String id,
            @RequestBody @NotNull CapacityTypePatchRequest capacityTypePatchRequest
    ) {
        capacityTypeService.patch(id, capacityTypePatchRequest.name(), capacityTypePatchRequest.defaultDuration());
    }

    @GetMapping("/api/capacity-type")
    public List<CapacityTypeBackofficeModel> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "defaultDuration", required = false) Integer defaultDuration
    ) {
        return capacityTypeService.search(name, defaultDuration);
    }

    @GetMapping("/api/capacity-type/{id}")
    public Optional<CapacityTypeBackofficeModel> ofId(@PathVariable(name = "id", required = true) String id) {
        return capacityTypeService.ofId(id);
    }
}
