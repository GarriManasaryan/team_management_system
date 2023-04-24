package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.CapacityTemplateService;
import com.team.management.ports.adapters.backoffice.model.capacity.template.CapacityTemplateBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.capacity.template.CapacityTemplateCreationRequest;
import com.team.management.ports.adapters.backoffice.model.capacity.template.CapacityTemplatePatchRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CapacityTemplateController {

    private final CapacityTemplateService capacityTemplateService;

    public CapacityTemplateController(CapacityTemplateService capacityTemplateService) {
        this.capacityTemplateService = capacityTemplateService;
    }

    @PostMapping("/api/capacity-template")
    public void save(@RequestBody CapacityTemplateCreationRequest capacityTemplateCreationRequest) {
        capacityTemplateService.save(capacityTemplateCreationRequest);
    }

    @DeleteMapping("/api/capacity-template/{id}")
    public void delete(@PathVariable(name = "id", required = true) String id) {
        capacityTemplateService.delete(id);
    }

    @PatchMapping("/api/capacity-template/{id}")
    public void patch(
            @PathVariable(name = "id", required = true) String id,
            @RequestBody CapacityTemplatePatchRequest capacityTemplatePatchRequest
    ) {
        capacityTemplateService.patch(id, capacityTemplatePatchRequest.title(), capacityTemplatePatchRequest.description());
    }

    @GetMapping("/api/capacity-template")
    public List<CapacityTemplateBackofficeModel> search(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "description", required = false) String description
    ) {
        return capacityTemplateService.search(title, description);
    }

    @GetMapping("/api/capacity-template/{id}")
    public Optional<CapacityTemplateBackofficeModel> ofId(@PathVariable(name = "id", required = true) String id) {
        return capacityTemplateService.ofId(id);
    }
}
