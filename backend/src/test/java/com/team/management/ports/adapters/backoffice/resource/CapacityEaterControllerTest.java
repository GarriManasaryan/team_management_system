package com.team.management.ports.adapters.backoffice.resource;

import com.team.management.application.CapacityEaterService;
import com.team.management.domain.capacity.CapacityEater;
import com.team.management.domain.capacity.CapacityEaterStatus;
import com.team.management.domain.capacity.CapacityPriority;
import com.team.management.ports.adapters.backoffice.model.capacity.eater.CapacityEaterBackofficeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CapacityEaterController.class)
class CapacityEaterControllerTest {

    @MockBean
    private CapacityEaterService capacityEaterService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldReturnAllCapacityEatersWithoutParams() throws Exception {
        // given
        var testCapacityEater1 = new CapacityEaterBackofficeModel(
                "b",
                "testName",
                5,
                "1typeId",
                CapacityPriority.MEDIUM,
                OffsetDateTime.now(),
                "DONE"
        );

        var testCapacityEater2 = new CapacityEaterBackofficeModel(
                "a",
                "testName2",
                15,
                "1typeId",
                CapacityPriority.LOW,
                OffsetDateTime.now(),
                "DONE"
        );
        var expectedCapacityEaters = List.of(testCapacityEater1, testCapacityEater2);

        given(capacityEaterService.search(null,null,null,null,null,null)).willReturn(expectedCapacityEaters);

        // when and then
        mockMvc.perform(get("/api/capacity-eater"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedCapacityEaters.size()))
                .andExpect(jsonPath("$.[0].id").value(expectedCapacityEaters.get(0).id()))
                .andExpect(jsonPath("$.[0].name").value(expectedCapacityEaters.get(0).name()))
                // для второго элемента
                .andExpect(jsonPath("$.[1].id").value(expectedCapacityEaters.get(1).id()))
                .andExpect(jsonPath("$.[1].name").value(expectedCapacityEaters.get(1).name()));

    }



}