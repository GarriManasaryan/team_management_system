package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.TeamService;
import com.team.management.application.UserService;
import com.team.management.domain.team.Team;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.team.management.ports.adapters.backoffice.model.team.TeamBackofficeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class TeamControllerTest {

    @MockBean
    TeamService teamService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        // given
        var expectedUsers = List.of(teamGen(), teamGen(), teamGen(), teamGen());
        given(teamService.search(null, null)).willReturn(expectedUsers);

        // when and then
        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedUsers.size()))
                .andExpect(jsonPath("$.[0].id").value(expectedUsers.get(0).id()))
                .andExpect(jsonPath("$.[0].name").value(expectedUsers.get(0).name()));
    }

    private TeamBackofficeModel teamGen() {
        return new TeamBackofficeModel(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void patch() {
        // given

        verify(teamService, only()).patch("ac", "cas", null); // только один раз - это уже проверка, как assert
        verify(teamService, never()).patch("ac", "cas", null); // никогда не выз с такими параметрами - это уже проверка, как assert

    }

    @Test
    void search() {
    }

    @Test
    void ofId() {
    }
}