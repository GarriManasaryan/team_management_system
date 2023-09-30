package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.UserService;
import com.team.management.ports.adapters.backoffice.model.user.UserBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.user.UserCreationRequest;
import com.team.management.ports.adapters.backoffice.model.user.UserNewEntityBackOfficeModel;
import com.team.management.ports.adapters.backoffice.model.user.UserPatchBackOfficeModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/api/users/{user_id}")
    public void delete(@PathVariable(name = "user_id") String userId) {
        userService.delete(userId);
    }

    @PostMapping("/api/users")
    public UserNewEntityBackOfficeModel save(@RequestBody UserCreationRequest userCreationRequest) {
        return userService.saveUser(userCreationRequest);
    }

    @PatchMapping("/api/users/{user_id}")
    public void replace(
            @PathVariable("user_id") String userId,
            @RequestBody UserPatchBackOfficeModel userPatchBackOfficeModel
    ) {
        userService.patch(
                userId,
                userPatchBackOfficeModel.role(),
                userPatchBackOfficeModel.email(),
                userPatchBackOfficeModel.name()
        );
    }

    @GetMapping("/api/users/{user_id}")
    public @NotNull Optional<UserBackofficeModel> ofId(
            @PathVariable(name = "user_id") String id
    ) {
        return userService.ofId(id);
    }

    @GetMapping("/api/users")
    public @NotNull List<UserBackofficeModel> search(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "name", required = false) String name
    ) {
        return userService.search(role, email, name);
    }
}
