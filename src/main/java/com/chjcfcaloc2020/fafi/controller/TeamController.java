package com.chjcfcaloc2020.fafi.controller;

import com.chjcfcaloc2020.fafi.dto.TeamDTO;
import com.chjcfcaloc2020.fafi.entity.Team;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        List<TeamDTO> teamDTOS = teams.stream()
                .map(TeamDTO::new)
                .collect(Collectors.toList());
        if (teamDTOS.isEmpty()) {
            throw new ResourceNotFoundException("Team not found");
        }
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable String id) {
        Team team = teamService.getTeamById(id);
        return ResponseEntity.ok(new TeamDTO(team));
    }

    @GetMapping("/my-teams")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<List<TeamDTO>> getMyTeams(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new AccessDeniedException("You must be logged in to get all your teams");
        }
        List<Team> teams = teamService.getMyTeams(userDetails.getUsername());
        List<TeamDTO> teamDTOs = teams.stream()
                .map(TeamDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOs);
    }

    @GetMapping("/{leagueId}/my-teams")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<List<TeamDTO>> getMyTeamsInLeague(
            @PathVariable String leagueId,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new AccessDeniedException("You must be logged in to get all your teams");
        }
        List<TeamDTO> teams = teamService.getTeamsByLeagueAndManager(leagueId, userDetails.getUsername());
        return ResponseEntity.ok(teams);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<TeamDTO> createTeam(
            @Valid @RequestBody TeamDTO teamDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new AccessDeniedException("You must be logged in to create a team");
        }
        Team createdTeam = teamService.createTeam(teamDTO, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TeamDTO(createdTeam));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<TeamDTO> updateTeam(
            @PathVariable String id,
            @Valid @RequestBody TeamDTO teamDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new AccessDeniedException("You must be logged in to update a team");
        }
        Team updatedTeam = teamService.updateTeam(id, teamDTO, userDetails.getUsername());
        return ResponseEntity.ok(new TeamDTO(updatedTeam));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        teamService.deleteTeam(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
