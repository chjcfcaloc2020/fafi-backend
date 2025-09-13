package com.chjcfcaloc2020.fafi.entity;

import com.chjcfcaloc2020.fafi.common.LeagueFormat;
import com.chjcfcaloc2020.fafi.common.LeagueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "league")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "team_number", nullable = false)
    private Integer teamNumber;

    @Column(name = "team_size", nullable = false)
    private Integer teamSize;

    @Enumerated(EnumType.STRING)
    private LeagueFormat format;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private LeagueStatus status;

    @ManyToMany
    @JoinTable(
            name = "league_team",
            joinColumns = @JoinColumn(name = "league_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    @ManyToOne
    @JoinColumn(referencedColumnName = "username")
    private User organizer;
}