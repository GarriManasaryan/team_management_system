package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.SkillService;
import com.team.management.ports.adapters.backoffice.model.skill.SkillBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.skill.SkillCreationRequest;
import com.team.management.ports.adapters.backoffice.model.skill.SkillRenameRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/api/skills")
    public void save(@RequestBody @NotNull SkillCreationRequest skillCreationRequest) {
        skillService.save(skillCreationRequest);
    }

    @PatchMapping("/api/skills/{skillId}")
    public void rename(
            @PathVariable(name = "skillId") @NotNull String skillId,
            @RequestBody @NotNull SkillRenameRequest skillRenameRequest
    ) {
        skillService.rename(skillId, skillRenameRequest.name());
    }

    @DeleteMapping("/api/skills/{skillId}")
    public void delete(@PathVariable(name = "skillId") @NotNull String skillId) {
        skillService.delete(skillId);
    }

    @GetMapping("/api/skills")
    public List<SkillBackofficeModel> search(@RequestParam(name = "name", required = false) String name) {
        return skillService.search(name);
    }

    @GetMapping("/api/skills/{skillId}")
    public Optional<SkillBackofficeModel> ofId(@PathVariable(name = "skillId") @NotNull String skillId) {
        return skillService.ofId(skillId);
    }
}
