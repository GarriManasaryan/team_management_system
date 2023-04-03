package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.ResponsibilityService;
import com.team.management.domain.responsibility.Responsibility;
import com.team.management.ports.adapters.backoffice.model.responsibility.ResponsibilityBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.responsibility.ResponsibilityCreationRequest;
import com.team.management.ports.adapters.backoffice.model.responsibility.ResponsibilityPatchRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ResponsibilityController {

    private final ResponsibilityService responsibilityService;

    public ResponsibilityController(ResponsibilityService responsibilityService) {
        this.responsibilityService = responsibilityService;
    }

    @PostMapping("/api/responsibility")
    public void save(@RequestBody(required = true) ResponsibilityCreationRequest responsibilityCreationRequest) {
        responsibilityService.save(responsibilityCreationRequest);
    }

    @DeleteMapping("/api/responsibility/{respId}")
    public void delete(@PathVariable(name = "respId", required = true) @NotNull String respId) {
        responsibilityService.delete(respId);
    }

    @PatchMapping("/api/responsibility/{respId}")
    public void patch(
            @PathVariable(name = "respId", required = true) @NotNull String respId,
            @RequestBody(required = true) ResponsibilityPatchRequest responsibilityPatchRequest
    ) {
        responsibilityService.patch(respId, responsibilityPatchRequest);
    }

    @GetMapping("/api/responsibility")
    public List<ResponsibilityBackofficeModel> search(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "outerId", required = false) String outerId,
            @RequestParam(name = "label", required = false) String label
    ) {
        return responsibilityService.search(name, outerId, label);
    }

    @GetMapping("/api/responsibility/{respId}")
    public Optional<Responsibility> ofId(@PathVariable(name = "respId", required = true) String respId) {
        return responsibilityService.ofId(respId);
    }
}
