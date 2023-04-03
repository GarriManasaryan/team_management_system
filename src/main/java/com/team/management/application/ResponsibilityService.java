package com.team.management.application;

import com.team.management.domain.responsibility.Responsibility;
import com.team.management.domain.responsibility.ResponsibilityRepository;
import com.team.management.ports.adapters.backoffice.model.responsibility.ResponsibilityBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.responsibility.ResponsibilityCreationRequest;
import com.team.management.ports.adapters.backoffice.model.responsibility.ResponsibilityPatchRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponsibilityService {

    private final ResponsibilityRepository responsibilityRepository;

    public ResponsibilityService(ResponsibilityRepository responsibilityRepository) {
        this.responsibilityRepository = responsibilityRepository;
    }

    public void save(@NotNull ResponsibilityCreationRequest responsibilityCreationRequest) {

        responsibilityRepository.save(Responsibility.of(
                responsibilityCreationRequest.name(),
                responsibilityCreationRequest.outerId(),
                responsibilityCreationRequest.label()
        ));
    }

    public void delete(@NotNull String respId) {
        responsibilityRepository.delete(respId);
    }

    public void patch(@NotNull String respId, @NotNull ResponsibilityPatchRequest responsibilityPatchRequest) {
        responsibilityRepository.patch(
                respId,
                responsibilityPatchRequest.name(),
                responsibilityPatchRequest.outerId(),
                responsibilityPatchRequest.label());
    }

    public List<ResponsibilityBackofficeModel> search(@Nullable String name, @Nullable String outerId, @Nullable String label) {
        return responsibilityRepository.search(name, outerId, label).stream()
                .map(responsibility -> new ResponsibilityBackofficeModel(
                        responsibility.id(),
                        responsibility.name(),
                        responsibility.outerId(),
                        responsibility.label()
                ))
                .toList();
    }

    public Optional<Responsibility> ofId(@NotNull String respId) {
        return responsibilityRepository.ofId(respId);
    }
}
