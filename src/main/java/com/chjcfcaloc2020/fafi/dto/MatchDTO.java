package com.chjcfcaloc2020.fafi.dto;

import com.chjcfcaloc2020.fafi.common.MatchStatus;
import com.chjcfcaloc2020.fafi.entity.Match;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {
    private Long id;
    private TeamDTO firstTeam;
    private TeamDTO secondTeam;
    private Date matchDate;
    private String location;
    private Integer firstTeamScore;
    private Integer secondTeamScore;
    private String leagueId;
    private String leagueName;
    private Long stageId;
    private String stageName;
    private MatchStatus status;

    public MatchDTO(Match match) {
        this.id = match.getId();
        this.firstTeam = new TeamDTO(match.getFirstTeam());
        this.secondTeam = new TeamDTO(match.getSecondTeam());
        this.matchDate = match.getMatchDate();
        this.location = match.getLocation();
        this.firstTeamScore = match.getFirstTeamScore();
        this.secondTeamScore = match.getSecondTeamScore();
        this.leagueId = match.getLeague() != null ? match.getLeague().getId() : null;
        this.leagueName = match.getLeague() != null ? match.getLeague().getName() : null;
        this.stageId = match.getLeagueStage() != null ? match.getLeagueStage().getId() : null;
        this.stageName = match.getLeagueStage() != null ? match.getLeagueStage().getStageName() : null;
        this.status = match.getStatus();
    }
}
