package com.chjcfcaloc2020.fafi.service;

import com.chjcfcaloc2020.fafi.common.LeagueStatus;
import com.chjcfcaloc2020.fafi.dto.LeagueDTO;
import com.chjcfcaloc2020.fafi.entity.League;
import com.chjcfcaloc2020.fafi.entity.Team;
import com.chjcfcaloc2020.fafi.entity.User;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceAlreadyExistsException;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.repository.LeagueRepository;
import com.chjcfcaloc2020.fafi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;
    private final TeamService teamService;

    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    public League getLeagueById(String id) {
        return leagueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("League with ID = " + id + " not found"));
    }

    public List<League> getMyLeagues(String organizerUsername) {
        return leagueRepository.findByOrganizerUsername(organizerUsername);
    }

    public Set<Team> getTeamsInLeague(String leagueId) {
        League league = getLeagueById(leagueId);
        return new HashSet<>(league.getTeams());
    }

    public boolean isLeagueOrganizer(String leagueId, String organizerUsername) {
        League league = getLeagueById(leagueId);
        return league.getOrganizer() != null && league.getOrganizer().getUsername().equals(organizerUsername);
    }

    public League createLeague(LeagueDTO leagueDTO, String organizerUsername) {
        if (leagueRepository.existsByName(leagueDTO.getName())) {
            throw new ResourceAlreadyExistsException("League already exists");
        }
        User organizer = userRepository.findById(organizerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found"));

        League league = League.builder()
                .name(leagueDTO.getName())
                .description(leagueDTO.getDescription())
                .teamNumber(leagueDTO.getTeamNumber())
                .teamSize(leagueDTO.getTeamSize())
                .format(leagueDTO.getFormat())
                .startDate(leagueDTO.getStartDate())
                .endDate(leagueDTO.getEndDate())
                .status(LeagueStatus.UPCOMING)
                .organizer(organizer)
                .build();

        return leagueRepository.save(league);
    }

    public League updateLeague(String id, LeagueDTO leagueDTO, String organizerUsername) {
        League league = getLeagueById(id);
        if (!league.getOrganizer().getUsername().equals(organizerUsername)) {
            throw new AccessDeniedException("You can only update your own leagues");
        }
        if (!league.getName().equals(leagueDTO.getName()) &&
                leagueRepository.existsByName(leagueDTO.getName())) {
            throw new ResourceAlreadyExistsException("League's name already exists");
        }

        league.setName(leagueDTO.getName());
        league.setDescription(leagueDTO.getDescription());
        league.setTeamNumber(leagueDTO.getTeamNumber());
        league.setTeamSize(leagueDTO.getTeamSize());
        league.setFormat(leagueDTO.getFormat());
        league.setStartDate(leagueDTO.getStartDate());
        league.setEndDate(leagueDTO.getEndDate());
        league.setStatus(leagueDTO.getStatus());

        return leagueRepository.save(league);
    }

    public void deleteLeague(String id, String organizerUsername) {
        League league = getLeagueById(id);
        if (!league.getOrganizer().getUsername().equals(organizerUsername)) {
            throw new AccessDeniedException("You can only delete your own leagues");
        }
        leagueRepository.delete(league);
    }

    @Transactional
    public void addTeamToLeague(String leagueId, String teamId) {
        League league = getLeagueById(leagueId);
        Team team = teamService.getTeamById(teamId);

        league.getTeams().add(team);
        leagueRepository.save(league);
    }
}
