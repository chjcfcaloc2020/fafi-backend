package com.chjcfcaloc2020.fafi.repository;

import com.chjcfcaloc2020.fafi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, String> {
    List<Team> findByManagerUsername(String managerUsername);

    @Query("""
            SELECT t
            FROM League l
            JOIN l.teams t
            WHERE l.id = :leagueId AND t.manager.username = :managerUsername
            """)
    List<Team> findTeamsByLeagueAndManager(@Param("leagueId") String leagueId,
                                           @Param("managerUsername") String managerUsername);
}
