package com.team.management.application;

import com.team.management.domain.skill.Skill;
import com.team.management.domain.skill.SkillRepository;
import com.team.management.ports.adapters.backoffice.model.skill.SkillBackofficeModel;
import com.team.management.ports.adapters.backoffice.model.skill.SkillCreationRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void save(@NotNull SkillCreationRequest skillCreationRequest) {
        skillRepository.save(Skill.of(skillCreationRequest.name()));
    }

    public void rename(@NotNull String skillId, @NotNull String newName) {
        skillRepository.rename(skillId, newName);
    }

    public void delete(@NotNull String skillId) {
        skillRepository.delete(skillId);
    }

    public List<SkillBackofficeModel> search(@Nullable String name) {
        return skillRepository.search(name).stream()
                .map(skill -> new SkillBackofficeModel(
                        skill.id(),
                        skill.name()
                ))
                .toList();
    }

    public Optional<SkillBackofficeModel> ofId(@NotNull String skillId) {
        return skillRepository.ofId(skillId)
                .map(skill -> new SkillBackofficeModel(
                        skillId,
                        skill.name()
                ));
    }
}
