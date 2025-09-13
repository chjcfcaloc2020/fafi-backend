package com.chjcfcaloc2020.fafi.service;

import com.chjcfcaloc2020.fafi.dto.PlayerDTO;
import com.chjcfcaloc2020.fafi.entity.Player;
import com.chjcfcaloc2020.fafi.entity.Team;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceAlreadyExistsException;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.repository.PlayerRepository;
import com.chjcfcaloc2020.fafi.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player with ID = " + id + " not found"));
    }

    public List<Player> getPlayersByTeam(String teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    public Player createPlayer(PlayerDTO playerDTO, String teamId) {
        if (playerRepository.existsByTeamIdAndShirtNumber(teamId, playerDTO.getShirtNumber())) {
            throw new ResourceAlreadyExistsException("Shirt number already exists in this team");
        }
        if (playerRepository.existsByTeamIdAndName(teamId, playerDTO.getName())) {
            throw new ResourceAlreadyExistsException("PLayer's name already exists in this team");
        }
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        Player player = Player.builder()
                .name(playerDTO.getName())
                .dob(playerDTO.getDob())
                .position(playerDTO.getPosition())
                .shirtNumber(playerDTO.getShirtNumber())
                .team(team)
                .build();

        return playerRepository.save(player);
    }

    public Player updatePlayer(Long id, PlayerDTO playerDTO, String teamId) {
        Player player = getPlayerById(id);
        if (!player.getTeam().getId().equals(teamId)) {
            throw new AccessDeniedException("Player does not belong to this team");
        }
        if (!player.getShirtNumber().equals(playerDTO.getShirtNumber()) &&
                playerRepository.existsByTeamIdAndShirtNumber(teamId, playerDTO.getShirtNumber())) {
            throw new ResourceAlreadyExistsException("Shirt number already exists in this team");
        }

        player.setName(playerDTO.getName());
        player.setDob(playerDTO.getDob());
        player.setPosition(playerDTO.getPosition());
        player.setShirtNumber(playerDTO.getShirtNumber());

        return playerRepository.save(player);
    }

    public void deletePlayer(Long id, String teamId) {
        Player player = getPlayerById(id);
        if (!player.getTeam().getId().equals(teamId)) {
            throw new AccessDeniedException("Player does not belong to this team");
        }
        playerRepository.delete(player);
    }
}
