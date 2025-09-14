package com.chjcfcaloc2020.fafi.dto.request;

import com.chjcfcaloc2020.fafi.common.LeagueInvitationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateInvitationStatus {
    @NotNull(message = "Status is required")
    private LeagueInvitationStatus status;
}
