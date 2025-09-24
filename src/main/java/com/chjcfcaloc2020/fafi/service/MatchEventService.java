package com.chjcfcaloc2020.fafi.service;

import com.chjcfcaloc2020.fafi.entity.*;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.repository.EventRepository;
import com.chjcfcaloc2020.fafi.repository.MatchEventRepository;
import com.chjcfcaloc2020.fafi.repository.MatchRepository;
import com.chjcfcaloc2020.fafi.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchEventService {
    private final MatchEventRepository matchEventRepository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final EventRepository eventRepository;
    private final MatchService matchService;
    private final PlayerService playerService;
    private final EventService eventService;

    public List<MatchEvent> getAllMatchEvents() {
        return matchEventRepository.findAll();
    }

    public MatchEvent getMatchEventById(MatchEventPK id) {
        return matchEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match event not found"));
    }

    public MatchEvent createMatchEvent(MatchEvent matchEvent, Long matchId, Long playerId, Long eventId) {
        // Validate unique combination
        if (matchEventRepository.existsByMatchIdAndPlayerIdAndEventIdAndMinute(
                matchId, playerId, eventId, matchEvent.getMinute())) {
            throw new IllegalArgumentException("Duplicate match event found for the same minute");
        }
        // Set relationships
        Match match = matchService.getMatchById(matchId);
        matchEvent.setMatch(match);
        Player player = playerService.getPlayerById(playerId);
        matchEvent.setPlayer(player);
        Event event = eventService.getEventById(eventId);
        matchEvent.setEvent(event);
        // Validate player belongs to one of the teams in the match
//        validatePlayerInMatch(player, match);

        // Validate minute is within reasonable range
        validateMinute(matchEvent.getMinute());

        // Set composite ID
        MatchEventPK id = new MatchEventPK(matchId, playerId, eventId);
        matchEvent.setId(id);

        return matchEventRepository.save(matchEvent);
    }

//    private void validatePlayerInMatch(Player player, Match match) {
//        boolean playerInFirstTeam = match.getFirstTeam().getPlayers().stream()
//                .anyMatch(p -> p.getId().equals(player.getId()));
//        boolean playerInSecondTeam = match.getSecondTeam().getPlayers().stream()
//                .anyMatch(p -> p.getId().equals(player.getId()));
//
//        if (!playerInFirstTeam && !playerInSecondTeam) {
//            throw new IllegalArgumentException("Player does not belong to any team in this match");
//        }
//    }

    private void validateMinute(int minute) {
        if (minute < 0 || minute > 120) { // 120 minutes for extra time
            throw new IllegalArgumentException("Minute must be between 0 and 120");
        }
    }
}
