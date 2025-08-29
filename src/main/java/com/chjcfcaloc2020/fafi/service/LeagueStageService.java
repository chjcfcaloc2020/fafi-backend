package com.chjcfcaloc2020.fafi.service;

import com.chjcfcaloc2020.fafi.dto.LeagueStageDTO;
import com.chjcfcaloc2020.fafi.entity.LeagueStage;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceAlreadyExistsException;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.repository.LeagueStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueStageService {
    private final LeagueStageRepository leagueStageRepository;

    public LeagueStage createStage(LeagueStageDTO leagueStageDTO) {
        if (leagueStageRepository.existsByStageName(leagueStageDTO.getStageName())) {
            throw new ResourceAlreadyExistsException("Stage's name already exists");
        }
        LeagueStage leagueStage = LeagueStage.builder()
                .stageName(leagueStageDTO.getStageName()).build();
        return leagueStageRepository.save(leagueStage);
    }

    public List<LeagueStage> getAllStages() {
        return leagueStageRepository.findAll();
    }

    public LeagueStage getStageById(Long id) {
        return leagueStageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stage with ID = " + id + " not found"));
    }

    public LeagueStage updateStage(Long id, LeagueStageDTO leagueStageDTO) {
        LeagueStage leagueStage = getStageById(id);
        leagueStage.setStageName(leagueStageDTO.getStageName());
        return leagueStageRepository.save(leagueStage);
    }

    public void deleteStage(Long id) {
        LeagueStage leagueStage = getStageById(id);
        leagueStageRepository.delete(leagueStage);
    }
}
