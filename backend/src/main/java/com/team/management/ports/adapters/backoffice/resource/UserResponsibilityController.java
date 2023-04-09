package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.UserResponsibilityService;
import com.team.management.ports.adapters.backoffice.model.responsibility.UserResponsibilityBackofficeModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserResponsibilityController {

    private final UserResponsibilityService userResponsibilityService;

    public UserResponsibilityController(UserResponsibilityService userResponsibilityService) {
        this.userResponsibilityService = userResponsibilityService;
    }

    @GetMapping("/api/user-responsibility")
    public List<UserResponsibilityBackofficeModel> search(
            @RequestParam(name = "userId", required = false) String userId,
            @RequestParam(name = "respId", required = false) String respId
    ) {
        return userResponsibilityService.search(userId, respId);
    }

    @PatchMapping("/api/user-responsibility/{userId}/{respId}")
    public void addUserResponsibility(
            @PathVariable(name = "userId", required = true) String userId,
            @PathVariable(name = "respId", required = true) String respId
    ) {
        userResponsibilityService.addUserResponsibility(userId, respId);
    }

    @DeleteMapping("/api/user-responsibility/{userId}/{respId}")
    public void removeUserResponsibility(
            @PathVariable(name = "userId", required = true) String userId,
            @PathVariable(name = "respId", required = true) String respId
    ) {
        userResponsibilityService.removeUserResponsibility(userId, respId);
    }

    @DeleteMapping("/api/user-responsibility/{userId}")
    public void deleteAllUserResponsibility(@PathVariable(name = "userId", required = true) String userId) {
        userResponsibilityService.deleteAllUserResponsibility(userId);
    }
}
