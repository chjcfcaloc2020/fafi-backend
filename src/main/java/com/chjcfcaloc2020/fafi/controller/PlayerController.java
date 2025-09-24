package com.chjcfcaloc2020.fafi.controller;

import com.chjcfcaloc2020.fafi.dto.PlayerDTO;
import com.chjcfcaloc2020.fafi.entity.Player;
import com.chjcfcaloc2020.fafi.service.PlayerService;
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
@RequestMapping("/api/players")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        List<PlayerDTO> playerDTOs = players.stream()
                .map(PlayerDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(new PlayerDTO(player));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeam(@PathVariable String teamId) {
        List<Player> players = playerService.getPlayersByTeam(teamId);
        List<PlayerDTO> playerDTOs = players.stream()
                .map(PlayerDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<PlayerDTO> createPlayer(
            @Valid @RequestBody PlayerDTO playerDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        Player createdPlayer = playerService.createPlayer(playerDTO, playerDTO.getTeam());
        return ResponseEntity.status(HttpStatus.CREATED).body(new PlayerDTO(createdPlayer));
    }

    @PutMapping("/{id}/team/{teamId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<PlayerDTO> updatePlayer(
            @PathVariable Long id,
            @PathVariable String teamId,
            @Valid @RequestBody PlayerDTO playerDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        Player updatedPlayer = playerService.updatePlayer(id, playerDTO, teamId);
        return ResponseEntity.ok(new PlayerDTO(updatedPlayer));
    }

    @DeleteMapping("/{id}/team/{teamId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<Void> deletePlayer(
            @PathVariable Long id,
            @PathVariable String teamId,
            @AuthenticationPrincipal UserDetails userDetails) {
        playerService.deletePlayer(id, teamId);
        return ResponseEntity.noContent().build();
    }
}
