package com.team.management.application;

import com.team.management.domain.capacity.CapacityTemplateEaterRepository;
import com.team.management.ports.adapters.backoffice.model.capacity.templateeaters.CapacityTemplateEaterBackofficeModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapacityTemplateEaterService {

    private final CapacityTemplateEaterRepository capacityTemplateEaterRepository;

    public CapacityTemplateEaterService(CapacityTemplateEaterRepository capacityTemplateEaterRepository) {
        this.capacityTemplateEaterRepository = capacityTemplateEaterRepository;
    }

    public void saveTemplateEater(@NotNull String capacityTemplateId, @NotNull String capacityEaterId) {
        capacityTemplateEaterRepository.saveTemplateEater(capacityTemplateId, capacityEaterId);
    }

    public void removeAllEatersByTemplateId(@NotNull String capacityTemplateId) {
        capacityTemplateEaterRepository.removeAllEatersByTemplateId(capacityTemplateId);
    }

    public void removeEater(@NotNull String capacityTemplateId, @NotNull String capacityEaterId) {
        capacityTemplateEaterRepository.removeEater(capacityTemplateId, capacityEaterId);
    }

    public List<CapacityTemplateEaterBackofficeModel> search(@Nullable String capacityTemplateId, @Nullable String capacityEaterId) {
        return capacityTemplateEaterRepository.search(capacityTemplateId, capacityEaterId).stream()
                .map(capacityTemplateEater -> new CapacityTemplateEaterBackofficeModel(
                        capacityTemplateEater.capacityTemplateId(),
                        capacityTemplateEater.capacityEaterId()
                ))
                .toList();
    }

    public Optional<CapacityTemplateEaterBackofficeModel> ofId(@NotNull String capacityTemplateId, @NotNull String capacityEaterId) {
        return capacityTemplateEaterRepository.ofId(capacityTemplateId, capacityEaterId).stream()
                .map(capacityTemplateEater -> new CapacityTemplateEaterBackofficeModel(
                        capacityTemplateEater.capacityTemplateId(),
                        capacityTemplateEater.capacityEaterId()
                ))
                .findFirst();
    }
}
