package com.chjcfcaloc2020.fafi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "league_stage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stage_name", nullable = false)
    private String stageName;
}
