package com.chjcfcaloc2020.fafi.dto;

import com.chjcfcaloc2020.fafi.entity.MatchEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchEventDTO {
    private Long matchId;
    private String matchName;
    private Long playerId;
    private String playerName;
    private Long eventId;
    private String eventName;
    private int minute;
    private String description;

    public MatchEventDTO(MatchEvent matchEvent) {
        this.matchId = matchEvent.getMatch().getId();
        this.matchName = matchEvent.getMatch().getFirstTeam().getName() + " vs " +
                matchEvent.getMatch().getSecondTeam().getName();
        this.playerId = matchEvent.getPlayer().getId();
        this.playerName = matchEvent.getPlayer().getName();
        this.eventId = matchEvent.getEvent().getId();
        this.eventName = matchEvent.getEvent().getEventName();
        this.minute = matchEvent.getMinute();
        this.description = matchEvent.getDescription();
    }
}
