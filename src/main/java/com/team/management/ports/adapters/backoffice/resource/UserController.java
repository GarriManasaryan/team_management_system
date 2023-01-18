package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.UserService;
import com.team.management.ports.adapters.backoffice.model.UserBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.UserCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public String save(@RequestBody UserCreationRequest userCreationRequest) {
        return userService.save(userCreationRequest);
    }

    @GetMapping("/api/users")
    public @NotNull List<UserBackofficeModel> all() {
        return userService.all();
    }
}
