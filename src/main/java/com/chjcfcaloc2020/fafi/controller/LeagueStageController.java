package com.chjcfcaloc2020.fafi.controller;

import com.chjcfcaloc2020.fafi.dto.LeagueStageDTO;
import com.chjcfcaloc2020.fafi.entity.LeagueStage;
import com.chjcfcaloc2020.fafi.service.LeagueStageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/league-stages")
@RequiredArgsConstructor
public class LeagueStageController {
    private final LeagueStageService leagueStageService;

    @PostMapping
    public ResponseEntity<LeagueStage> createStage(@Valid @RequestBody LeagueStageDTO leagueStageDTO) {
        return ResponseEntity.ok(leagueStageService.createStage(leagueStageDTO));
    }

    @GetMapping
    public ResponseEntity<List<LeagueStage>> getAllStages() {
        return ResponseEntity.ok(leagueStageService.getAllStages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueStage> getStageById(@PathVariable Long id) {
        return ResponseEntity.ok(leagueStageService.getStageById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueStage> updateStage(
            @PathVariable Long id,
            @RequestBody @Valid LeagueStageDTO leagueStageDTO) {
        return ResponseEntity.ok(leagueStageService.updateStage(id, leagueStageDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        leagueStageService.deleteStage(id);
        return ResponseEntity.noContent().build();
    }
}
