package com.chjcfcaloc2020.fafi.repository;

import com.chjcfcaloc2020.fafi.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByLeagueId(String leagueId);
    List<Match> findByLeagueStageId(Long stageId);
    List<Match> findByFirstTeamIdOrSecondTeamId(String teamId1, String teamId2);
}
