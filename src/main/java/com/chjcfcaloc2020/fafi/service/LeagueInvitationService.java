package com.chjcfcaloc2020.fafi.service;

import com.chjcfcaloc2020.fafi.common.UserRole;
import com.chjcfcaloc2020.fafi.entity.League;
import com.chjcfcaloc2020.fafi.entity.LeagueInvitation;
import com.chjcfcaloc2020.fafi.common.LeagueInvitationStatus;
import com.chjcfcaloc2020.fafi.entity.LeagueInvitationPK;
import com.chjcfcaloc2020.fafi.entity.User;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceAlreadyExistsException;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.repository.LeagueInvitationRepository;
import com.chjcfcaloc2020.fafi.repository.LeagueRepository;
import com.chjcfcaloc2020.fafi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueInvitationService {
    private final LeagueInvitationRepository invitationRepository;
    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;
    private final TeamService teamService;

    public List<LeagueInvitation> getAllInvitations() {
        return invitationRepository.findAll();
    }

    public LeagueInvitation getInvitationById(String leagueId, String managerUsername) {
        return invitationRepository.findByLeagueIdAndManagerUsername(leagueId, managerUsername)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invitation not found for league: " + leagueId + " and manager: " + managerUsername));
    }

    public List<LeagueInvitation> getInvitationsByLeague(String leagueId) {
        return invitationRepository.findByLeagueId(leagueId);
    }

    public List<LeagueInvitation> getInvitationsByManager(String managerUsername) {
        return invitationRepository.findByManagerUsername(managerUsername);
    }

    public List<LeagueInvitation> getPendingInvitationsByLeague(String leagueId) {
        return invitationRepository.findByLeagueIdAndStatus(leagueId, LeagueInvitationStatus.PENDING);
    }

    public List<LeagueInvitation> getPendingInvitationsByManager(String managerUsername) {
        return invitationRepository.findByManagerUsernameAndStatus(managerUsername, LeagueInvitationStatus.PENDING);
    }

    public LeagueInvitation createInvitation(String leagueId, String managerUsername, String organizerUsername) {
        if (invitationRepository.existsByLeagueIdAndManagerUsername(leagueId, managerUsername)) {
            throw new ResourceAlreadyExistsException("Invitation already exists for this manager and league");
        }

        League league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new ResourceNotFoundException("League not found"));
        if (!league.getOrganizer().getUsername().equals(organizerUsername)) {
            throw new AccessDeniedException("Only league organizer can send invitations");
        }

        User manager = userRepository.findById(managerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
        if (manager.getRole() != UserRole.MANAGER) {
            throw new AccessDeniedException("Only users with MANAGER role can be invited");
        }

        LeagueInvitationPK id = new LeagueInvitationPK(leagueId, managerUsername);
        LeagueInvitation invitation = LeagueInvitation.builder()
                .id(id)
                .league(league)
                .manager(manager)
                .status(LeagueInvitationStatus.PENDING)
                .build();

        return invitationRepository.save(invitation);
    }

    public LeagueInvitation updateInvitationStatus(String leagueId, String managerUsername,
                                                   LeagueInvitationStatus status, String currentUserUsername) {
        LeagueInvitation invitation = getInvitationById(leagueId, managerUsername);
        if (!invitation.getManager().getUsername().equals(currentUserUsername)) {
            throw new AccessDeniedException("Only the invited manager can respond to invitations");
        }
        if (invitation.getStatus() != LeagueInvitationStatus.PENDING) {
            throw new RuntimeException("Cannot update already processed invitation");
        }
        if (status != LeagueInvitationStatus.ACCEPT && status != LeagueInvitationStatus.REJECT) {
            throw new RuntimeException("Invalid status. Only ACCEPTED or REJECTED allowed");
        }

        invitation.setStatus(status);
        return invitationRepository.save(invitation);
    }
}
