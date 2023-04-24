package com.team.management.application;

import com.team.management.domain.capacity.CapacityEater;
import com.team.management.domain.capacity.CapacityEaterRepository;
import com.team.management.domain.capacity.CapacityEaterStatus;
import com.team.management.domain.capacity.CapacityPriority;
import com.team.management.ports.adapters.backoffice.model.capacity.eater.CapacityEaterBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.capacity.eater.CapacityEaterCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CapacityEaterService {

    private final CapacityEaterRepository capacityEaterRepository;

    public CapacityEaterService(CapacityEaterRepository capacityEaterRepository) {
        this.capacityEaterRepository = capacityEaterRepository;
    }

    public void save(@NotNull CapacityEaterCreationRequest capacityEaterCreationRequest) {
        capacityEaterRepository.save(CapacityEater.of(
                capacityEaterCreationRequest.name(),
                capacityEaterCreationRequest.duration(),
                capacityEaterCreationRequest.typeId(),
                CapacityPriority.valueOf(capacityEaterCreationRequest.priority()),
                OffsetDateTime.parse(capacityEaterCreationRequest.deadline()),
                CapacityEaterStatus.stringToEnum(capacityEaterCreationRequest.status())
        ));
    }

    public void delete(@NotNull String id) {
        capacityEaterRepository.delete(id);
    }

    public void patch(
            @NotNull String id,
            @Nullable String name,
            @Nullable Integer duration,
            @Nullable String typeId,
            @Nullable String priority,
            @Nullable String deadline,
            @Nullable String status
    ) {
        capacityEaterRepository.patch(
                id,
                name,
                duration,
                typeId,
                priority != null ? CapacityPriority.valueOf(priority) : null,
                deadline != null ? OffsetDateTime.parse(deadline) : null,
                status != null ? CapacityEaterStatus.stringToEnum(status) : null
        );
    }

    public List<CapacityEaterBackofficeModel> search(
            @Nullable String name,
            @Nullable Integer duration,
            @Nullable String typeId,
            @Nullable String priority,
            @Nullable String deadline,
            @Nullable String status
    ) {
        return capacityEaterRepository.search(
                        name,
                        duration,
                        typeId,
                        priority != null ? CapacityPriority.valueOf(priority) : null,
                        deadline != null ? OffsetDateTime.parse(deadline) : null,
                        status != null ? CapacityEaterStatus.stringToEnum(status) : null
                )
                .stream()
                .map(capacityEater -> new CapacityEaterBackofficeModel(
                        capacityEater.id(),
                        capacityEater.name(),
                        capacityEater.duration(),
                        capacityEater.typeId(),
                        capacityEater.priority(),
                        capacityEater.deadline(),
                        CapacityEaterStatus.EnumToString(capacityEater.status())
                ))
                .toList();
    }

    public Optional<CapacityEaterBackofficeModel> ofId(@NotNull String id) {
        return capacityEaterRepository.ofId(id).stream()
                .map(capacityEater -> new CapacityEaterBackofficeModel(
                        capacityEater.id(),
                        capacityEater.name(),
                        capacityEater.duration(),
                        capacityEater.typeId(),
                        capacityEater.priority(),
                        capacityEater.deadline(),
                        CapacityEaterStatus.EnumToString(capacityEater.status())

                ))
                .findFirst();
    }
}
