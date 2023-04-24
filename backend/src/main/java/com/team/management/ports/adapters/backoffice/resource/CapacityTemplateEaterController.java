package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.CapacityTemplateEaterService;
import com.team.management.ports.adapters.backoffice.model.capacity.templateeaters.CapacityTemplateEaterBackofficeModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CapacityTemplateEaterController {

    private final CapacityTemplateEaterService capacityTemplateEaterService;

    public CapacityTemplateEaterController(CapacityTemplateEaterService capacityTemplateEaterService) {
        this.capacityTemplateEaterService = capacityTemplateEaterService;
    }

    @PostMapping("/api/capacity-template-eater/{capacityTemplateId}/{capacityEaterId}")
    public void saveTemplateEater(
            @PathVariable(name = "capacityTemplateId", required = true) String capacityTemplateId,
            @PathVariable(name = "capacityEaterId", required = true) String capacityEaterId
    ) {
        capacityTemplateEaterService.saveTemplateEater(capacityTemplateId, capacityEaterId);
    }

    @DeleteMapping("/api/capacity-template-eater/{capacityTemplateId}")
    public void removeAllEatersByTemplateId(
            @PathVariable(name = "capacityTemplateId", required = true) String capacityTemplateId
    ) {
        capacityTemplateEaterService.removeAllEatersByTemplateId(capacityTemplateId);
    }

    @DeleteMapping("/api/capacity-template-eater/{capacityTemplateId}/{capacityEaterId}")
    public void removeEater(
            @PathVariable(name = "capacityTemplateId", required = true) String capacityTemplateId,
            @PathVariable(name = "capacityEaterId", required = true) String capacityEaterId
    ) {
        capacityTemplateEaterService.removeEater(capacityTemplateId, capacityEaterId);
    }

    @GetMapping("/api/capacity-template-eater")
    public List<CapacityTemplateEaterBackofficeModel> search(
            @RequestParam(name = "capacityTemplateId", required = false) String capacityTemplateId,
            @RequestParam(name = "capacityEaterId", required = false) String capacityEaterId
    ) {
        return capacityTemplateEaterService.search(capacityTemplateId, capacityEaterId);
    }

    @GetMapping("/api/capacity-template-eater/{capacityTemplateId}/{capacityEaterId}")
    public Optional<CapacityTemplateEaterBackofficeModel> ofId(
            @PathVariable(name = "capacityTemplateId", required = true) String capacityTemplateId,
            @PathVariable(name = "capacityEaterId", required = true) String capacityEaterId
    ) {
        return capacityTemplateEaterService.ofId(capacityTemplateId, capacityEaterId);
    }
}
