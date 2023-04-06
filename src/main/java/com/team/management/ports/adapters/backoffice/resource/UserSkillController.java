package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.UserResponsibilityService;
import com.team.management.application.UserSkillService;
import com.team.management.ports.adapters.backoffice.model.responsibility.UserResponsibilityBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.skill.UserSkillBackofficeModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserSkillController {

    private final UserSkillService userSkillService;

    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    @GetMapping("/api/user-skill")
    public List<UserSkillBackofficeModel> search(
            @RequestParam(name = "userId", required = false) String userId,
            @RequestParam(name = "skillId", required = false) String skillId
    ) {
        return userSkillService.search(userId, skillId);
    }

    @PatchMapping("/api/user-skill/{userId}/{skillId}")
    public void addUserSkill(
            @PathVariable(name = "userId", required = true) String userId,
            @PathVariable(name = "skillId", required = true) String skillId
    ) {
        userSkillService.addUserSkill(userId, skillId);
    }

    @DeleteMapping("/api/user-skill/{userId}/{skillId}")
    public void removeUserResponsibility(
            @PathVariable(name = "userId", required = true) String userId,
            @PathVariable(name = "skillId", required = true) String skillId
    ) {
        userSkillService.removeUserSkill(userId, skillId);
    }

    @DeleteMapping("/api/user-skill/{userId}")
    public void deleteAllUserResponsibility(@PathVariable(name = "userId", required = true) String userId) {
        userSkillService.deleteAllUserSkill(userId);
    }
}
