package com.chjcfcaloc2020.fafi.service;

import com.chjcfcaloc2020.fafi.dto.TeamDTO;
import com.chjcfcaloc2020.fafi.entity.Team;
import com.chjcfcaloc2020.fafi.entity.User;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.repository.TeamRepository;
import com.chjcfcaloc2020.fafi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(String id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID = " + id + " not found"));
    }

    public List<Team> getMyTeams(String managerUsername) {
        return teamRepository.findByManagerUsername(managerUsername);
    }

    public List<TeamDTO> getTeamsByLeagueAndManager(String leagueId, String managerUsername) {
        List<Team> teams = teamRepository.findTeamsByLeagueAndManager(leagueId, managerUsername);
        return teams.stream()
                .map(t -> new TeamDTO(
                        t.getId(),
                        t.getName(),
                        t.getLogo(),
                        t.getCoachName(),
                        (t.getManager() != null ? t.getManager().getUsername() : null)
                ))
                .collect(Collectors.toList());
    }

    public Team createTeam(TeamDTO teamDTO, String managerUsername) {
        User manager = userRepository.findById(managerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

        Team team = Team.builder()
                .name(teamDTO.getName())
                .logo(teamDTO.getLogo())
                .coachName(teamDTO.getCoachName())
                .manager(manager)
                .build();

        return teamRepository.save(team);
    }

    public Team updateTeam(String id, TeamDTO teamDTO, String managerUsername) {
        Team team = getTeamById(id);
        if (!team.getManager().getUsername().equals(managerUsername)) {
            throw new AccessDeniedException("You can only update your own teams");
        }

        team.setName(teamDTO.getName());
        team.setLogo(teamDTO.getLogo());
        team.setCoachName(teamDTO.getCoachName());

        return teamRepository.save(team);
    }

    public void deleteTeam(String id, String managerUsername) {
        Team team = getTeamById(id);
        if (!team.getManager().getUsername().equals(managerUsername)) {
            throw new AccessDeniedException("You can only delete your own teams");
        }
        teamRepository.delete(team);
    }
}
