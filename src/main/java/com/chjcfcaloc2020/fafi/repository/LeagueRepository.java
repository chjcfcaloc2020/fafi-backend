package com.chjcfcaloc2020.fafi.repository;

import com.chjcfcaloc2020.fafi.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeagueRepository extends JpaRepository<League, String> {
    boolean existsByName(String name);
    List<League> findByOrganizerUsername(String organizerUsername);
}
