package com.chjcfcaloc2020.fafi.repository;

import com.chjcfcaloc2020.fafi.common.LeagueInvitationStatus;
import com.chjcfcaloc2020.fafi.entity.LeagueInvitation;
import com.chjcfcaloc2020.fafi.entity.LeagueInvitationPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueInvitationRepository extends JpaRepository<LeagueInvitation, LeagueInvitationPK> {
    List<LeagueInvitation> findByLeagueId(String leagueId);
    List<LeagueInvitation> findByManagerUsername(String managerUsername);
    List<LeagueInvitation> findByLeagueIdAndStatus(String leagueId, LeagueInvitationStatus status);
    List<LeagueInvitation> findByManagerUsernameAndStatus(String managerUsername, LeagueInvitationStatus status);
    Optional<LeagueInvitation> findByLeagueIdAndManagerUsername(String leagueId, String managerUsername);
    boolean existsByLeagueIdAndManagerUsername(String leagueId, String managerUsername);
}
