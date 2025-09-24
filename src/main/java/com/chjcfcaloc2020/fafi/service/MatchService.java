package com.chjcfcaloc2020.fafi.service;

import com.chjcfcaloc2020.fafi.common.MatchStatus;
import com.chjcfcaloc2020.fafi.dto.request.CreateMatchRequest;
import com.chjcfcaloc2020.fafi.entity.League;
import com.chjcfcaloc2020.fafi.entity.LeagueStage;
import com.chjcfcaloc2020.fafi.entity.Match;
import com.chjcfcaloc2020.fafi.entity.Team;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceAlreadyExistsException;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.repository.LeagueRepository;
import com.chjcfcaloc2020.fafi.repository.LeagueStageRepository;
import com.chjcfcaloc2020.fafi.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final LeagueRepository leagueRepository;
    private final LeagueStageRepository leagueStageRepository;
    private final TeamService teamService;
    private final LeagueService leagueService;

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id));
    }

    public List<Match> getMatchesByLeague(String leagueId) {
        return matchRepository.findByLeagueId(leagueId);
    }

    public List<Match> getMatchesByStage(Long stageId) {
        return matchRepository.findByLeagueStageId(stageId);
    }

    public List<Match> getMatchesByTeam(String teamId) {
        return matchRepository.findByFirstTeamIdOrSecondTeamId(teamId, teamId);
    }

    public Match createMatch(CreateMatchRequest matchRequest) {
        // Get 2 teams
        Team firstTeam = teamService.getTeamById(matchRequest.getFirstTeamId());
        Team secondTeam = teamService.getTeamById(matchRequest.getSecondTeamId());

        // Validate teams
        validateTeams(firstTeam, secondTeam);

        if (matchRequest.getLeagueId() != null && matchRequest.getStageId() != null) {
            League league = leagueService.getLeagueById(matchRequest.getLeagueId());
            LeagueStage stage = leagueStageRepository.findById(matchRequest.getStageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

            Match match = Match.builder()
                    .firstTeam(firstTeam)
                    .secondTeam(secondTeam)
                    .matchDate(matchRequest.getMatchDate())
                    .location(matchRequest.getLocation())
                    .firstTeamScore(0)
                    .secondTeamScore(0)
                    .status(MatchStatus.UPCOMING)
                    .league(league)
                    .leagueStage(stage)
                    .build();

            return matchRepository.save(match);
        }

        return null;
    }

    private void validateTeams(Team firstTeam, Team secondTeam) {
        if (firstTeam.getId().equals(secondTeam.getId())) {
            throw new ResourceAlreadyExistsException("A team cannot play against itself");
        }
    }
}
