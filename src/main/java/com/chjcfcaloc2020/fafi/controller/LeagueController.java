package com.chjcfcaloc2020.fafi.controller;

import com.chjcfcaloc2020.fafi.dto.LeagueDTO;
import com.chjcfcaloc2020.fafi.dto.TeamDTO;
import com.chjcfcaloc2020.fafi.entity.League;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.service.LeagueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leagues")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LeagueController {
    private final LeagueService leagueService;

    @GetMapping
    public ResponseEntity<List<LeagueDTO>> getAllLeagues() {
        List<League> leagues = leagueService.getAllLeagues();
        List<LeagueDTO> leagueDTOs = leagues.stream().map(LeagueDTO::new).toList();
        if (leagueDTOs.isEmpty()) {
            throw new ResourceNotFoundException("Leagues not found");
        }
        return ResponseEntity.ok(leagueDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDTO> getLeagueById(@PathVariable String id) {
        League league = leagueService.getLeagueById(id);
        return ResponseEntity.ok(new LeagueDTO(league));
    }

    @GetMapping("/{leagueId}/teams")
    public ResponseEntity<List<TeamDTO>> getTeamsInLeague(@PathVariable String leagueId) {
        List<TeamDTO> teams = leagueService.getTeamsInLeague(leagueId).stream()
                .map(TeamDTO::new)
                .toList();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/my-leagues")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<List<LeagueDTO>> getMyLeagues(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new AccessDeniedException("You must be logged in to get all your teams");
        }
        List<League> leagues = leagueService.getMyLeagues(userDetails.getUsername());
        List<LeagueDTO> leagueDTOS = leagues.stream()
                .map(LeagueDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(leagueDTOS);
    }

    @GetMapping("/{leagueId}/is-organizer")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<Boolean> checkIfOrganizer(
            @PathVariable String leagueId,
            @AuthenticationPrincipal UserDetails userDetails) {
        boolean isOrganizer = leagueService.isLeagueOrganizer(leagueId, userDetails.getUsername());
        return ResponseEntity.ok(isOrganizer);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<LeagueDTO> createLeague(
            @RequestBody @Valid LeagueDTO leagueDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new AccessDeniedException("You must be logged in to create a league");
        }
        League createdLeague = leagueService.createLeague(leagueDTO, userDetails.getUsername());
        return ResponseEntity.ok(new LeagueDTO(createdLeague));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<LeagueDTO> updateLeague(
            @PathVariable String id,
            @RequestBody @Valid LeagueDTO leagueDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new AccessDeniedException("You must be logged in to update a league");
        }
        League updatedLeague = leagueService.updateLeague(id, leagueDTO, userDetails.getUsername());
        return ResponseEntity.ok(new LeagueDTO(updatedLeague));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<Void> deleteLeague(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        leagueService.deleteLeague(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{leagueId}/teams/{teamId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<String> addTeamToLeague(
            @PathVariable String leagueId,
            @PathVariable String teamId,
            @AuthenticationPrincipal UserDetails userDetails) {
        leagueService.addTeamToLeague(leagueId, teamId);
        return ResponseEntity.ok("Add team successfully!");
    }
}
