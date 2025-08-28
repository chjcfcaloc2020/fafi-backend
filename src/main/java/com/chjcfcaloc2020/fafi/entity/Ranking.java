package com.chjcfcaloc2020.fafi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ranking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ranking {
    @EmbeddedId
    private RankingPK id;

    @ManyToOne
    @MapsId("leagueId")
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @OneToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private int points;
    private int wins;
    private int draws;
    private int losses;
}
