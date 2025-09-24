package com.chjcfcaloc2020.fafi.dto;

import com.chjcfcaloc2020.fafi.common.LeagueInvitationStatus;
import com.chjcfcaloc2020.fafi.entity.LeagueInvitation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueInvitationDTO {
    @JsonProperty("league_id")
    private String leagueId;

    @JsonProperty("league_name")
    private String leagueName;

    @JsonProperty("organizer_username")
    private String organizerUsername;

    @JsonProperty("manager_username")
    private String managerUsername;

    private LeagueInvitationStatus status;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    public LeagueInvitationDTO(LeagueInvitation invitation) {
        this.leagueId = invitation.getLeague().getId();
        this.leagueName = invitation.getLeague().getName();
        this.organizerUsername = invitation.getLeague().getOrganizer().getUsername();
        this.managerUsername = invitation.getManager().getUsername();
        this.status = invitation.getStatus();
        this.createdDate = invitation.getCreatedDate();
    }
}
