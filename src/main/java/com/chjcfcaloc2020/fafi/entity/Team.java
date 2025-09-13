package com.chjcfcaloc2020.fafi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String logo;

    @Column(name = "coach_name")
    private String coachName;

    @ManyToMany(mappedBy = "teams")
    private Set<League> leagues = new HashSet<>();

    @ManyToOne
    @JoinColumn(referencedColumnName = "username")
    private User manager;
}
