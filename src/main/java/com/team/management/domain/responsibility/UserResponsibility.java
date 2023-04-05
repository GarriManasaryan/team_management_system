package com.team.management.domain.responsibility;

import org.jetbrains.annotations.NotNull;

public record UserResponsibility(
        @NotNull String userId,
        @NotNull String respId
) {

}
