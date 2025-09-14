package com.chjcfcaloc2020.fafi.dto;

import com.chjcfcaloc2020.fafi.common.LeagueInvitationStatus;
import com.chjcfcaloc2020.fafi.entity.LeagueInvitation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueInvitationDTO {
    private String leagueId;
    private String leagueName;
    private String managerUsername;
    private String managerEmail;
    private LeagueInvitationStatus status;
    private LocalDateTime createdDate;

    public LeagueInvitationDTO(LeagueInvitation invitation) {
        this.leagueId = invitation.getLeague().getId();
        this.leagueName = invitation.getLeague().getName();
        this.managerUsername = invitation.getManager().getUsername();
        this.managerEmail = invitation.getManager().getEmail();
        this.status = invitation.getStatus();
        this.createdDate = invitation.getCreatedDate();
    }
}
