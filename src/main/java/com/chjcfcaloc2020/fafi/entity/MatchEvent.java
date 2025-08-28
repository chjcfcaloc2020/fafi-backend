package com.chjcfcaloc2020.fafi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "match_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEvent {
    @EmbeddedId
    private MatchEventPK id;

    @ManyToOne
    @MapsId("matchId")
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private int minute;
    private String description;
}
