package com.chjcfcaloc2020.fafi.controller;

import com.chjcfcaloc2020.fafi.dto.LeagueDTO;
import com.chjcfcaloc2020.fafi.entity.League;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.service.LeagueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leagues")
@RequiredArgsConstructor
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

    @PostMapping
    public ResponseEntity<LeagueDTO> createLeague(
            @RequestBody @Valid LeagueDTO leagueDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        League createdLeague = leagueService.createLeague(leagueDTO, userDetails.getUsername());
        return ResponseEntity.ok(new LeagueDTO(createdLeague));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueDTO> updateLeague(
            @PathVariable String id,
            @RequestBody @Valid LeagueDTO leagueDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        League updatedLeague = leagueService.updateLeague(id, leagueDTO, userDetails.getUsername());
        return ResponseEntity.ok(new LeagueDTO(updatedLeague));
    }
}
