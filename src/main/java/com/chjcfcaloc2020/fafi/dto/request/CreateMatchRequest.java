package com.chjcfcaloc2020.fafi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CreateMatchRequest {
    @NotNull(message = "First team ID is required")
    private String firstTeamId;

    @NotNull(message = "Second team ID is required")
    private String secondTeamId;

    @Future(message = "Match date must be in the future")
    @NotNull(message = "Match date is required")
    private Date matchDate;

    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @Min(value = 0, message = "First team score cannot be negative")
    private Integer firstTeamScore;

    @Min(value = 0, message = "Second team score cannot be negative")
    private Integer secondTeamScore;

    private String leagueId;
    private Long stageId;
}
