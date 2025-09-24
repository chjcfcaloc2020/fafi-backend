package com.chjcfcaloc2020.fafi.controller;

import com.chjcfcaloc2020.fafi.dto.MatchDTO;
import com.chjcfcaloc2020.fafi.dto.request.CreateMatchRequest;
import com.chjcfcaloc2020.fafi.entity.Match;
import com.chjcfcaloc2020.fafi.entity.Team;
import com.chjcfcaloc2020.fafi.repository.LeagueRepository;
import com.chjcfcaloc2020.fafi.repository.LeagueStageRepository;
import com.chjcfcaloc2020.fafi.repository.TeamRepository;
import com.chjcfcaloc2020.fafi.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MatchController {
    private final MatchService matchService;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final LeagueStageRepository leagueStageRepository;

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        List<MatchDTO> matchDTOs = matches.stream()
                .map(MatchDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(matchDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long id) {
        Match match = matchService.getMatchById(id);
        return ResponseEntity.ok(new MatchDTO(match));
    }

    @GetMapping("/league/{leagueId}")
    public ResponseEntity<List<MatchDTO>> getMatchesByLeague(@PathVariable String leagueId) {
        List<Match> matches = matchService.getMatchesByLeague(leagueId);
        List<MatchDTO> matchDTOs = matches.stream()
                .map(MatchDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(matchDTOs);
    }

    @GetMapping("/stage/{stageId}")
    public ResponseEntity<List<MatchDTO>> getMatchesByStage(@PathVariable Long stageId) {
        List<Match> matches = matchService.getMatchesByStage(stageId);
        List<MatchDTO> matchDTOs = matches.stream()
                .map(MatchDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(matchDTOs);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<MatchDTO>> getMatchesByTeam(@PathVariable String teamId) {
        List<Match> matches = matchService.getMatchesByTeam(teamId);
        List<MatchDTO> matchDTOs = matches.stream()
                .map(MatchDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(matchDTOs);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<MatchDTO> createMatch(
            @Valid @RequestBody CreateMatchRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Match createdMatch = matchService.createMatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MatchDTO(createdMatch));
    }
}
