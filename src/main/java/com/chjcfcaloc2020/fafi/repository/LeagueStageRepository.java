package com.chjcfcaloc2020.fafi.repository;

import com.chjcfcaloc2020.fafi.entity.LeagueStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueStageRepository extends JpaRepository<LeagueStage, Long> {
    boolean existsByStageName(String name);
}
