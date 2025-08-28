package com.chjcfcaloc2020.fafi.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MatchEventPK implements Serializable {
    private Long matchId;
    private Long playerId;
    private Long eventId;
}
