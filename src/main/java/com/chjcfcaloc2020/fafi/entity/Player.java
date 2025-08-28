package com.chjcfcaloc2020.fafi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "player")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private Date dob;
    private String position;

    @Column(name = "shirt_number", nullable = false)
    private Integer shirtNumber;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}