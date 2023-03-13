package com.team.management.application;

import com.team.management.domain.team.Team;
import com.team.management.domain.team.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        teamService = new TeamService(teamRepository);
    }

    @Test
    void shouldSaveNewTeam() {

        // given
        var testTeam = Team.of("name", "manager");

        teamRepository.save(testTeam);

        // when
        ArgumentCaptor<Team> argumentCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepository).save(argumentCaptor.capture());

        var capturedArgument = argumentCaptor.getValue();

        // then
        assertThat(capturedArgument.name()).isEqualTo(testTeam.name());

    }

    @Test
    void delete() {

        // given
        var testTeam = Team.of("name", "manager");
        teamRepository.delete(testTeam.id());

        // when
        var argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(teamRepository).delete(argumentCaptor.capture());

        var capturedArgument = argumentCaptor.getValue();

        // then
        assertThat(capturedArgument).isEqualTo(testTeam.id());

    }

    // patch
    @Test
    void patch() {
        // given
        var testTeam = Team.of("name", "some_manager");

    }

    // search
    @Test
    void shouldReturnTeamByName() {
        var name = "Gastro";
        var testTeam = Team.of(name, "some_manager");

        given(teamRepository.search(eq(name), any())).willReturn(List.of(testTeam));

        // when
        var foundTeam = teamService.search(name, null);

        // then
        assertThat(foundTeam).hasSize(1);
        assertThat(foundTeam.get(0).name()).isEqualTo(name);
        assertThat(foundTeam.get(0).managerId()).isEqualTo(testTeam.managerId());

    }

    // search
    @Test
    void shouldReturnTeamByManagerId() {
        var managerId = "some_manager";
        var testTeam = Team.of("name", managerId);

        given(teamRepository.search(any(), eq(managerId))).willReturn(List.of(testTeam));

        // when
        var foundTeam = teamService.search(null, managerId);

        // then
        assertThat(foundTeam).hasSize(1);
        assertThat(foundTeam.get(0).managerId()).isEqualTo(managerId);
        assertThat(foundTeam.get(0).name()).isEqualTo(testTeam.name());

    }

    // search
    @Test
    void shouldReturnAllTeamsIfNoParams() {
        var testTeam1 = Team.of("name", "manager");
        var testTeam2 = Team.of("name1", "manager1");

        given(teamRepository.search(any(), any())).willReturn(List.of(testTeam1, testTeam2));

        // when
        var foundTeams = teamService.search(null, null);

        // then
        assertThat(foundTeams).hasSize(2);
        assertThat(foundTeams.get(1).name()).isEqualTo(testTeam2.name());

    }

    @Test
    void ofId() {
    }
}