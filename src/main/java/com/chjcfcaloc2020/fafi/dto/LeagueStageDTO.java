package com.chjcfcaloc2020.fafi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeagueStageDTO {
    @NotBlank(message = "Please type stage's name!")
    @NotNull(message = "Stage's name is required")
    private String stageName;
}
