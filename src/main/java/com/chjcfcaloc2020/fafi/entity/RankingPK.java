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
public class RankingPK implements Serializable {
    private String leagueId;
    private String teamId;
}