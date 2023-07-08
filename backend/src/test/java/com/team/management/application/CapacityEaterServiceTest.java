package com.team.management.application;

import com.team.management.domain.capacity.CapacityEater;
import com.team.management.domain.capacity.CapacityEaterRepository;
import com.team.management.domain.capacity.CapacityEaterStatus;
import com.team.management.domain.capacity.CapacityPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CapacityEaterServiceTest {

    @Mock
    private CapacityEaterRepository capacityEaterRepository;

    private CapacityEaterService capacityEaterService;

    @BeforeEach
    void setUp() {
        capacityEaterService = new CapacityEaterService(capacityEaterRepository);
    }

    @Test
    void shouldReturnCapacityEaterByName() {
        var testName = "Name";

        var testCapacityEater = CapacityEater.of(
                testName,
                5,
                "typeId",
                CapacityPriority.MEDIUM,
                OffsetDateTime.now(),
                CapacityEaterStatus.IN_PROGRESS
        );

        // mock
        given(capacityEaterRepository.search(
                testName, null, null, null, null, null
        )).willReturn(List.of(testCapacityEater));

        // when
        var foundCapacityEater = capacityEaterService.search(testName, null, null, null, null, null);

        // then
        assertThat(foundCapacityEater).hasSize(1);
        // вот тут есть смысл?
        assertThat(foundCapacityEater.get(0).status()).isEqualTo(CapacityEaterStatus.EnumToString(testCapacityEater.status()));

        // assertThatThrownBy().hasCause() → поймать ожидаемую ошибку

        assertThatThrownBy(() -> {
            capacityEaterService.search(testName, null, null, null, "null", null);
        }).isInstanceOf(DateTimeParseException.class)
                .hasMessage("Text 'null' could not be parsed at index 0");
    }

}