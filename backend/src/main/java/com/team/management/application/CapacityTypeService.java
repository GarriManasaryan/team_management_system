package com.team.management.application;

import com.team.management.domain.capacity.CapacityType;
import com.team.management.domain.capacity.CapacityTypeRepository;
import com.team.management.ports.adapters.backoffice.model.capacity.type.CapacityTypeBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.capacity.type.CapacityTypeCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapacityTypeService {

    private final CapacityTypeRepository capacityTypeRepository;

    public CapacityTypeService(CapacityTypeRepository capacityTypeRepository) {
        this.capacityTypeRepository = capacityTypeRepository;
    }

    public void save(@NotNull CapacityTypeCreationRequest capacityTypeCreationRequest) {
        capacityTypeRepository.save(
                CapacityType.of(
                        capacityTypeCreationRequest.name(),
                        capacityTypeCreationRequest.defaultDuration()
                )
        );
    }

    public void delete(@NotNull String id) {
        capacityTypeRepository.delete(id);
    }

    public void patch(@NotNull String id, @Nullable String name, @Nullable Integer defaultDuration) {
        capacityTypeRepository.patch(id, name, defaultDuration);
    }

    public List<CapacityTypeBackofficeModel> search(@Nullable String name, @Nullable Integer defaultDuration) {
        return capacityTypeRepository.search(name, defaultDuration).stream()
                .map(capacityType -> new CapacityTypeBackofficeModel(
                        capacityType.id(),
                        capacityType.name(),
                        capacityType.defaultDuration()
                ))
                .toList();
    }

    public Optional<CapacityTypeBackofficeModel> ofId(@NotNull String id) {
        return capacityTypeRepository.ofId(id).stream()
                .map(capacityType -> new CapacityTypeBackofficeModel(
                        capacityType.id(),
                        capacityType.name(),
                        capacityType.defaultDuration()
                ))
                .findFirst();
    }
}
