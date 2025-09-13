package com.chjcfcaloc2020.fafi.repository;

import com.chjcfcaloc2020.fafi.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeamId(String id);
    boolean existsByTeamIdAndShirtNumber(String teamId, Integer shirtNumber);
    boolean existsByTeamIdAndName(String teamId, String name);
}
