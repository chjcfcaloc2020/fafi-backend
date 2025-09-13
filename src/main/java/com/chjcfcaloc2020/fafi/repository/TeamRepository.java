package com.chjcfcaloc2020.fafi.repository;

import com.chjcfcaloc2020.fafi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, String> {
    List<Team> findByManagerUsername(String managerUsername);
}
