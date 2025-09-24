package com.chjcfcaloc2020.fafi.entity;

import com.chjcfcaloc2020.fafi.common.MatchStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_team_id")
    private Team firstTeam;

    @ManyToOne
    @JoinColumn(name = "second_team_id")
    private Team secondTeam;

    @Column(name = "match_date", nullable = false)
    private Date matchDate;

    private String location;
    private Integer firstTeamScore;
    private Integer secondTeamScore;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private LeagueStage leagueStage;
}
