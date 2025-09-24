package com.chjcfcaloc2020.fafi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMatchEventRequest {
    @NotNull(message = "Match ID is required")
    @JsonProperty("match_id")
    private Long matchId;

    @NotNull(message = "Player ID is required")
    @JsonProperty("player_id")
    private Long playerId;

    @NotNull(message = "Event ID is required")
    @JsonProperty("event_id")
    private Long eventId;

    @Min(value = 0, message = "Minute cannot be negative")
    @Max(value = 120, message = "Minute cannot exceed 120")
    private int minute;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
