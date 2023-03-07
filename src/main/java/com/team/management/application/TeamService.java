package com.team.management.application;

import com.team.management.domain.team.Team;
import com.team.management.domain.team.TeamRepository;
import com.team.management.ports.adapters.backoffice.model.team.TeamBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.team.TeamCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void save(@NotNull TeamCreationRequest teamCreationRequest) {
        teamRepository.save(Team.of(teamCreationRequest.name(), teamCreationRequest.managerId()));
    }

    public void delete(@NotNull String teamId) {
        teamRepository.delete(teamId);
    }

    public void patch(@NotNull String teamId, @Nullable String newManagerId, @Nullable String newName) {
        teamRepository.patch(teamId, newManagerId, newName);
    }

    public List<TeamBackofficeModel> search(@Nullable String name, @Nullable String managerId) {
        return teamRepository.search(name, managerId).stream()
                .map(team -> new TeamBackofficeModel(
                        team.id(),
                        team.name(),
                        team.managerId()
                ))
                .toList();
    }

    public Optional<TeamBackofficeModel> ofId(@NotNull String teamId) {
        return teamRepository.ofId(teamId)
                .map(team -> new TeamBackofficeModel(
                        teamId,
                        team.name(),
                        team.managerId()
                ));
    }
}
