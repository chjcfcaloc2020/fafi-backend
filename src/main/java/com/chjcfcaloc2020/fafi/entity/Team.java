package com.chjcfcaloc2020.fafi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String logo;

    @Column(name = "coach_name")
    private String coachName;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToOne
    @JoinColumn(referencedColumnName = "username")
    private User manager;
}
