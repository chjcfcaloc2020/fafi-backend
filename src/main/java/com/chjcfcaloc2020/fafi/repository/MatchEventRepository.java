package com.chjcfcaloc2020.fafi.repository;

import com.chjcfcaloc2020.fafi.entity.MatchEvent;
import com.chjcfcaloc2020.fafi.entity.MatchEventPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchEventRepository extends JpaRepository<MatchEvent, MatchEventPK> {
    List<MatchEvent> findByMatchId(Long matchId);
    List<MatchEvent> findByPlayerId(Long playerId);
    List<MatchEvent> findByEventId(Long eventId);
    List<MatchEvent> findByMatchIdAndPlayerId(Long matchId, Long playerId);
    List<MatchEvent> findByMatchIdAndEventId(Long matchId, Long eventId);
    boolean existsByMatchIdAndPlayerIdAndEventIdAndMinute(Long matchId, Long playerId, Long eventId, int minute);
    Optional<MatchEvent> findByMatchIdAndPlayerIdAndEventId(Long matchId, Long playerId, Long eventId);
}
