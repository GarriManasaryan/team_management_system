package com.team.management.application;

import com.team.management.domain.capacity.CapacityTemplate;
import com.team.management.domain.capacity.CapacityTemplateRepository;
import com.team.management.ports.adapters.backoffice.model.capacity.CapacityTemplateBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.capacity.CapacityTemplateCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapacityTemplateService {

    private final CapacityTemplateRepository capacityTemplateRepository;

    public CapacityTemplateService(CapacityTemplateRepository capacityTemplateRepository) {
        this.capacityTemplateRepository = capacityTemplateRepository;
    }

    public void save(@NotNull CapacityTemplateCreationRequest capacityTemplateCreationRequest) {
        capacityTemplateRepository.save(
                CapacityTemplate.ofId(
                        capacityTemplateCreationRequest.title(),
                        capacityTemplateCreationRequest.description()
                )
        );
    }

    public void delete(@NotNull String id) {
        capacityTemplateRepository.delete(id);
    }

    public void patch(@NotNull String id, @Nullable String title, @Nullable String description) {
        capacityTemplateRepository.patch(id, title, description);
    }

    public List<CapacityTemplateBackofficeModel> search(@Nullable String title, @Nullable String description) {
        return capacityTemplateRepository.search(title, description).stream()
                .map(capacityTemplate -> new CapacityTemplateBackofficeModel(
                        capacityTemplate.id(),
                        capacityTemplate.title(),
                        capacityTemplate.description()
                ))
                .toList();
    }

    public Optional<CapacityTemplateBackofficeModel> ofId(@NotNull String id) {
        return capacityTemplateRepository.ofId(id).stream()
                .map(capacityTemplate -> new CapacityTemplateBackofficeModel(
                        capacityTemplate.id(),
                        capacityTemplate.title(),
                        capacityTemplate.description()
                ))
                .toList().stream().findFirst();
    }
}
