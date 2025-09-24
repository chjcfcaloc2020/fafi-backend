package com.chjcfcaloc2020.fafi.controller;

import com.chjcfcaloc2020.fafi.dto.MatchEventDTO;
import com.chjcfcaloc2020.fafi.dto.request.CreateMatchEventRequest;
import com.chjcfcaloc2020.fafi.dto.response.MatchEventResponse;
import com.chjcfcaloc2020.fafi.entity.MatchEvent;
import com.chjcfcaloc2020.fafi.service.MatchEventService;
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
@RequestMapping("/api/match-events")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MatchEventController {
    private final MatchEventService matchEventService;

    @GetMapping
    public ResponseEntity<List<MatchEventDTO>> getAllMatchEvents() {
        List<MatchEvent> events = matchEventService.getAllMatchEvents();
        List<MatchEventDTO> eventDTOs = events.stream()
                .map(MatchEventDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<MatchEventResponse> createMatchEvent(
            @Valid @RequestBody CreateMatchEventRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        MatchEvent matchEvent = MatchEvent.builder()
                .minute(request.getMinute())
                .description(request.getDescription())
                .build();
        MatchEvent createdEvent = matchEventService.createMatchEvent(
                matchEvent,
                request.getMatchId(),
                request.getPlayerId(),
                request.getEventId()
        );
        MatchEventResponse response = new MatchEventResponse(
                new MatchEventDTO(createdEvent),
                "Match event created successfully"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
