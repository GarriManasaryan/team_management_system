package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.TeamService;
import com.team.management.ports.adapters.backoffice.model.team.TeamBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.team.TeamCreationRequest;
import com.team.management.ports.adapters.backoffice.model.team.TeamUpdateRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/api/teams")
    public void save(@RequestBody TeamCreationRequest teamCreationRequest) {
        teamService.save(teamCreationRequest);
    }

    @DeleteMapping("/api/teams/{team_id}")
    public void delete(@PathVariable(name = "team_id") @NotNull String teamId) {
        teamService.delete(teamId);
    }

    @PatchMapping("/api/teams/{team_id}")
    public void patch(
            @PathVariable(name = "team_id") @NotNull String teamId,
            @RequestBody @NotNull TeamUpdateRequest teamUpdateRequest
            ) {

        teamService.patch(teamId, teamUpdateRequest.managerId(), teamUpdateRequest.name());
    }

    @GetMapping("/api/teams")
    public List<TeamBackofficeModel> search(
            @RequestParam(name = "name" , required = false) @Nullable String name,
            @RequestParam(name = "managerId" , required = false) @Nullable String managerId
    ) {
        return teamService.search(name, managerId);
    }

    @GetMapping("/api/teams/{team_id}")
    public Optional<TeamBackofficeModel> ofId(
            @PathVariable(name = "team_id") @NotNull String teamId
    ) {
        return teamService.ofId(teamId);
    }
}