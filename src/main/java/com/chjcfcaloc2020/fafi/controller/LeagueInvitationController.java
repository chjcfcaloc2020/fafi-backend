package com.chjcfcaloc2020.fafi.controller;

import com.chjcfcaloc2020.fafi.dto.LeagueInvitationDTO;
import com.chjcfcaloc2020.fafi.dto.request.CreateInvitationRequest;
import com.chjcfcaloc2020.fafi.dto.request.UpdateInvitationStatus;
import com.chjcfcaloc2020.fafi.entity.LeagueInvitation;
import com.chjcfcaloc2020.fafi.service.LeagueInvitationService;
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
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LeagueInvitationController {
    private final LeagueInvitationService invitationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<List<LeagueInvitationDTO>> getAllInvitations() {
        List<LeagueInvitation> invitations = invitationService.getAllInvitations();
        List<LeagueInvitationDTO> invitationDTOs = invitations.stream()
                .map(LeagueInvitationDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(invitationDTOs);
    }

    @GetMapping("/league/{leagueId}")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<List<LeagueInvitationDTO>> getInvitationsByLeague(
            @PathVariable String leagueId,
            @AuthenticationPrincipal UserDetails userDetails) {
        List<LeagueInvitation> invitations = invitationService.getInvitationsByLeague(leagueId);
        List<LeagueInvitationDTO> invitationDTOs = invitations.stream()
                .map(LeagueInvitationDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(invitationDTOs);
    }

    @GetMapping("/my-invitations")
    @PreAuthorize("hasAnyRole('MANAGER', 'ORGANIZER', 'ADMIN')")
    public ResponseEntity<List<LeagueInvitationDTO>> getMyInvitations(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<LeagueInvitation> invitations = invitationService.getInvitationsByManager(userDetails.getUsername());
        List<LeagueInvitationDTO> invitationDTOs = invitations.stream()
                .map(LeagueInvitationDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(invitationDTOs);
    }

    @GetMapping("/league/{leagueId}/pending")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<List<LeagueInvitationDTO>> getPendingInvitationsByLeague(
            @PathVariable String leagueId,
            @AuthenticationPrincipal UserDetails userDetails) {
        List<LeagueInvitation> invitations = invitationService.getPendingInvitationsByLeague(leagueId);
        List<LeagueInvitationDTO> invitationDTOs = invitations.stream()
                .map(LeagueInvitationDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(invitationDTOs);
    }

    @GetMapping("/my-invitations/pending")
    @PreAuthorize("hasAnyRole('MANAGER', 'ORGANIZER', 'ADMIN')")
    public ResponseEntity<List<LeagueInvitationDTO>> getMyPendingInvitations(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<LeagueInvitation> invitations = invitationService.getPendingInvitationsByManager(userDetails.getUsername());
        List<LeagueInvitationDTO> invitationDTOs = invitations.stream()
                .map(LeagueInvitationDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(invitationDTOs);
    }

    @GetMapping("{leagueId}/check-in")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<?> checkExistsManagerInLeague(
            @PathVariable String leagueId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(invitationService.checkExistsManagerInLeague(leagueId, userDetails.getUsername()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<LeagueInvitationDTO> createInvitation(
            @Valid @RequestBody CreateInvitationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        LeagueInvitation createdInvitation = invitationService.createInvitation(
                request.getLeagueId(),
                request.getManagerUsername(),
                userDetails.getUsername()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new LeagueInvitationDTO(createdInvitation));
    }

//    @PatchMapping("/{leagueId}/manager/{managerUsername}/status")
//    @PreAuthorize("hasAnyRole('MANAGER', 'ORGANIZER', 'ADMIN')")
//    public ResponseEntity<LeagueInvitationDTO> updateInvitation(
//            @PathVariable String leagueId,
//            @PathVariable String managerUsername,
//            @Valid @RequestBody UpdateInvitationStatus request,
//            @AuthenticationPrincipal UserDetails userDetails) {
//        LeagueInvitation invitation
//    }

    @PatchMapping("/{leagueId}/manager/{managerUsername}/status")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<LeagueInvitationDTO> acceptByManager(
            @PathVariable String leagueId,
            @PathVariable String managerUsername,
            @Valid @RequestBody UpdateInvitationStatus request,
            @AuthenticationPrincipal UserDetails userDetails) {
        LeagueInvitation acceptRequest = invitationService.acceptByManager(
                leagueId,
                managerUsername,
                request.getStatus()
        );
        return ResponseEntity.status(HttpStatus.OK).body(new LeagueInvitationDTO(acceptRequest));
    }
}
