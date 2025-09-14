package com.chjcfcaloc2020.fafi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateInvitationRequest {
    @NotBlank(message = "Manager username is required")
    @JsonProperty("manager_username")
    private String managerUsername;

    @NotBlank(message = "League ID is required")
    @JsonProperty("league_id")
    private String leagueId;
}
